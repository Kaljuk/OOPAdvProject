package com.utoopproject.app;


import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * RequestHandler
 */
public class RequestHandler extends Thread {
    Socket socket;
    private int clientID;

    public RequestHandler(Socket requestSocket, int clientNumber) {
        super();
        this.socket   = requestSocket;
        this.clientID = clientNumber;
    }

    @Override
    public void run() {
        try {
            
            DataInputStream dataIn   = new DataInputStream(
                socket.getInputStream()
            );
            DataOutputStream dataOut = new DataOutputStream(
                socket.getOutputStream()
            );

            String line;
            boolean done = false;


        } catch(IOException e) {
            System.out.println(String.format("Client %d error %s", this.clientID, e.toString()));
        } finally {
            try {
                socket.close();
            } catch(Exception e) {
                System.out.println("Socket close error"+e.toString());
            }
        }
    }

    
}