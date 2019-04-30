package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.multiplayer.Client;
import no.uib.inf112.core.multiplayer.Server;

import java.util.List;

public class LobbyScreen extends AbstractMenuScreen {

    protected final Client client;
    private final Server server;
    List<String> connectedPlayers;

    LobbyScreen(GameGraphics game, boolean isHost, String ip, int port) {
        super(game);
        if (isHost) {
            server = new Server(port, 8);
            GameGraphics.setServer(server);
            client = new Client(ip, port);
            client.setName(GameGraphics.mainPlayerName);
            client.getClientNameFromServer();
            client.setHost();
            GameGraphics.setClient(client);
        } else {
            server = null;
            client = new Client(ip, port);
            client.setName(GameGraphics.mainPlayerName);
            client.startGame(game);
            GameGraphics.setClient(client);
        }
        connectedPlayers = client.getConnectedPlayers();
    }

    @Override
    public void show() {
        TextButton returnButton = createReturnButton(50);
        returnButton.setPosition(3 * stage.getWidth() / 4 - returnButton.getWidth() - 10, stage.getHeight() / 20);

        String[] players = new String[GameGraphics.players];
        for (int i = 0; i < GameGraphics.players; i++) {
            players[i] = "Not connected";
        }
        int i = 0;
        for (String connectedPlayer : connectedPlayers) {
            players[i++] = connectedPlayer;
        }

        com.badlogic.gdx.scenes.scene2d.ui.List<String> list = createList(players);
        stage.addActor(list);
        stage.addActor(returnButton);
    }
}