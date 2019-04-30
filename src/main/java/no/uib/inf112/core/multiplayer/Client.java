package no.uib.inf112.core.multiplayer;

import com.badlogic.gdx.Gdx;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.cards.Card;
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
import java.util.Arrays;
import java.util.List;

public class Client {

    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private String clientName;
    private Thread listener;
    private GameGraphics game;
    private MultiPlayerHandler playerHandler;
    private List<String> players;

    public Client(String IP, int port) throws IOException {
        clientSocket = new Socket(IP, port);
        System.out.println("Connected to server at" + clientSocket.getLocalAddress().getHostAddress());
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        clientName = getClientNameFromServer();

        players = new ArrayList<>();
        Thread listener = new Thread(() -> handleInput());
        listener.setDaemon(true);
        listener.start();
    }

    public List<String> getConnectedPlayers() {
        String result = "";
        try {
            outToServer.writeUTF("getConnectedPlayers:");
            result = inFromServer.readLine();
        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        players = Arrays.asList(result.split(","));
        return Arrays.asList(result.split(","));
    }

    public List<String> getPlayers() {
        return players;
    }

    public void handleInput() {
        String result = "first";
        while (!clientSocket.isClosed() && result != null) {
            try {
                result = inFromServer.readLine();
                System.out.println("FROM SERVER FOR " + clientName + ": " + result);
                if (result == null) {
                    System.out.println("Host disconnected");
                    Gdx.app.exit();
                }
                String command = result.substring(0, result.indexOf(":"));
                String data = result.substring(result.indexOf(":") + 1);
                switch (command) {
                    case "StartGame":
                        setupGame(data);
                        break;
                    case "giveCards":
                        giveCards(data);
                        break;
                    case "name":
                        clientName = data;
                        break;
                    case "connectedPlayers":
                        getConnectedPlayers();
                        //TODO receive a ConnectedPlayersDto, this happens everytime a new client is added
                        break;
                    case "threadName":
                        //Do nothing
                        break;
                    case "startRound":
                        playerHandler.runRound(GameGraphics.gson.fromJson(data, StartRoundDto.class));
                        break;
                    case "countDown":
                        //Do nothing
                        //int seconds = GameGraphics.gson.fromJson(data, Integer.class);
                        //TODO this seconds int has the information about the current number for the countdown
                        break;
                    case "partyMode":
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

    private void giveCards(String data) {
        GameScreen.scheduleSync(() ->
                playerHandler.startRound(GameGraphics.gson.fromJson(data, StartRoundDto.class)), 0);
    }

    private void setupGame(String data) {
        if (game == null) {
            throw new IllegalArgumentException("Trying to start game with a null reference to GameGraphics");
        }
        NewGameDto newGameDto = GameGraphics.gson.fromJson(data, NewGameDto.class);
        GameScreen.scheduleSync(() -> {
            game.setScreen(new GameScreen(game, newGameDto, this));
            writeToServer("finishedSetup:");
            IPlayerHandler playerHandler = GameGraphics.getRoboRally().getPlayerHandler();
            if (playerHandler instanceof MultiPlayerHandler) {
                this.playerHandler = (MultiPlayerHandler) playerHandler;
            } else {
                throw new IllegalStateException("Player handler is not for multiplayer");
            }

        }, 0);
    }

    public void setPartyMode() {
        writeToServer("partyMode:");
    }

    public boolean writeToServer(String text) {
        try {
            outToServer.writeUTF(text);
            return true;

        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        return false;
    }

    public String getClientNameFromServer() {
        try {
            outToServer.writeUTF("getName:");
        } catch (IOException e) {
            System.out.println("IOExeption " + e);
        }
        return clientName;
    }

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


    public void setName(String name) {
        writeToServer("setDisplayName:" + name);
    }


    public void startGame(GameGraphics game) {
        this.game = game;
        writeToServer("startGame:");
    }

    public void setSelectedCards(boolean poweredDown, List<Card> cards) {
        SelectedCardsDto message = new SelectedCardsDto(poweredDown, cards);
        writeToServer("setSelectedCards:" + GameGraphics.gson.toJson(message, SelectedCardsDto.class));

    }

    public void setHost() {
        writeToServer("setHostId:");
    }
}