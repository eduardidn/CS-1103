import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ChatServer {
    private static final int PORT = 5000;
    static List<ClientHandler> clients = new ArrayList<>();
    private static int userIdCounter = 1;
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("       CHAT SERVER - STARTING UP");
        System.out.println("===========================================");
        System.out.println("Server listening on port " + PORT + "...");
        System.out.println("Waiting for clients to connect...\n");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, userIdCounter++);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                
                System.out.println("[SERVER] New connection. Total clients: " + clients.size());
            }
        } catch (IOException e) {
            System.err.println("[SERVER ERROR] Failed to start: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void broadcastMessage(String message, ClientHandler sender) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message;
        
        System.out.println(formattedMessage);
        
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(formattedMessage);
            }
        }
    }
    
    public static void removeClient(ClientHandler clientHandler) {
        synchronized (clients) {
            clients.remove(clientHandler);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private int userId;
    
    public ClientHandler(Socket socket, int userId) {
        this.socket = socket;
        this.userId = userId;
        this.username = "User" + userId;
    }
    
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            out.println("===========================================");
            out.println("  WELCOME TO THE CHAT ROOM!");
            out.println("===========================================");
            out.println("Your User ID: " + userId);
            out.println("Your Username: " + username);
            out.println("Type '/help' for commands");
            out.println("Type '/quit' to exit");
            out.println("===========================================\n");
            
            ChatServer.broadcastMessage("*** " + username + " has joined the chat! ***", this);
            
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/quit")) {
                    break;
                } else if (message.startsWith("/help")) {
                    sendMessage("\nAvailable commands:");
                    sendMessage("  /help  - Show this help message");
                    sendMessage("  /quit  - Leave the chat");
                    sendMessage("  /users - List all connected users\n");
                } else if (message.startsWith("/users")) {
                    sendMessage("\nConnected users: " + ChatServer.clients.size());
                    synchronized (ChatServer.clients) {
                        for (ClientHandler client : ChatServer.clients) {
                            sendMessage("  - " + client.username + " (ID: " + client.userId + ")");
                        }
                    }
                    sendMessage("");
                } else if (!message.trim().isEmpty()) {
                    ChatServer.broadcastMessage(username + ": " + message, this);
                }
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Error with client " + username + ": " + e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }
    
    private void disconnect() {
        try {
            ChatServer.broadcastMessage("*** " + username + " has left the chat ***", this);
            ChatServer.removeClient(this);
            
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            
            System.out.println("[SERVER] " + username + " disconnected. Remaining: " + 
                             ChatServer.clients.size());
        } catch (IOException e) {
            System.err.println("[SERVER ERROR] Error during disconnect: " + e.getMessage());
        }
    }
}
