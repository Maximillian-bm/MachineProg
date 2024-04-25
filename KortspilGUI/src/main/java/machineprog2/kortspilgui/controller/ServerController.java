package machineprog2.kortspilgui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
            // Create a ServerSocket and bind it to a port
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for client...");

            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            // Get input and output streams
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            // Continuously send and receive data
            while (true) {
                // Receive data from client
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                if (bytesRead == -1) {
                    break; // Connection closed by client
                }
                String receivedMessage = new String(buffer, 0, bytesRead);
                System.out.println("Received from client: " + receivedMessage);

                // Send response to client
                String responseMessage = "Hello from server!";
                outputStream.write(responseMessage.getBytes());
                System.out.println("Sent to client: " + responseMessage);
            }

            // Close streams and socket
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            serverSocket.close();
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

