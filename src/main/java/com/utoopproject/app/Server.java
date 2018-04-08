package com.utoopproject.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server
 */
public class Server {

    // Default ip address 1111
    private int serverPort = 1111;
    // Should the server run
    private boolean serverOn = false;
    // 
    private int clientsServed = 0;

    static List<RequestHandler> connectedClients = new ArrayList<RequestHandler>();

    /**
     *
     */
    public Server() {
        System.out.printf("[Server] Started <port %d>\n", serverPort);
    }


    // Server launch
    public void startServer() throws Exception {
        // Activate server
        serverOn = true;
        // Count served clients
        ServerSocket listen = new ServerSocket(serverPort);

        try {
            while (serverOn) {
                RequestHandler handleRequest = new RequestHandler(listen.accept(), ++clientsServed);
                handleRequest.start();

                connectedClients.add(handleRequest);

                System.out.printf("Client connected [ID: %d]\n", clientsServed);
            }

            //} catch() {

        } finally {
            // End server
            listen.close();
        }
    }

    public static void messageClients(RequestHandler sender, String message) throws IOException {
        System.out.println("DEBUG: Sending the entered message to other clients");

        for (RequestHandler connectedClient : connectedClients) {
            if (!(connectedClient.equals(sender))) {
                connectedClient.sendMessage(message);
            }
        }

    }

    // // Getters
    // Get data

    /**
     * @return number of clients that have connected to the server
     */
    public int getClientsServed() {
        return this.clientsServed;
    }

    // // Setters
    // Config

    /**
     * @param newPort - Set a new port for the server
     * @TODO Method that returns an error if server is already on,
     * which means that port can't be changed
     */
    public void setPort(int newPort) {
        this.serverPort = newPort;
    }

    // // // FUNCTIONS

    /**
     * @action Closes the server
     */
    public void closeServer() {
        this.serverOn = false;
    }

}