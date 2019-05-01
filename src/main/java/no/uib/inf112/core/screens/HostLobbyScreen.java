package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

import java.io.IOException;
import java.net.*;

import static no.uib.inf112.core.GameGraphics.generateFont;

public class HostLobbyScreen extends LobbyScreen {
    private String ipAddress;
    private GameGraphics game;
    private int port;
    private BitmapFont font;
    private TextButton startButton;


    HostLobbyScreen(GameGraphics game, boolean isHost, String ip, int port) throws IOException {
        super(game, isHost, ip, port);
        this.port = port;
        this.game = game;
        font = generateFont(GameGraphics.SCREEN_FONT, 50);
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ipAddress = socket.getLocalAddress().getHostAddress();
            if ("0.0.0.0".equals(ipAddress)) ipAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        startButton = createButton("START", 80);
    }

    @Override
    public void show() {
        super.show();
        Label ipPortLabel = game.createLabel("IP: " + ipAddress + " Port: " + port, stage.getWidth() / 2 - stage.getWidth() / 4, stage.getHeight() - 70, 50);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO check to see if enough players are connected
                if (!startButton.isDisabled()) GameScreen.scheduleSync(() -> client.startGame(game), 0);
            }
        });

        startButton.getStyle().disabledFontColor = new Color(0, 0, 0, 0.4f);
        startButton.setDisabled(true);

        startButton.setPosition(3 * stage.getWidth() / 4 + 20, stage.getHeight() / 20);
        stage.addActor(ipPortLabel);
        stage.addActor(startButton);
        stage.addActor(new Actor());
//        stage.addActor(new Actor());
    }

    @Override
    public void render(float v) {
        super.render(v);
        int length = client.getPlayerNames().length;
        if (length == GameGraphics.players) {
            startButton.setDisabled(false);
        }

        Label players = createLabel(length + "/" + GameGraphics.players, stage.getWidth() / 2 - 70, stage.getHeight() - 200);
        stage.addActor(players);
    }


    private Label createLabel(String text, float x, float y) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        Label label = new Label(text, labelStyle);
        label.setColor(Color.BLACK);

        label.setPosition(x, y);
        return label;
    }
}
