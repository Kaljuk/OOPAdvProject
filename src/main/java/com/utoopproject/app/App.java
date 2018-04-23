package com.utoopproject.app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {

        // Launch app
        System.out.println("Launching application, options:");

        // Ask if app turns into a server or client
        Scanner sc = new Scanner(System.in);
        System.out.println("1. launch server");
        System.out.println("2. launch client");

        // Check the input and launch the selected option
        // Check input
        int appChoice = scannerInt(sc);
        
        if (appChoice == 1) {
            Server server = new Server();
            server.startServer();
        } else if (appChoice == 2) {
            sc.nextLine();

            // Ask the user for server IP address
            System.out.println("Enter the server IP address: ");
            String serverAddress = sc.nextLine();

            // Ask the user for username
            System.out.println("Enter your username: ");
            String username = sc.nextLine();

            // Start a new client
            Client client = new Client(serverAddress, username);
            // Client.start(); ?
        }
    }

    /**
     * @param  scan - Scanner entity
     * @return Integer as from scanner
     */
    private static int scannerInt(Scanner scan) {
        boolean gotInt = false;
        int scannedInt = 0;
        while (!gotInt) {
            try {
                scannedInt = Integer.valueOf(scan.nextLine());
                gotInt     = true;
            } catch (NumberFormatException e) {
                gotInt     = false;
                System.out.print("\nIntegers only, please try again: ");
            }
        }
        return scannedInt;
    }
    /**
     * @param  scan - Scanner entity
     * @param  errorMsg - Message to show when user input is different from needed
     * @return Integer as from scanner
     */
    private static int scannerInt(Scanner scan, String errorMsg) {
        boolean gotInt = false;
        int scannedInt = 0;
        while (!gotInt) {
            try {
                scannedInt = Integer.valueOf(scan.nextLine());
                gotInt     = true;
            } catch (NumberFormatException e) {
                gotInt     = false;
                System.out.print(errorMsg);
            }
        }
        return scannedInt;
    }
}
