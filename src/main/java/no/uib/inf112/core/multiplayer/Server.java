package no.uib.inf112.core.multiplayer;

import no.uib.inf112.core.multiplayer.jsonClasses.PlayerJson;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class Server {

    List<ConnectedPlayer> players = new ArrayList<>();

    public Server(int port, int numThreads) {
        ServerSocket servSock;

        try {
            servSock = new ServerSocket(port);

        } catch (IOException e) {
            /* Crash the server if IO fails. Something bad has happened */
            throw new RuntimeException("Could not create ServerSocket ", e);
        }

        // Create a series of threads and start them.
        for (int i = 0; i < numThreads; i++) {
            ConnectedPlayer player = new ConnectedPlayer(servSock, i);
            players.add(player);
            player.start();
        }
        try {
            System.out.println("Server at " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void startGame() {
        for (ConnectedPlayer player : players) {
            if (player.connected) {
                player.sendMessage("Game started");
            }
        }
    }

    /**
     * A Thread subclass to handle one client conversation.
     */
    class ConnectedPlayer extends Thread {
        ServerSocket handlerServSock;
        int threadNumber;
        boolean connected;
        PlayerJson player = new PlayerJson();
        private PrintWriter outToClient;

        /**
         * Construct a Handler.
         */
        ConnectedPlayer(ServerSocket s, int i) {
            handlerServSock = s;
            threadNumber = i;
            connected = false;
            setName("Thread " + threadNumber);
        }

        public void sendMessage(String message) {
            if (connected) {
                if (handlerServSock.isBound()) {
                    outToClient.print(message);
                }
            }
        }

        @Override
        public void run() {
            /* Wait for a connection. Synchronized on the ServerSocket
             * while calling its accept() method.
             */
            connected = true;
            while (connected) {
                connected = false;
                try {
                    System.out.println(getName() + " waiting");

                    Socket clientSocket;
                    // Wait here for the next connection.
                    synchronized (handlerServSock) {
                        clientSocket = handlerServSock.accept();
                    }
                    connected = true;
                    System.out.println(getName() + " starting, IP=" + clientSocket.getInetAddress());
                    DataInputStream inFromClient = new DataInputStream(clientSocket.getInputStream());
                    outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                    String line;
                    while ((line = inFromClient.readUTF()) != null) {
                        handleInput(line);
                    }
                } catch (EOFException e) {
                    System.out.println(getName() + " Disconnected");
                    connected = false;
                    try {
                        handlerServSock.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException ex) {
                    System.out.println(getName() + ": IO Error on socket " + ex);
                    return;
                }
            }
        }

        private void handleInput(String line) {
            String[] command = line.split(":");
            System.out.println("receiving " + line);
            switch (command[0]) {
                case "getName":
                    outToClient.print(getName() + "\r\n");
                    outToClient.flush();
                    break;
                case "setDisplayName":
                    player.name = command[1];
                    outToClient.print("Name:" + player.name + "for" + getName() + "\r\n");
                    outToClient.flush();
                    break;
                case "getConnectedPlayers":
                    System.out.println("getConnectedPlayers");
                    outToClient.print(getConnectedPlayers() + "\r\n");
                    outToClient.flush();
                    break;
                case "startGame":
                    startGame();
                    outToClient.print("Started game" + "\r\n");
                    outToClient.flush();
                default:
                    System.out.println("Received from client " + threadNumber + ":" + line);
                    outToClient.print("Did not understand message" + "\r\n");
                    outToClient.flush();

            }
        }
    }

    private String getConnectedPlayers() {
        StringBuilder result = new StringBuilder();
        for (ConnectedPlayer connectedPlayer : players) {
            if (connectedPlayer.handlerServSock.isBound()) {
                result.append(connectedPlayer.player.name == null ? "Player with no name" : connectedPlayer.player.name);
            }
        }
        System.out.println("Server:" + result.toString());
        return result.toString();
    }
}
