package io.codeforall.bootcamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Server server;
    private Socket clientSocket;
    private String clientName;
    private BufferedReader input;
    private PrintWriter output;


    public ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.clientName = null;
        initStreamConnection();
    }

    public void initStreamConnection() {

        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String readMessage() {

        String message = null;

        try {
            message = input.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return message;

    }

    public void writeMessage(String clientMessage) {
        output.println(clientMessage);
    }



    public void disconnectStreams() {
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        writeMessage("Choose your user name please.");
        this.clientName = readMessage();
        writeMessage("Welcome " + this.clientName + " :) Enjoy the chat!");

        this.server.informConnection(this.clientName);

        while (clientSocket.isConnected()) {
            String clientMessage = readMessage();
            server.dispatch(this.clientSocket, this, clientMessage);
        }

    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getClientName() {
        return clientName;
    }

}
