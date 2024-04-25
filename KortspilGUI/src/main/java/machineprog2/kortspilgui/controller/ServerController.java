package machineprog2.kortspilgui.controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerController {
    private final BlockingQueue<String> sendMessageQueue = new LinkedBlockingQueue<>();
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerController(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for clients to connect...");

            // Start server in a separate thread
            Thread serverThread = new Thread(this::startServer);
            serverThread.start();
        } catch (IOException e) {
            System.out.println("ERROR in ServerController! Message: " + e.getMessage());
        }
    }

    private void startServer() {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Thread for receiving messages from client
            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.start();

            // Thread for sending messages to client
            Thread sendThread = new Thread(this::sendMessages);
            sendThread.start();
        } catch (IOException e) {
            System.out.println("ERROR in startServer! Message: " + e.getMessage());
        }
    }

    private void receiveMessages() {
        try {
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                Thread.sleep(1);
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client exited. Closing server.");
                    break;
                }
                System.out.println("Received from client2: " + clientMessage);

                handleMessageFromClient(clientMessage);
            }
        } catch (IOException e) {
            System.out.println("ERROR! IOException in receiveMessages. Message: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("ERROR! InterruptedException in receiveMessages. Message: " + e.getMessage());
        }
    }

    private void sendMessages() {
        try {
            while (true) {
                //Thread.sleep(1000);
                String serverMessage = sendMessageQueue.take(); // Block until a message is available
                System.out.println("Sending message: " + serverMessage);
                out.print(serverMessage);
                out.flush(); // Manually flushing the output stream
                if (serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Exit. Closing server.");
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR in sendMessages! Message: " + e.getMessage());
        }
    }

    public void handleMessageFromClient(String receivedMessage) {
        System.out.println("Received from client: " + receivedMessage);
    }

    public void addMessageToClient(String message) {
        boolean offered = sendMessageQueue.offer(message);
        if (!offered) {
            System.out.println("Error in adding message to queue. Message: '" + message + "'");
        }
    }
}
