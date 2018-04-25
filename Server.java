package com.utoopproject.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Server
 */
public class Server {
    private int serverPort = 1111;
    private boolean serverOn = false;
    private int clientsServed = 0;
    private List<RequestHandler> connectedClients;


    public Server() {
        this.connectedClients = new ArrayList<RequestHandler>();
        System.out.printf("[Server] Started <port %d>\n", serverPort);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.startServer();
    }


    public void startServer() throws Exception {
        serverOn = true;
        ServerSocket listen = new ServerSocket(serverPort);

        try {
            while (serverOn) {
                RequestHandler handleRequest = new RequestHandler(listen.accept(), ++clientsServed, this);
                handleRequest.start();
                System.out.println("server on");

                connectedClients.add(handleRequest);

                System.out.printf("Client connected [ID: %d]\n", clientsServed);
            }
            //} catch() {
        } finally {
            listen.close();
        }
    }

    public List<RequestHandler> getConnectedClients() {
        return connectedClients;
    }

    private void addToClientHandlers(RequestHandler connectedClient){
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