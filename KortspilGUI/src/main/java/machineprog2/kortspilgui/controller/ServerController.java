package machineprog2.kortspilgui.controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerController {
    static private final BlockingQueue<String> sendMessageQueue = new LinkedBlockingQueue<>();
    static private final BlockingQueue<String> receiveMessageQueue = new LinkedBlockingQueue<>();
    private String lastBoardUpdate;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
    private PrintWriter out;
    static private boolean clientReadyToReceive = true;

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

            in = clientSocket.getInputStream();
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
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                clientMessage = new String(buffer, 0, bytesRead);
                //Thread.sleep(10);
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client exited. Closing server.");
                    break;
                }
                boolean offered = receiveMessageQueue.offer(clientMessage);
                if (!offered) {
                    System.out.println("Error in adding clientMessage to queue. Message: '" + clientMessage + "'");
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected. Exception message: " + e.getMessage());
        }
    }

    private void sendMessages() {
        try {
            while (true) {
                Thread.sleep(10);
                if (clientReadyToReceive) {
                    //System.out.println("Waiting for message to send...");
                    String serverMessage = sendMessageQueue.take(); // Block until a message is available
                    System.out.println("   [Server]: " + serverMessage);
                    if (serverMessage.equalsIgnoreCase("exit")) {
                        System.out.println("Exit. Closing server.");
                        out.flush(); // Manually flushing the output stream
                        break;
                    }
                    out.print(serverMessage);
                    out.flush(); // Manually flushing the output stream
                    clientReadyToReceive = false;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR in sendMessages! Message: " + e.getMessage());
        }
    }

    public void addMessageToClient(String message) {
        //System.out.println("Adding message to queue: '" + message + "'");
        boolean offered = sendMessageQueue.offer(message);
        if (!offered) {
            System.out.println("Error in adding message to queue. Message: '" + message + "'");
        }
    }

    public BlockingQueue<String> getReceiveMessageQueue() {
        return receiveMessageQueue;
    }

    public static void setClientReadyToReceive() {
        ServerController.clientReadyToReceive = true;
        //System.out.println("Ready to send new message!");
    }
}
