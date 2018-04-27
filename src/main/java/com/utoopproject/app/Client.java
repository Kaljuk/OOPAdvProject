package com.utoopproject.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //private final int serverPort = 1111;
    private final String username;
    private final Socket socket;
    private Scanner scanner;

    public Client(String serverAddress, String username, Scanner scanner) throws Exception {
        this.socket = new Socket(serverAddress, 1111);
        this.scanner = scanner;
        this.username = username;
        this.start();
    }

    void start() throws IOException {
        //TODO try with resources paneb streamid kinni ja sellep tuli viga
        //TODO oleks vaja mingi muu lahendus leida, et hiljem streamid sulgeda


        ClientOutput clientOutput = new ClientOutput(scanner, new DataOutputStream(socket.getOutputStream()), username);
        ClientInput clientInput = new ClientInput(new DataInputStream(socket.getInputStream()));

        Thread thread1 = new Thread(clientOutput);
        thread1.start();
        Thread thread = new Thread(clientInput);
        thread.start();
        System.out.println("You are now connected");
    }

    public String getUsername() {
        return username;
    }

}
