package no.uib.inf112.core.multiplayer;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.multiplayer.dtos.*;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.util.ComparableTuple;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;


public class Server {

    private List<ConnectedPlayer> players = new ArrayList<>();
    private int hostId;
    private ServerSocket servSock;
    private static Timer timer = new Timer();
    private static int seconds = 0;
    private boolean startedRound;
    private boolean countdownStarted = false;

    public Server(int port, int numThreads) throws IOException {
        servSock = new ServerSocket(port);

        // Create a series of threads and start them.
        for (int i = 0; i < numThreads; i++) {
            ConnectedPlayer player = new ConnectedPlayer(servSock, i);
            players.add(player);
            player.setDaemon(true);
            player.start();
        }
    }

    /**
     * Starts a new game if the calling client is the host
     *
     * @param id id of the client sending the request
     */
    private void startGame(int id) {
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
                    String message = ClientAction.START_GAME + GameGraphics.gson.toJson(newGameDto);
                    player.sendMessage(message);
                }
            }
        }
    }

    /**
     * Close connections to all clients
     */
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
        private ServerSocket handlerServSock;
        private int threadNumber;
        private boolean connected = false;
        private boolean readyToStart = false;
        private PlayerDto player = new PlayerDto();
        private PrintWriter outToClient;

        /**
         * Construct a Handler.
         *
         * @param threadNumber this connected players thread identification number
         */
        ConnectedPlayer(@NotNull ServerSocket s, int threadNumber) {
            handlerServSock = s;
            this.threadNumber = threadNumber;
            setName("Thread " + this.threadNumber);
            player.id = threadNumber;
            setDaemon(true);
        }

        /**
         * Send a message to the client
         *
         * @param message message to send
         */
        private void sendMessage(@NotNull String message) {
            if (connected && handlerServSock.isBound()) {
                outToClient.print(message + "\r\n");
                outToClient.flush();
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
                    Socket clientSocket;
                    // Wait here for the next connection.
                    synchronized (handlerServSock) {
                        clientSocket = handlerServSock.accept();
                    }
                    connected = true;
                    DataInputStream inFromClient = new DataInputStream(clientSocket.getInputStream());
                    outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                    String line;
                    while ((line = inFromClient.readUTF()) != null) {
                        handleInput(line);
                    }
                } catch (EOFException e) {
                    connected = false;
                    try {
                        handlerServSock.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (SocketException ex) {
                    //Socket closed by application
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        /**
         * Handle input from the client
         *
         * @param line the input
         */
        private void handleInput(String line) {
            ServerAction command = ServerAction.fromCommandString(line.substring(0, line.indexOf(":")));
            String data = line.substring(line.indexOf(":") + 1);
            switch (command) {
                case GET_NAME:
                    sendMessage(ClientAction.THREAD_NAME + getName());
                    break;
                case SET_DISPLAY_NAME:
                    player.name = data;
                    sendMessage(ClientAction.NAME + player.name + "for" + getName());
                    sendMessageToAll(ClientAction.CONNECTED_PLAYERS + getConnectedPlayers());
                    break;
                case GET_CONNECTED_PLAYERS:
                    sendMessage(ClientAction.CONNECTED_PLAYERS + getConnectedPlayers());
                    break;
                case START_GAME:
                    startGame(player.id);
                    break;
                case SEND_SELECTED_CARDS:
                    setCards(GameGraphics.gson.fromJson(data, SelectedCardsDto.class));
                    readyToStart = true;
                    if (shouldStartCountdown()) {
                        giveDisconnectedPlayersRandomCard();
                        startCountdown();
                        countdownStarted = true;
                    }
                    checkAllPlayersReady();
                    //user waits for rest of players
                    break;
                case SET_HOST_ID:
                    hostId = player.id;
                    break;
                case FINISHED_SETUP:
                    startRound(ClientAction.GIVE_CARDS);
                    break;
                case PARTY_MODE:
                    sendMessageToAll(ClientAction.PARTY_MODE + "");
                    break;
                default:
                    System.out.println("Dit not understand command received from client " + threadNumber + ":" + line);
                    sendMessage("error: Did not understand message");
                    break;

            }
        }

        /**
         * Set selected cards for this given client
         *
         * @param data SelectedCards dto containing data about the selected cards and power down status for this client
         */
        private void setCards(@NotNull SelectedCardsDto data) {
            player.isPoweredDown = data.poweredDown;
            player.cards = data.cards;
        }

        /**
         * Close the connection to this client
         *
         * @throws IOException if not able to close the streams and socket
         */
        private void close() throws IOException {
            if (outToClient != null) {
                outToClient.close();
            }
            handlerServSock.close();
            connected = false;
            interrupt();
        }
    }

    /**
     * Checks to see if the countdown should start,
     *
     * @return true if we are only waiting for one connected player
     */
    private boolean shouldStartCountdown() {
        System.out.println();
        int count = 0;
        for (ConnectedPlayer player : players) {
            if (player.connected && !player.readyToStart) {
                if (++count > 1) {
                    return false;
                }
            }
        }
        return count == 1 && !countdownStarted;

    }

    /**
     * Send a message to all clients
     *
     * @param message message to send
     */
    private void sendMessageToAll(@NotNull String message) {
        for (ConnectedPlayer player : players) {
            if (player.player.name != null) {
                player.sendMessage(message);
            }
        }
    }

    /**
     * Helper function giving disconnected players random cards
     */
    private void giveDisconnectedPlayersRandomCard() {
        for (ConnectedPlayer player : players) {
            if (player.player.name != null && !player.connected) {
                player.player.cards = DtoMapper.drawRandomCards(player.player.drawnCards);
            }
        }
    }

    /**
     * Starts the countdown
     */
    private void startCountdown() {
        seconds = 0;
        TimerTask task = new TimerTask() {
            private final int MAX_SECONDS = 31;

            @Override
            public void run() {
                if (!startedRound && seconds < MAX_SECONDS) {
                    sendMessageToAll(ClientAction.COUNT_DOWN + GameGraphics.gson.toJson(seconds, Integer.class));
                    seconds++;
                } else if (!startedRound) {
                    for (ConnectedPlayer player : players) {
                        if (player.connected && !player.readyToStart && player.player.drawnCards != null) {
                            player.player.cards = DtoMapper.drawRandomCards(player.player.drawnCards);
                        }
                    }
                    countdownStarted = false;
                    startRound(ClientAction.START_ROUND);
                    cancel();
                } else {
                    countdownStarted = false;
                    cancel();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    /**
     * Checks to see if all players have selected their cards
     */
    private void checkAllPlayersReady() {
        for (ConnectedPlayer player : players) {
            if (player.connected && !player.readyToStart) {
                startedRound = false;
                return;
            }
        }
        startRound(ClientAction.START_ROUND);
    }

    /**
     * Starts the given round
     *
     * @param command command to send with the dto either startround or GIVE_CARDS
     */
    private void startRound(@NotNull ClientAction command) {
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
                player.player.drawnCards = DtoMapper.mapToDto(cards);
                player.sendMessage(command + GameGraphics.gson.toJson(new StartRoundDto(players, cards), StartRoundDto.class));
                player.readyToStart = false;
            }
        }
    }

    /**
     * Collects playerDtos from all connected players and creates a ConnectedPlayers dto
     *
     * @return json object of all connected players
     */
    @NotNull
    private String getConnectedPlayers() {
        List<PlayerDto> playerDtos = players.stream().map(connectedPlayer -> {
            if (connectedPlayer.connected) {
                return connectedPlayer.player;
            }
            return null;
        }).collect(Collectors.toList());
        return GameGraphics.gson.toJson(new ConnectedPlayersDto(playerDtos));
    }

}
