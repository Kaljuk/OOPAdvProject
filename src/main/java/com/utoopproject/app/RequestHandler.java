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
            //OutputStream fileOut = new FileOutputStream(new File("rww"));
            this.username = dataIn.readUTF();

            while (true) {
                try {
                    String clientPick = dataIn.readUTF();

                    //TODO Midagi veidi katki siin
                    switch (clientPick){
                        case "All":
                            String clientMessage = dataIn.readUTF();
                            for (RequestHandler connectedClient: server.getConnectedClients()) {
                                if (!connectedClient.getUsername().equals(this.username)){
                                    //connectedClient.dataOut.writeUTF("All");
                                    connectedClient.dataOut.writeUTF(clientMessage);
                                }
                            }
                            break;
                        case "Private":
                            String kasutaja = dataIn.readUTF();
                            String message = dataIn.readUTF();
                            for (RequestHandler connectedClient: server.getConnectedClients()) {
                                if (connectedClient.getUsername().equals(kasutaja)){
                                    //onnectedClient.dataOut.writeUTF("Private");
                                    connectedClient.dataOut.writeUTF(message);
                                }
                            }
                            break;
                        case "File":
                            /*String kasutaja1 = dataIn.readUTF();
                            for (RequestHandler connectedClient: server.getConnectedClients()) {
                                if (connectedClient.getUsername().equals(kasutaja1)){
                                    connectedClient.dataOut.writeUTF("File");
                                    connectedClient.dataOut.writeUTF("Kasutaja " + this.username + " soovib sulle faili saata");
                                    if (dataIn.readUTF().equals("y")){

                                    }
                                }
                            }*/
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