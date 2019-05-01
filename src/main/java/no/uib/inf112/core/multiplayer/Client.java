package no.uib.inf112.core.multiplayer;

import com.badlogic.gdx.Gdx;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.multiplayer.dtos.ConnectedPlayersDto;
import no.uib.inf112.core.multiplayer.dtos.NewGameDto;
import no.uib.inf112.core.multiplayer.dtos.SelectedCardsDto;
import no.uib.inf112.core.multiplayer.dtos.StartRoundDto;
import no.uib.inf112.core.player.IPlayerHandler;
import no.uib.inf112.core.player.MultiPlayerHandler;
import no.uib.inf112.core.screens.GameScreen;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Client {

    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private String clientName;
    private GameGraphics game;
    private MultiPlayerHandler playerHandler;
    private List<String> players;

    public Client(String IP, int port) throws IOException {
        clientSocket = new Socket(IP, port);
        System.out.println("Connected to server at" + clientSocket.getLocalAddress().getHostAddress());
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        requestClientNameFromServer();

        players = new ArrayList<>();
        Thread listener = new Thread(() -> handleInput());
        listener.setDaemon(true);
        listener.start();
    }

    /**
     * The listener thread is running this while loop waiting for input from the server and doing corresponding actions
     */
    public void handleInput() {
        String result = "";
        while (!clientSocket.isClosed() && result != null) {
            try {
                result = inFromServer.readLine();
                System.out.println("FROM SERVER FOR " + clientName + ": " + result);
                if (result == null) {
                    System.out.println("Host disconnected");
                    Gdx.app.exit();
                }
                ClientAction command = ClientAction.fromCommandString(result.substring(0, result.indexOf(":")));
                String data = result.substring(result.indexOf(":") + 1);
                switch (command) {
                    case startGame:
                        setupGame(data);
                        break;
                    case giveCards:
                        giveCards(data);
                        break;
                    case name:
                        clientName = data;
                        break;
                    case connectedPlayers:
                        receiveConnectedPlayers(data);
                        //TODO receive a ConnectedPlayersDto, this happens everytime a new client is added
                        break;
                    case threadName:
                        //Do nothing
                        break;
                    case startRound:
                        playerHandler.runRound(GameGraphics.gson.fromJson(data, StartRoundDto.class));
                        break;
                    case countDown:
                        //Do nothing
                        //int seconds = GameGraphics.gson.fromJson(data, Integer.class);
                        //TODO this seconds int has the information about the current number for the countdown
                        break;
                    case partyMode:
                        InputHandler.enableMode();
                        break;
                    default:
                        System.out.println("Unknown operation :" + result);
                        break;
                }
            } catch (IOException e) {
                System.out.println("IOExeption " + e);
            }
        }
    }

    /**
     * Set the received cards for the players
     *
     * @param data a startround dto containing cards for each player and the drawn cards for this instance's mainplayer
     */
    private void giveCards(String data) {
        GameScreen.scheduleSync(() ->
                playerHandler.startRound(GameGraphics.gson.fromJson(data, StartRoundDto.class)), 0);
    }

    /**
     * Start a new roborally game
     *
     * @param data a newGame dto containing parameters for the new game
     */
    private void setupGame(String data) {
        if (game == null) {
            throw new IllegalArgumentException("Tried to start game with a null reference to GameGraphics");
        }
        NewGameDto newGameDto = GameGraphics.gson.fromJson(data, NewGameDto.class);
        GameScreen.scheduleSync(() -> {
            game.setScreen(new GameScreen(game, newGameDto, this));
            writeToServer(ServerAction.finishedSetup + "");
            IPlayerHandler playerHandler = GameGraphics.getRoboRally().getPlayerHandler();
            if (playerHandler instanceof MultiPlayerHandler) {
                this.playerHandler = (MultiPlayerHandler) playerHandler;
            } else {
                throw new IllegalStateException("Player handler is not for multiplayer");
            }

        }, 0);
    }

    /**
     * Sends given text to the server
     *
     * @param text to send to the server
     * @return true if able to write to the server
     */
    private boolean writeToServer(String text) {
        try {
            outToServer.writeUTF(text);
            return true;

        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        return false;
    }

    /**
     * Receive the connected players parse them and put them in the player list.
     *
     * @return a list of the names of all the connected players
     */
    public List<String> receiveConnectedPlayers(String data) {
        ConnectedPlayersDto result = GameGraphics.gson.fromJson(data, ConnectedPlayersDto.class);
        if (result.players != null) {
            players = result.players.stream()
                    .filter(player -> player != null)
                    .map(player -> player.name)
                    .collect(Collectors.toList());
            return players;
        }
        return null;
    }


    /**
     * @return an array of all connected players
     */
    public String[] getPlayerNames() {
        return players.toArray(new String[players.size()]);
    }

    /**
     * Request this clients name from the server
     */
    public void requestClientNameFromServer() {
        writeToServer(ServerAction.getName + "");
    }

    /**
     * Send that party mode is on to the server
     */
    public void setPartyModeOn() {
        writeToServer(ServerAction.partyMode + "");
    }

    /**
     * Send given display name to the server
     *
     * @param name name to set
     */
    public void setName(String name) {
        writeToServer(ServerAction.setDisplayName + name);
    }

    /**
     * Send a message to the server that the game is started
     *
     * @param game game that have been started
     */
    public void startGame(GameGraphics game) {
        this.game = game;
        writeToServer(ServerAction.startGame + "");
    }

    /**
     * send the users selected moves(either cards or powereddown) to the server
     *
     * @param poweredDown true if player is to power down this turn
     * @param cards       selected cards
     */
    public void sendSelectedCards(boolean poweredDown, List<Card> cards) {
        SelectedCardsDto message = new SelectedCardsDto(poweredDown, cards);
        writeToServer(ServerAction.sendSelectedCards + GameGraphics.gson.toJson(message, SelectedCardsDto.class));

    }

    /**
     * Sets this client as responsible host on the server
     */
    public void setHost() {
        writeToServer(ServerAction.setHostId + "");
    }

    /**
     * Close the connections to the server
     */
    public void closeConnection() {
        try {
            //listener.interrupt();
            clientSocket.close();
            inFromServer.close();
            outToServer.close();
        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
    }

}