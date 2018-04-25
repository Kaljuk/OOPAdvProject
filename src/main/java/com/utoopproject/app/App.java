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
        int appChoice = ScannerMethods.scannerInt(sc, "1. launch server\n2. launch client\n", new int[]{1,2});
        
        if (appChoice == 1) {
            Server server = new Server();
            server.startServer();
        } else if (appChoice == 2) {
            
            // Ask the user for server IP address
            System.out.println("Enter the server IP address: ");
            String serverAddress = ScannerMethods.scannerString(sc, "Not Fitting input");

            // Ask the user for username
            System.out.println("Enter your username: ");
            String username = ScannerMethods.scannerString(sc, "");

            // Start a new client
            Client client = new Client(serverAddress, username);
            // Client.start(); ?
        }

        // Close scanner 
        sc.close();
    }
}
    