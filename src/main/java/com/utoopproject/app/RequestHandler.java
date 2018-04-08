package com.utoopproject.app;


import java.io.*;
import java.net.Socket;

/**
 * RequestHandler
 */
public class RequestHandler extends Thread {
    private Socket socket;
    private int clientID;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;

    public RequestHandler(Socket requestSocket, int clientNumber) {
        super();
        this.socket = requestSocket;
        this.clientID = clientNumber;
    }

    @Override
    public void run() {
        try {

            dataIn = new DataInputStream(
                    socket.getInputStream()
            );

            dataOut = new DataOutputStream(
                    socket.getOutputStream()
            );

            boolean listen = true;

            while (listen) {
                try {
                    String line = dataIn.readUTF();
                    System.out.println(line);

                    // Tell the server to send this message to all the other clients
                    Server.messageClients(this, line);
                } catch (IOException ioe) {
                    listen = false;
                }
            }

        } catch (IOException e) {
            System.out.println(String.format("Client %d error %s", this.clientID, e.toString()));
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Socket close error" + e.toString());
            }
        }
    }

    /**
     * Sends a message to all the clients currently connected
     *
     * @param msg - The message which will be sent
     */
    public void sendMessage(String msg) throws IOException {
        System.out.println("DEBUG: Writing message UTF");

        dataOut.writeUTF(msg);
        dataOut.flush();
    }
}