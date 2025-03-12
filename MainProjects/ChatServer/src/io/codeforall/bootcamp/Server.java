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

    public void dispatch(Socket clientSocket, String clientMessage) {

        if (clientMessage.equals("/quit")) {
            close(clientSocket);

        } else if (clientMessage.equals("/list")) {
            showList(clientSocket);

        } else {
            broadcast(clientSocket, clientMessage);
        }

    }

    public void broadcast(Socket clientSocket, String clientMessage) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getClientSocket() != clientSocket) {
                clientHandler.writeMessage(clientMessage);
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

        currentClientHandle.writeMessage("These are the current users:");

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != currentClientHandle) {
                currentClientHandle.writeMessage(clientHandler.getClientName());
            }
        }

    }

    private void close(Socket clientSocket) {

        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getClientSocket() == clientSocket) {
                clientHandler.writeMessage("You was disconnected.");
                clientHandler.disconnectStreams();
                clientHandlers.remove(clientHandler);
            }
        }

        try {
            logger.log(Level.INFO, "closing client socket for " + getAddress(clientSocket));
            clientSocket.close();
        } catch (IOException e) {
            logger.log(Level.INFO, e.getMessage());
        }

    }

    private String getAddress(ServerSocket socket) {

        if (socket == null) {
            return null;
        }

        return socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort();
    }

    private String getAddress(Socket socket) {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort();
    }

}
