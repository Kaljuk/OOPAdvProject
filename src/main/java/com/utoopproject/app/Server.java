package com.utoopproject.app;

import java.io.*;
import java.sql.*;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Server
 */
public class Server {
    private int serverPort = 1111;
    private boolean serverOn = false;
    private int clientsServed = 0;
    private List<RequestHandler> connectedClients;
    private List<File> failidServeris = new ArrayList<>();
    private LinkedList<String> latestMessages = new LinkedList();
    private BufferedWriter logWriter; // To keep the log file open
    private ServerSocket serverSocket;

    public Server() throws Exception {
        this.connectedClients = new ArrayList<RequestHandler>();
        this.createDatabase();
        this.startServer();
    }

    public void startServer() throws Exception {
        serverOn = true;
        System.out.printf("[Server] Started <port %d>\n", serverPort);

        try {
            serverSocket = new ServerSocket(serverPort);
            logWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("chatlog.txt", true), "UTF-8"));

            while (serverOn) {
                RequestHandler handleRequest = new RequestHandler(serverSocket, ++clientsServed, this);
                handleRequest.start();

                connectedClients.add(handleRequest);

                System.out.printf("Client connected [ID: %d]\n", clientsServed);
            }
        } finally {
            logWriter.close();
            serverSocket.close();
        }
    }

    // TODO Send message to all clients that the server will close
    public void stopServer() throws IOException {
        serverOn = false;
        serverSocket.close();

    }

    public void createDatabase() throws Exception {
        Class.forName("org.h2.Driver");
        Connection databaseConnection = DriverManager.getConnection("jdbc:h2:file:./database");
        databaseConnection.prepareStatement("CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(64) NOT NULL PRIMARY KEY, " +
                "password VARCHAR(32) NOT NULL)").executeUpdate();
        databaseConnection.close();
        System.out.println("Database created (if it didn't exist before)");
    }

    public List<RequestHandler> getConnectedClients() {
        return connectedClients;
    }

    public boolean loginOrRegisterUser(String username, String password) throws Exception {
        Class.forName("org.h2.Driver");
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./database")) {
            UserSystem user = new UserSystem(conn);

            if (user.userExists(username))
                return user.loginUser(username, password);
            else
                return user.registerUser(username, password);
        }
    }

    /**
     * Writes the string into chat log text file, with a timestamp with current date and time in front of it.
     *
     * @param message the string to log
     */
    public void writeToLog(String message) throws IOException {
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        logWriter.write("[" + timeStamp + "] " + message + "\n");
        logWriter.flush();
    }

    //ei tööta
    public void receiveFile(String fileName, DataInputStream dataIn) throws IOException {
        File file = new File(fileName);
        failidServeris.add(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        byte[] puhver = new byte[1024];
        int loetud = dataIn.read(puhver);
        while (loetud > 0) {
            fileOutputStream.write(puhver, 0, loetud); // ainult andmetega täidetud osa!
            loetud = dataIn.read(puhver); // loeme järgmise tüki
        }
        fileOutputStream.close();
    }

    public void addLatestMessage(String message) {
        if (latestMessages.size() >= 5) latestMessages.removeFirst();
        latestMessages.addLast(message);
    }

    public String getLatestMessages() {
        StringBuilder sb = new StringBuilder();
        if (latestMessages.size() == 0) return "No messages found!";

        for (String latestMessage : latestMessages) {
            sb.append(latestMessage + "\n");
        }

        return sb.toString();
    }

    private void addToClientHandlers(RequestHandler connectedClient) {
        connectedClients.add(connectedClient);
    }

    /*public static void messageClients(RequestHandler sender, String message) throws IOException {
        System.out.println("DEBUG: Sending the entered message to other clients");

        for (RequestHandler connectedClient : connectedClients) {
            if (!(connectedClient.equals(sender))) {
                connectedClient.sendMessage(message);
            }
        }

    }*/


    public int getClientsServed() {
        return this.clientsServed;
    }

    /**
     * @param newPort - Set a new port for the server
     * @TODO Method that returns an error if server is already on,
     * which means that port can't be changed
     */
    public void setPort(int newPort) {
        this.serverPort = newPort;
    }


    public void closeServer() {
        this.serverOn = false;
    }

}