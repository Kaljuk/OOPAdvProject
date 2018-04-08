package com.utoopproject.app;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {

    private DataInputStream dis;

    public ClientThread(Socket socket) throws IOException {
        dis = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println(dis.readUTF());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
