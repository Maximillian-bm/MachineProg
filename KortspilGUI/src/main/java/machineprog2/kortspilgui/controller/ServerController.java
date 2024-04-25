package machineprog2.kortspilgui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ServerController implements Runnable {
    private final int port;
    private Controller ctrl;

    public ServerController(int port, Controller ctrl) {
        this.port = port;
        this.ctrl = ctrl;

    }
    private Socket clientSocket;

    @Override
    public void run() {
        try {
            // Lambda function to handle received messages
            Consumer<String> messageHandler = (message) -> {
                System.out.println("Received from client: " + message);
                // Call a method to process the message
                receiveMessageFromServer(message);
            };

            // Create a ServerSocket and bind it to a port
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for client...");

            // Accept client connection
            clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Get input stream from the client
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read messages from the client and pass them to the messageHandler lambda function
            String message;
            while ((message = reader.readLine()) != null) {
                // Pass received message to the handler
                messageHandler.accept(message);
            }

            // Close the reader and socket
            reader.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessageFromServer(String receivedMessage) {
        System.out.println("Received from client: " + receivedMessage);
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
