package io.codeforall.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private int portNum;
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server(int portNum) {
        this.portNum = portNum;
        this.clientHandlers = new ArrayList<ClientHandler>();
        initServer();
    }

    public void initServer() {
        try {
            this.serverSocket = new ServerSocket(portNum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        listen();
    }

    public void listen() {

        ExecutorService cachedPool = Executors.newCachedThreadPool();

        while (true) {

            try {
                ClientHandler newClientHandler = new ClientHandler(this, serverSocket.accept());
                logger.log(Level.INFO, "new connection from " + getAddress(newClientHandler.getClientSocket()));
                clientHandlers.add(newClientHandler);
                cachedPool.submit(newClientHandler);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void dispatch(Socket clientSocket, ClientHandler clientHandler, String clientMessage) {

        String[] separatedMessage = null;

        if (clientMessage.charAt(0) == '/') {
            separatedMessage = clientMessage.split("--");
        } else {
            broadcast(clientSocket, clientHandler.getClientName(), clientMessage);
        }

        if (separatedMessage[0].equals("/quit")) {
            close(clientSocket, clientHandler.getClientName());
        } else if (separatedMessage[0].equals("/list")) {
            showList(clientSocket);
        } else if (separatedMessage[0].equals("/whisper")) {
            whisper(clientHandler.getClientName(), separatedMessage[1], separatedMessage[2]);
        }

    }

    public void broadcast(Socket clientSocket, String clientName, String clientMessage) {
        for (ClientHandler recipient : clientHandlers) {
            if (recipient.getClientSocket() != clientSocket) {
                recipient.writeMessage(clientName + ": " + clientMessage);
            }
        }
    }

    public void showList(Socket clientSocket) {

        ClientHandler currentClientHandle = null;

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getClientSocket() == clientSocket) {
                currentClientHandle = clientHandler;
            }
        }

        currentClientHandle.writeMessage("Users list:");

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != currentClientHandle) {
                currentClientHandle.writeMessage(clientHandler.getClientName());
            }
        }

    }

    public void whisper(String senderName, String recipientName, String message) {

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getClientName().equals(recipientName)) {
                clientHandler.writeMessage(senderName + " whispered to you: " + message);
            }
        }

    }


    public void close(Socket clientSocket, String clientName) {

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getClientSocket() == clientSocket) {
                clientHandler.writeMessage("You was disconnected.");
                clientHandler.disconnectStreams();
                clientHandlers.remove(clientHandler);
                informDisconnection(clientName);
            }
        }


        try {
            logger.log(Level.INFO, "closing client socket for " + getAddress(clientSocket));
            clientSocket.close();
        } catch (IOException e) {
            logger.log(Level.INFO, e.getMessage());
        }

    }

    public void informDisconnection(String clientName) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.writeMessage(clientName + " disconnected from chat :(");
        }
    }

    public void informConnection(String clientName) {

        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.getClientName().equals(clientName)) {
                clientHandler.writeMessage(clientName + " connected to the chat!");
            }
        }

    }

    public String getAddress(ServerSocket socket) {

        if (socket == null) {
            return null;
        }

        return socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort();
    }

    public String getAddress(Socket socket) {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort();
    }

}
