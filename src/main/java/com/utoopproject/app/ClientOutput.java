package com.utoopproject.app;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientOutput implements Runnable {
    private Scanner scanner;
    private DataOutputStream dOut;
    private String username;
    private ArrayList<String> lubatudCommandid = new ArrayList<>(Arrays.asList("/file", "/private", "/log", "/fileupload", "/stopserver", "/online", "/ip", "/time"));

    public ClientOutput(Scanner scanner, DataOutputStream dOut, String username, String password) throws IOException {
        this.scanner = scanner;
        this.dOut = dOut;
        this.username = username;
        dOut.writeUTF(username); //edastame requestHandlerile username'i
        dOut.writeUTF(password);
    }


    @Override
    public void run() {
        while (true) {
            String line = scanner.nextLine();
            String command;

            if(line.startsWith("/")) {
                command = line.split(" ", 2)[0];

                if(!lubatudCommandid.contains(command))
                    command = "error";
            } else
                command = "all";

            try {
                dOut.writeUTF(command);
            } catch (IOException e) {
                throw new RuntimeException("valiku edastamine ebaõnnestus: " + e);
            }

            try {
                switch (command) {
                    case "all":
                        dOut.writeUTF(username + ": " + line);
                        break;

                    case "error":
                        dOut.writeUTF("Command not found, please try again!");
                        break;

                    case "/private":
                        System.out.println("Sisesta saaja kasutaja: ");
                        String kasutaja = scanner.nextLine();
                        dOut.writeUTF(kasutaja);
                        System.out.println("sisesta sõnum: ");
                        String message1 = scanner.nextLine();
                        dOut.writeUTF(username + ": " + message1);

                        break;
                    case "/fileupload":
                        //ei tööta

                        System.out.println("Sisesta faili path: ");
                        File file = new File(scanner.nextLine());
                        InputStream fileInputStream = new FileInputStream(file);
                        System.out.println("Sisesta faili nimi: ");
                        dOut.writeUTF(scanner.nextLine());
                        byte[] bytes = new byte[1024];
                        int count = fileInputStream.read(bytes);
                        while (count > 0) {
                            dOut.write(bytes, 0, count);
                            count = fileInputStream.read(bytes);
                        }
                        fileInputStream.close();


                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
