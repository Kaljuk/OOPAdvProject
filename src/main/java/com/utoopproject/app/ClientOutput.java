package com.utoopproject.app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientOutput implements Runnable {
    private Scanner scanner;

    private DataOutputStream dOut;

    public ClientOutput(Scanner scanner, DataOutputStream dOut) {
        this.scanner = scanner;
        this.dOut = dOut;
    }

    @Override
    public void run() {
        System.out.println("ClientOutput started");
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            System.out.println("Client is outputting message");

            try {
                dOut.writeUTF(message);
                dOut.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Wrote the message to output");
        }
    }
}
