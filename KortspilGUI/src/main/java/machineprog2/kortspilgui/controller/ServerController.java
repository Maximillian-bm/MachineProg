package machineprog2.kortspilgui.controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerController {
    private final BlockingQueue<String> sendMessageQueue = new LinkedBlockingQueue<>();
    private String lastBoardUpdate;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
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

            in = clientSocket.getInputStream();
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Thread for sending messages to client
            Thread sendThread = new Thread(this::sendMessages);
            sendThread.start();
        } catch (IOException e) {
            System.out.println("ERROR in startServer! Message: " + e.getMessage());
        }
    }

    private void sendMessages() {
        try {
            while (true) {
                Thread.sleep(100);
                String serverMessage = sendMessageQueue.take(); // Block until a message is available
                System.out.println("Sending message: " + serverMessage);
                out.print(serverMessage);
                out.flush(); // Manually flushing the output stream
                if (serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Exit. Closing server.");
                    break;
                }
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                if (bytesRead == -1) {
                    break; // Connection closed by client
                }
                String receivedMessage = new String(buffer, 0, bytesRead);
                lastBoardUpdate = receivedMessage;
            }
        } catch (InterruptedException e) {
            System.out.println("ERROR in sendMessages! Message: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessageToClient(String message) {
        String r = null;
        boolean offered = sendMessageQueue.offer(message);
        if (!offered) {
            System.out.println("Error in adding message to queue. Message: '" + message + "'");
        }
    }

    public String getLastBoardUpdate(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lastBoardUpdate;
    }
}
