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
    private Server server;

    public RequestHandler(Socket requestSocket, int clientNumber, Server server) {
        super(); //???
        this.socket = requestSocket;
        this.clientID = clientNumber;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            //TODO username?


            dataIn = new DataInputStream(socket.getInputStream());

            dataOut = new DataOutputStream(socket.getOutputStream());

            //boolean listen = true;

            while (true) {
                try {
                    String clientInput = dataIn.readUTF();
                    //System.out.println(line);

                    // Tell the server to send this message to all the other clients
                    //Server.messageClients(this, line);
                    //TODO sündmused nagu private, all, show log etc ja vastavalt info kuvada enne(olemasolevatest võimalustest) ja pärast
                    //TODO private ainult requestHandleri id pealt? vb ok, sest private msg saaja määrataks username'iga
                    for (RequestHandler connectedClient: server.getConnectedClients()) {
                        if (connectedClient.getClientID() != this.clientID){
                            connectedClient.dataOut.writeUTF(clientInput);
                        }
                    }
                } catch (IOException ioe) {
                    break;
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

    public int getClientID() {
        return clientID;
    }

    /**
     * Sends a message to all the clients currently connected
     *
     * @param msg - The message which will be sent
     *//*
    public void sendMessage(String msg) throws IOException {
        System.out.println("DEBUG: Writing message UTF");

        dataOut.writeUTF(msg);
        dataOut.flush();
    }*/
}