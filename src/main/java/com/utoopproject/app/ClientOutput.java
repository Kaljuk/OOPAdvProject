package com.utoopproject.app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientOutput implements Runnable {
    private Scanner scanner;
    private DataOutputStream dOut;
    private String username;

    public ClientOutput(Scanner scanner, DataOutputStream dOut, String username) {
        this.scanner = scanner;
        this.dOut = dOut;
        this.username = username;
    }

    @Override
    public void run() {
        while (true) {
            String message = scanner.nextLine();

            try {
                dOut.writeUTF(username + ": " + message);
                dOut.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
