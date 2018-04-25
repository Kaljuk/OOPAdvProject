package com.utoopproject.app;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientInput implements Runnable {
    private DataInputStream dIn;

    public ClientInput(DataInputStream dIn) {
        this.dIn = dIn;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = dIn.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
