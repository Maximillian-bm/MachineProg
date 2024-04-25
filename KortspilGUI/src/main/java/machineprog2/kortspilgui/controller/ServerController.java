package machineprog2.kortspilgui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ServerController {
    private final int port;
    private Controller ctrl;
    public ServerController(int port, Controller ctrl) {
        this.port = port;
        this.ctrl = ctrl;

        startServer();
    }
    private Socket clientSocket;

    public void startServer() {
        try {
            // Create a ServerSocket and bind it to a port
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for client...");

            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Get output streams
            OutputStream outputStream = clientSocket.getOutputStream();

            // Send hello to client
            String msg = "Hello from server!";
            outputStream.write(msg.getBytes());
            System.out.println("Sent to client: " + msg);

            // Close output stream
            outputStream.close();

            // Lambda function to handle received messages
            Consumer<String> messageHandler = (message) -> {
                System.out.println("Received message: " + message);
                // Call a method to process the message
                receiveMessageFromServer(message);
            };

            // Start listening for messages
            startListening(messageHandler);

        } catch (IOException e) {
            System.out.println("IOException. Msg: " + e.getMessage());
        }
    }

    public void startListening(Consumer<String> messageHandler) {
        // Simulating message reception from a client
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String message;
            while ((message = reader.readLine()) != null) {
                // Pass received message to the handler
                messageHandler.accept(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessageFromServer(String msg) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            // Receive data from client
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                // Connection closed by client
            }
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Received from client: " + receivedMessage);
        } catch (IOException e) {
            System.out.println("IOException. Msg: " + e.getMessage());
        }
    }

    public void sendCommandToServer(String msg) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(msg.getBytes());
            System.out.println("Sent to client: " + msg);
            outputStream.close();

        } catch (IOException e) {
            System.out.println("IOException. Msg: " + e.getMessage());
        }
    }
}
