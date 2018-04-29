package com.utoopproject.app;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ClientInput implements Runnable {
    private DataInputStream dIn;
    private Scanner scanner;

    public ClientInput(Scanner scanner, DataInputStream dIn) {
        this.dIn = dIn;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = dIn.readUTF();
                System.out.println(message);

                //ei tööta
                /*String pick = dIn.readUTF();
                switch (pick) {
                    case "All":
                        try {
                            String message = dIn.readUTF();
                            System.out.println(message);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    case "Private":
                        try{
                            String message = dIn.readUTF();
                            System.out.println(message);
                        } catch (IOException e){
                            throw new RuntimeException(e);
                        }
                    case "File":
                        String teade = dIn.readUTF();
                        System.out.println(teade);
                        if (scanner.nextLine().equals("y")){

                        }
                        break;
                }*/
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
