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
        if (sc.hasNextInt()) {
            int option = sc.nextInt();

            if (option == 1) {
                Server server = new Server();
                server.startServer();
            } else if (option == 2) {
                sc.nextLine();

                // Ask the user for server IP address
                System.out.println("Enter the server IP address: ");
                String serverAddress = sc.nextLine();

                // Ask the user for username
                System.out.println("Enter your username: ");
                String username = sc.nextLine();

                // Start a new client
                Client client = new Client(serverAddress, username);
            } else {
                System.out.println("Invalid option!");
            }
        }
    }
}
