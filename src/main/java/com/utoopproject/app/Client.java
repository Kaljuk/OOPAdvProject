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

    public Client(String serverAddress, String username) throws Exception {
        this.socket = new Socket(serverAddress, 1111);
        this.scanner = new Scanner(System.in);
        this.username = username;
        //this.username = setClientUsername();
        //this.start();
    }

    void start() throws IOException {
        //TODO try with resources paneb streamid kinni ja sellep tuli viga
        //TODO oleks vaja mingi muu lahendus leida, et hiljem streamid sulgeda
        /*try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream()); DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())){

            //dataOutputStream.writeUTF(setClientUsername());

            ClientOutput clientOutput = new ClientOutput(scanner, dataOutputStream);
            ClientInput clientInput = new ClientInput(dataInputStream);

            Thread thread1 = new Thread(clientOutput);
            thread1.start();
            Thread thread = new Thread(clientInput);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ClientOutput clientOutput = new ClientOutput(scanner, new DataOutputStream(socket.getOutputStream()));
        ClientInput clientInput = new ClientInput(new DataInputStream(socket.getInputStream()));

        Thread thread1 = new Thread(clientOutput);
        thread1.start();
        Thread thread = new Thread(clientInput);
        thread.start();
    }

    public String getUsername() {
        return username;
    }

    /*private String setClientUsername(){
        System.out.println("Insert your username here: ");
        String username = scanner.nextLine();
        while (true){
            String testUsername = scanner.nextLine();
            if (testUsername.length() > 20 || testUsername.length() < 1){
                System.out.println("Username must be at least 2 characters long and smaller than 20. Insert a new one: ");
            } else {
                break;
            }
        }
        return username;
    }/*

    /*private void askForInput() throws Exception {

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();

            dos.writeUTF("\n" + username + ": " + nextLine);
            dos.flush();
        }
    }*/
}
