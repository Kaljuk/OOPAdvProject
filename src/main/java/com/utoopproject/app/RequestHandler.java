package com.utoopproject.app;


import javax.xml.crypto.Data;
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
    private String[] lubatudKäsud = {"ALL", "PRIVATE", "FILE", "LOG", "STOP SERVER"};
    private String username;
    private String password;

    public RequestHandler(ServerSocket serverSocket, int clientNumber, Server server) throws IOException {
        this.messageSocket = serverSocket.accept();
        this.clientID = clientNumber;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            dataIn = new DataInputStream(messageSocket.getInputStream());
            dataOut = new DataOutputStream(messageSocket.getOutputStream());
            //OutputStream fileOut = new FileOutputStream(new File("rww"));

            this.username = dataIn.readUTF();
            this.password = dataIn.readUTF();

            dataOut.writeUTF("Connection established, server checking your login credentials.");

            if (server.loginOrRegisterUser(username, password)) {

                dataOut.writeUTF("All valid! You are now successfully logged in. ");
                dataOut.writeUTF("\nLatest messages in the chat: \n" + server.getLatestMessages()); // Send latest messages to the client
                dataOut.writeUTF("\nAvailable commands: '/file', '/private', '/log', '/fileupload', '/stopserver'");

                while (true) {
                    try {
                        String clientPick = dataIn.readUTF();

                        //TODO Midagi veidi katki siin
                        switch (clientPick) {
                            case "/stopserver":
                                server.stopServer();
                            case "all":
                                String clientMessage = dataIn.readUTF();
                                server.writeToLog(clientMessage); // Write it to the server log
                                server.addLatestMessage(clientMessage); // Add it to the recent messages linked list

                                for (RequestHandler connectedClient : server.getConnectedClients()) {
                                    if (!connectedClient.getUsername().equals(this.username)) {
                                        //connectedClient.dataOut.writeUTF("All");
                                        connectedClient.dataOut.writeUTF(clientMessage);
                                    }
                                }
                                break;
                            case "error":
                                String msg = dataIn.readUTF();
                                this.dataOut.writeUTF(msg);
                            case "/private":
                                String kasutaja = dataIn.readUTF();
                                String message = dataIn.readUTF();
                                for (RequestHandler connectedClient : server.getConnectedClients()) {
                                    if (connectedClient.getUsername().equals(kasutaja)) {
                                        //onnectedClient.dataOut.writeUTF("Private");
                                        connectedClient.dataOut.writeUTF(message);
                                    }
                                }
                                break;
                            case "/file":
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
            } else {
                dataOut.writeUTF("Unable to log into this account! Please try again.");
            }

        } catch (Exception e) {
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