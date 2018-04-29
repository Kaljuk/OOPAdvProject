package com.utoopproject.app;

import java.io.*;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Server
 */
public class Server {
    private int serverPort = 1111;
    private boolean serverOn = false;
    private int clientsServed = 0;
    private List<RequestHandler> connectedClients;

    public Server() throws Exception {
        this.connectedClients = new ArrayList<RequestHandler>();
        this.startServer();
        System.out.printf("[Server] Started <port %d>\n", serverPort);
    }

    public void startServer() throws Exception {
        serverOn = true;
        ServerSocket serverSocket = new ServerSocket(serverPort);
        System.out.println("Server on");

        try {
            while (serverOn) {
                RequestHandler handleRequest = new RequestHandler(serverSocket, ++clientsServed, this);
                handleRequest.start();

                connectedClients.add(handleRequest);

                System.out.printf("Client connected [ID: %d]\n", clientsServed);
            }
            //} catch() {
        } finally {
            serverSocket.close();
        }
    }

    public List<RequestHandler> getConnectedClients() {
        return connectedClients;
    }

    public void writeToLog(String message) {
        try (OutputStream out = new FileOutputStream("chatlog.txt", true);
             OutputStreamWriter textOut = new OutputStreamWriter(out, "UTF-8");
             BufferedWriter buffered = new BufferedWriter(textOut)){

            String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
            buffered.write("[" + timeStamp + "] " + message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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