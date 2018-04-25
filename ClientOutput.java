package com.utoopproject.app;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientOutput implements Runnable {
    private Scanner scanner;
        private DataOutputStream dOut;

        public ClientOutput (Scanner scanner, DataOutputStream dOut) {
            this.scanner = scanner;
            this.dOut = dOut;
        }

        @Override
        public void run() {
            while (true) {
                String message = scanner.nextLine();
                try {
                    dOut.writeUTF(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
}
