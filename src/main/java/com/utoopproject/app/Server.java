package com.utoopproject.app;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 * 
 */
public class Server {

    // Default ip address 1111
    private int serverPort = 1111;
    // Should the server run
    private boolean serverOn = false;
    // 
    private int clientsServed = 0;

    /**
     * 
     */
    public Server() {
        
        System.out.printf("[Server] <%d>", serverPort);
    }


    // Server launch
    public void startServer() throws Exception{
        // Activate server
        serverOn = true;
        // Count served clients
        ServerSocket listen = new ServerSocket(serverPort);

        try {
            while(serverOn) {
                RequestHandler handleRequest = new RequestHandler(listen.accept(), ++clientsServed);
                handleRequest.start();
            }

        //} catch() {
        
        } finally {
            // End server
            listen.close();
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
     * @param   newPort - Set a new port for the server
     * @TODO    Method that returns an error if server is already on,
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