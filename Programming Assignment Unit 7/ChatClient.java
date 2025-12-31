import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;
    private volatile boolean running = true;
    
    public ChatClient() {
        scanner = new Scanner(System.in);
    }
    
    public boolean connect() {
        try {
            System.out.println("Connecting to server at " + SERVER_ADDRESS + ":" + SERVER_PORT + "...");
            
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println("Connected successfully!\n");
            return true;
            
        } catch (UnknownHostException e) {
            System.err.println("ERROR: Unknown host " + SERVER_ADDRESS);
            return false;
        } catch (IOException e) {
            System.err.println("ERROR: Could not connect to server. Make sure the server is running.");
            System.err.println("Details: " + e.getMessage());
            return false;
        }
    }
    
    public void start() {
        Thread receiveThread = new Thread(new MessageReceiver());
        receiveThread.start();
        sendMessages();
    }
    
    private void sendMessages() {
        try {
            displayInterface();
            
            while (running) {
                String message = scanner.nextLine();
                
                if (message != null && !message.trim().isEmpty()) {
                    out.println(message);
                    
                    if (message.trim().equalsIgnoreCase("/quit")) {
                        running = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            if (running) {
                System.err.println("ERROR: Failed to send message: " + e.getMessage());
            }
        } finally {
            disconnect();
        }
    }
    
    private void displayInterface() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     TEXT-BASED CHAT CLIENT UI          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Type your messages below and press Enter to send.");
        System.out.println("─────────────────────────────────────────\n");
    }
    
    private void disconnect() {
        try {
            running = false;
            
            System.out.println("\n─────────────────────────────────────────");
            System.out.println("Disconnecting from chat server...");
            
            if (scanner != null) scanner.close();
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
            
            System.out.println("Disconnected. Goodbye!");
            System.out.println("─────────────────────────────────────────");
            
        } catch (IOException e) {
            System.err.println("ERROR during disconnect: " + e.getMessage());
        }
    }
    
    private class MessageReceiver implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while (running && (message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("\nERROR: Lost connection to server.");
                    running = false;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                                        ║");
        System.out.println("║       CHAT CLIENT APPLICATION          ║");
        System.out.println("║       CS 1103 - Unit 7 Assignment      ║");
        System.out.println("║                                        ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        ChatClient client = new ChatClient();
        
        if (client.connect()) {
            client.start();
        } else {
            System.err.println("\nFailed to connect to server.");
            System.err.println("Please ensure:");
            System.err.println("  1. The ChatServer is running");
            System.err.println("  2. The server is listening on port " + SERVER_PORT);
            System.err.println("  3. No firewall is blocking the connection");
        }
    }
}
