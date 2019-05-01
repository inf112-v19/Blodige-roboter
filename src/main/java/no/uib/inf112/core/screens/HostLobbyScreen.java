package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

import java.io.IOException;
import java.net.*;

public class HostLobbyScreen extends LobbyScreen {
    private String ipAddress;
    private GameGraphics game;
    private int port;

    HostLobbyScreen(GameGraphics game, boolean isHost, String ip, int port) throws IOException {
        super(game, isHost, ip, port);
        this.port = port;
        this.game = game;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ipAddress = socket.getLocalAddress().getHostAddress();
            if (ipAddress.equals("0.0.0.0")) ipAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
        Label ipPortLabel = game.createLabel("IP: " + ipAddress + " Port: " + port, stage.getWidth() / 2 - stage.getWidth() / 4, stage.getHeight() - 70, 50);
        TextButton startButton = createButton("START", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO check to see if enough players are connected
                GameScreen.scheduleSync(() -> client.startGame(game), 0);
            }
        });
        startButton.setPosition(3 * stage.getWidth() / 4 + 20, stage.getHeight() / 20);
        stage.addActor(ipPortLabel);
        stage.addActor(startButton);
        stage.addActor(new Actor());
        stage.addActor(new Actor());
    }

    @Override
    public void render(float v) {
        super.render(v);
        //TODO Render connectedplayers out of max players EX: 5/7
        int length = client.getPlayerNames().length;
        Label players = game.createLabel(length + "/" + GameGraphics.players, stage.getWidth() / 2, stage.getHeight() - 200, 50);
        stage.addActor(players);
    }
}
