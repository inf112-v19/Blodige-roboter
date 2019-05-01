package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class HostLobbyScreen extends LobbyScreen {
    private String ipAdress;
    private GameGraphics game;

    HostLobbyScreen(GameGraphics game, boolean isHost, String ip, int port) throws IOException {
        super(game, isHost, ip, port);
        this.game = game;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ipAdress = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();

        Label label = game.createLabel(ipAdress, stage.getWidth() / 2, stage.getHeight() / 4, 50);
        TextButton startButton = createButton("START", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO check to see if enough players are connected
                GameScreen.scheduleSync(() -> client.startGame(game), 0);
            }
        });
        stage.addActor(label);
        stage.addActor(startButton);
        stage.addActor(new Actor());
    }
}
