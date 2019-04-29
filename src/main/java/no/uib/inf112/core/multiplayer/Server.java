package no.uib.inf112.core.multiplayer;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.multiplayer.jsonClasses.NewGameDto;
import no.uib.inf112.core.multiplayer.jsonClasses.PlayerDto;
import no.uib.inf112.core.multiplayer.jsonClasses.SelectedCardsDto;
import no.uib.inf112.core.multiplayer.jsonClasses.StartRoundDto;
import no.uib.inf112.core.player.IPlayer;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Server {

    List<ConnectedPlayer> players = new ArrayList<>();
    private Integer hostId;


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

    /**
     * A Thread subclass to handle one client conversation.
     */
    class ConnectedPlayer extends Thread {
        ServerSocket handlerServSock;
        int threadNumber;
        boolean connected = false;
        boolean readyToStart = false;
        PlayerDto player = new PlayerDto();
        private PrintWriter outToClient;

        /**
         * Construct a Handler.
         */
        ConnectedPlayer(ServerSocket s, int i) {
            handlerServSock = s;
            threadNumber = i;
            setName("Thread " + threadNumber);
            player.id = i;
        }

        private void sendMessage(String message) {
            if (connected) {
                if (handlerServSock.isBound()) {
                    outToClient.print(message + "\r\n");
                    outToClient.flush();
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
            System.out.println(line);
            String command = line.substring(0, line.indexOf(":"));
            String data = line.substring(line.indexOf(":") + 1);
            System.out.println("receiving " + line);
            switch (command) {
                case "getName":
                    outToClient.print(getName() + "\r\n");
                    outToClient.flush();
                    break;
                case "setDisplayName":
                    player.name = data;
                    outToClient.print("Name:" + player.name + "for" + getName() + "\r\n");
                    outToClient.flush();
                    break;
                case "getConnectedPlayers":
                    System.out.println("getConnectedPlayers");
                    outToClient.print(getConnectedPlayers() + "\r\n");
                    outToClient.flush();
                    break;
                case "startGame":
                    startGame(player.id);
                    break;
                case "setSelectedCards":
                    setCards(GameGraphics.gson.fromJson(data, SelectedCardsDto.class));
                    readyToStart = true;
                    checkAllPlayersReady();
                    //user waits for rest of players
                    break;
                case "setHostId":
                    hostId = player.id;
                default:
                    System.out.println("Received from client " + threadNumber + ":" + line);
                    outToClient.print("Did not understand message" + "\r\n");
                    outToClient.flush();
                    break;

            }
        }

        private void setCards(SelectedCardsDto response) {
            player.cards = response.cards;
        }
    }

    private void checkAllPlayersReady() {
        for (ConnectedPlayer player : players) {
            if (player.connected) {
                if (!player.readyToStart) {
                    return;
                }
            }
        }
        startRound();
    }

    private void startRound() {
        List<PlayerDto> players = new ArrayList<>();
        for (ConnectedPlayer player : this.players) {
            players.add(player.player);
        }
        GameGraphics.getRoboRally().getDeck().shuffle();
        for (ConnectedPlayer player : this.players) {
            List<Card> cards = Arrays.asList(GameGraphics.getRoboRally().getDeck().draw(IPlayer.MAX_DRAW_CARDS));
            player.sendMessage("startRound:" + GameGraphics.gson.toJson(new StartRoundDto(players, cards), StartRoundDto.class));
            player.readyToStart = false;
        }
    }


    private String getConnectedPlayers() {
        StringBuilder result = new StringBuilder();
        for (ConnectedPlayer connectedPlayer : players) {
            if (connectedPlayer.player.name != null) {
                result.append(connectedPlayer.player.name);
            }
        }
        System.out.println("Server:" + result.toString());
        return result.toString();
    }

    public void startGame(int id) {
        List<PlayerDto> playerDtos = new ArrayList<>();
        for (ConnectedPlayer player : players) {
            if (player.player.name != null) {
                playerDtos.add(player.player);
            }
        }


        if (hostId == id) {
            NewGameDto newGameDto = new NewGameDto(GameGraphics.mapFileName, playerDtos, hostId);
            System.out.println("not hitting this");
            for (ConnectedPlayer player : players) {
                if (player.connected) {
                    String message = "StartGame:" + GameGraphics.gson.toJson(newGameDto);
                    player.sendMessage(message);
                }
            }
        }

    }
}
