package com.utoopproject.app;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final int serverPort = 1111;
    private final String username;
    private final Socket socket;

    public Client(String serverAddress, String username) throws Exception {
        this.username = username;

        Socket socket = new Socket(serverAddress, serverPort);
        this.socket = socket;

        // Creates a separate thread, which waits for messages from the server, because scanner and socket cant be
        // in the same thread (they pause the thread when waiting for next line/data input)
        ClientThread thread = new ClientThread(socket);
        thread.start();

        // Ask for chat input
        askForInput();
    }


    private void askForInput() throws Exception {

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        /// TODO: remove scanner and replace it with the one from app.java 
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();

            dos.writeUTF("\n" + username + ": " + nextLine);
            dos.flush();
        }
    }
}
