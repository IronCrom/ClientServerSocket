package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(8787)) {
            System.out.println("Server is started!");
            Thread thread = new Thread(() -> {
                try (Socket clientSocket = new Socket("localhost", 8787);
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    writer.println("Andrey");
                    System.out.println(reader.readLine());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            thread.start();

            try (Socket client = serverSocket.accept();
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))
            ) {
                String name = in.readLine();
                out.println(String.format("Hi %s, your port is %d", name, client.getPort()));
            }
        }
    }
}