package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.multiplayer.Client;
import no.uib.inf112.core.multiplayer.Server;

import java.util.List;

public class LobbyScreen extends AbstractMenuScreen {


    private final Client client;
    private final Server server;
    private final boolean isHost;

    List<String> connectedPlayers;

    LobbyScreen(GameGraphics game, boolean isHost, String ip, int port) {
        super(game);
        this.isHost = isHost;
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
        TextButton startButton = createButton("START", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO check to see if enough players are connected
                GameScreen.scheduleSync(() -> client.startGame(game), 0);
            }
        });
        //stage.addActor(renderPlayerList());
        stage.addActor(returnButton);
        if (isHost) {
            stage.addActor(startButton);
        }
    }

    private Actor renderPlayerList() {
        Skin skin = new Skin();
        skin.add("logo", new Texture("ui/cardSkins/emptySlot.png"));
        Table table = new Table(skin);
        connectedPlayers.forEach((player) -> {
            table.add(player);
            table.row();
        });
        return table;
    }
}