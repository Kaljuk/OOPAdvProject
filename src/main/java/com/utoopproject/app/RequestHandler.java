package com.utoopproject.app;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * RequestHandler
 */
public class RequestHandler extends Thread {
    private Socket messageSocket;
    private int clientID;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private Server server;
    private String[] lubatudKäsud = {"ALL", "PRIVATE", "FILE", "LOG"};
    private String username;

    public RequestHandler(ServerSocket serverSocket, int clientNumber, Server server) throws IOException {
        this.messageSocket = serverSocket.accept();
        this.clientID = clientNumber;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            //TODO username?


            dataIn = new DataInputStream(messageSocket.getInputStream());
            dataOut = new DataOutputStream(messageSocket.getOutputStream());
            this.username = dataIn.readUTF();

            while (true) {
                try {
                    /*dataOut.writeUTF("Pick one of the following, press ENTER and then enter your message: " + String.join(" ", lubatudKäsud));
                    String clientPick = dataIn.readUTF();
                    while (!Arrays.asList(lubatudKäsud).contains(clientPick)){
                        dataOut.writeUTF("Pick one of the following, press ENTER and then enter your message: " + String.join(" ", lubatudKäsud));
                        clientPick = dataIn.readUTF();
                    }*/
                    String clientPick = dataIn.readUTF();

                    //TODO sündmused nagu private, all, show log etc ja vastavalt info kuvada enne(olemasolevatest võimalustest) ja pärast
                    //TODO private ainult requestHandleri id pealt? vb ok, sest private msg saaja määrataks username'iga
                    switch (clientPick){
                        case "All":
                            String clientMessage = dataIn.readUTF();
                            for (RequestHandler connectedClient: server.getConnectedClients()) {
                                if (!connectedClient.getUsername().equals(this.username)){
                                    connectedClient.dataOut.writeUTF(clientMessage);
                                }
                            }
                            break;
                        case "Private":
                            String kasutaja = dataIn.readUTF();
                            String message = dataIn.readUTF();
                            for (RequestHandler connectedClient: server.getConnectedClients()) {
                                if (connectedClient.getUsername().equals(kasutaja)){
                                    connectedClient.dataOut.writeUTF(message);
                                }
                            }
                        case "File":
                            //TODO
                            break;
                    }

                } catch (IOException ioe) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(String.format("Client %d error %s", this.clientID, e.toString()));
        } finally {
            try {
                messageSocket.close();
            } catch (Exception e) {
                System.out.println("Socket close error" + e.toString());
            }
        }
    }

    public String getUsername() {
        return username;
    }
}