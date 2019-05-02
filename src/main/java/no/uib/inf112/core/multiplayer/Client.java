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
import no.uib.inf112.core.screens.menuscreens.TitleScreen;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Client implements IClient {

    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    private GameGraphics game;
    private MultiPlayerHandler playerHandler;
    private List<String> players;

    public Client(@NotNull String IP, int port) throws IOException {
        clientSocket = new Socket(IP, port);
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        requestClientNameFromServer();

        players = new ArrayList<>();
        Thread listener = new Thread(this::handleInput);
        listener.setDaemon(true);
        listener.start();
    }

    /**
     * The listener thread is running this while loop waiting for input from the server and doing corresponding actions
     */
    private void handleInput() {
        String result = "";
        while (!clientSocket.isClosed() && result != null) {
            try {
                result = inFromServer.readLine();
                if (result == null || result.equals("null")) {
                    GameScreen.scheduleSync(() -> game.setScreen(new TitleScreen(game)), 0);
                    System.out.println("Host disconnected");
                    return;
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
                        //clientName = data;
                        // Only used to check connectivity
                        break;
                    case connectedPlayers:
                        receiveConnectedPlayers(data);
                        break;
                    case threadName:
                        //Do nothing
                        break;
                    case startRound:
                        GameScreen.getUiHandler().updateCountDown(0);
                        playerHandler.runRound(GameGraphics.gson.fromJson(data, StartRoundDto.class));
                        break;
                    case countDown:
                        // This seconds int has the information about the current number for the countdown
                        int seconds = 30 - GameGraphics.gson.fromJson(data, Integer.class); // Count down, not count up
                        GameScreen.getUiHandler().updateCountDown(seconds);
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
    private void giveCards(@NotNull String data) {
        GameScreen.scheduleSync(() ->
                playerHandler.startRound(GameGraphics.gson.fromJson(data, StartRoundDto.class)), 0);
    }

    /**
     * Start a new roborally game
     *
     * @param data a newGame dto containing parameters for the new game
     */
    private void setupGame(@NotNull String data) {
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
    private boolean writeToServer(@NotNull String text) {
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
    private List<String> receiveConnectedPlayers(@NotNull String data) {
        ConnectedPlayersDto result = GameGraphics.gson.fromJson(data, ConnectedPlayersDto.class);
        if (result.players != null) {
            players = result.players.stream()
                    .filter(Objects::nonNull)
                    .map(player -> player.name)
                    .collect(Collectors.toList());
            return players;
        }
        return null;
    }

    /**
     * Request this clients name from the server
     */
    private void requestClientNameFromServer() {
        writeToServer(ServerAction.getName + "");
    }

    @Override
    @NotNull
    public String[] getPlayerNames() {
        return players.toArray(new String[0]);
    }

    @Override
    public void setPartyModeOn() {
        writeToServer(ServerAction.partyMode + "");
    }

    @Override
    public void setName(@NotNull String name) {
        writeToServer(ServerAction.setDisplayName + name);
    }

    @Override
    public void startGame(@NotNull GameGraphics game) {
        this.game = game;
        writeToServer(ServerAction.startGame + "");
    }

    @Override
    public void sendSelectedCards(boolean poweredDown, @NotNull List<Card> cards) {
        SelectedCardsDto message = new SelectedCardsDto(poweredDown, cards);
        writeToServer(ServerAction.sendSelectedCards + GameGraphics.gson.toJson(message, SelectedCardsDto.class));

    }

    @Override
    public void setHost() {
        writeToServer(ServerAction.setHostId + "");
    }

    @Override
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