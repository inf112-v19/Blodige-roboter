package no.uib.inf112.core.multiplayer;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.multiplayer.dtos.*;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.util.ComparableTuple;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;


public class Server {

    List<ConnectedPlayer> players = new ArrayList<>();
    private Integer hostId;
    ServerSocket servSock;
    static Timer timer = new Timer();
    private static int seconds = 0;
    private boolean receivedCard = false;
    private boolean startedRound;

    public Server(int port, int numThreads) {


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
            player.setDaemon(true);
            player.start();
        }
        try {
            System.out.println("Hosting sever at " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        for (ConnectedPlayer player :
                players) {
            try {
                player.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            servSock.close();
        } catch (IOException e) {
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
            setDaemon(true);
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
            String command = line.substring(0, line.indexOf(":"));
            String data = line.substring(line.indexOf(":") + 1);
            System.out.println("receiving " + line);
            switch (command) {
                case "getName":
                    outToClient.print("threadName:" + getName() + "\r\n");
                    outToClient.flush();
                    break;
                case "setDisplayName":
                    player.name = data;
                    outToClient.print("name:" + player.name + "for" + getName() + "\r\n");
                    outToClient.flush();
                    sendMessageToAll("connectedPlayers:" + getConnectedPlayers());
                    break;
                case "getConnectedPlayers":
                    outToClient.print("connectedPlayers:" + getConnectedPlayers() + "\r\n");
                    outToClient.flush();
                    break;
                case "startGame":
                    startGame(player.id);
                    break;
                case "setSelectedCards":
                    setCards(GameGraphics.gson.fromJson(data, SelectedCardsDto.class));
                    if (!receivedCard) {
                        giveDisconnectedPlayersRandomCard();
                        startCountdown();
                        receivedCard = true;
                    }
                    readyToStart = true;
                    checkAllPlayersReady();
                    //user waits for rest of players
                    break;
                case "setHostId":
                    hostId = player.id;
                    break;
                case "finishedSetup":
                    startRound("giveCards:");
                    break;
                case "partyMode":
                    sendMessageToAll("partyMode:");
                    break;
                default:
                    System.out.println("Received from client " + threadNumber + ":" + line);
                    outToClient.print("error: Did not understand message" + "\r\n");
                    outToClient.flush();
                    break;

            }
        }

        private void setCards(SelectedCardsDto response) {
            player.isPoweredDown = response.poweredDown;
            player.cards = response.cards;
        }

        protected void close() throws IOException {
            if (outToClient != null) {
                outToClient.close();
            }
            handlerServSock.close();
            connected = false;
            interrupt();
        }
    }

    private void giveDisconnectedPlayersRandomCard() {
        for (ConnectedPlayer player : players) {
            if (player.player.name != null && !player.connected) {
                player.player.cards = SelectedCardsDto.drawRandomCards(player.player.drawnCards);
            }
        }
    }

    private void sendMessageToAll(String message) {
        for (ConnectedPlayer player : players) {
            if (player.player.name != null) {
                player.sendMessage(message);
            }
        }
    }

    private void startCountdown() {
        seconds = 0;
        TimerTask task = new TimerTask() {
            private final int MAX_SECONDS = 15;

            @Override
            public void run() {
                if (!startedRound && seconds < MAX_SECONDS) {
                    sendMessageToAll("countDown:" + GameGraphics.gson.toJson(seconds, Integer.class));
                    seconds++;
                } else if (!startedRound) {
                    for (ConnectedPlayer player : players) {
                        if (player.connected) {
                            if (!player.readyToStart) {
                                if (player.player.drawnCards != null) {
                                    player.player.cards = SelectedCardsDto.drawRandomCards(player.player.drawnCards);
                                }
                            }
                        }
                    }
                    startRound("startRound:");
                    cancel();
                } else {
                    cancel();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    private void checkAllPlayersReady() {
        for (ConnectedPlayer player : players) {
            if (player.connected) {
                if (!player.readyToStart) {
                    startedRound = false;
                    return;
                }
            }
        }
        startRound("startRound:");
    }

    private void startRound(String command) {
        startedRound = true;
        List<PlayerDto> players = new ArrayList<>();
        for (ConnectedPlayer player : this.players) {
            if (player.player.name != null) {
                players.add(player.player);
            }
        }
        GameGraphics.getRoboRally().getDeck().shuffle();
        for (ConnectedPlayer player : this.players) {
            if (player.player.name != null) {
                List<Card> cards = Arrays.asList(GameGraphics.getRoboRally().getDeck().draw(IPlayer.MAX_DRAW_CARDS));
                player.player.drawnCards = SelectedCardsDto.mapToDto(cards);
                player.sendMessage(command + GameGraphics.gson.toJson(new StartRoundDto(players, cards), StartRoundDto.class));
                player.readyToStart = false;
            }
        }
        receivedCard = false;
    }


    private String getConnectedPlayers() {
        List<PlayerDto> playerDtos = players.stream().map(connectedPlayer -> connectedPlayer.player).collect(Collectors.toList());
        return GameGraphics.gson.toJson(new ConnectedPlayersDto(playerDtos));
    }

    public void startGame(int id) {
        if (hostId == id) {
            Stack<ComparableTuple<String, Color>> colors = PlayerHandler.addColors();
            List<PlayerDto> playerDtos = new ArrayList<>();
            Iterator<ConnectedPlayer> iterator = players.iterator();
            while (iterator.hasNext()) {
                ConnectedPlayer player = iterator.next();
                if (player.player.name != null) {
                    player.player.color = colors.pop().value;
                    playerDtos.add(player.player);
                } else {
                    try {
                        player.interrupt();
                        player.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iterator.remove();
                }
            }
            NewGameDto newGameDto = new NewGameDto(GameGraphics.mapFileName, playerDtos, hostId);
            for (ConnectedPlayer player : players) {
                if (player.player.name != null) {
                    newGameDto.userId = player.player.id;
                    String message = "StartGame:" + GameGraphics.gson.toJson(newGameDto);
                    player.sendMessage(message);
                }
            }
        }
    }
}
