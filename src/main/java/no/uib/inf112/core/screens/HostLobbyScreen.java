package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

import java.io.IOException;

public class HostLobbyScreen extends LobbyScreen {

    HostLobbyScreen(GameGraphics game, boolean isHost, String ip, int port) throws IOException {
        super(game, isHost, ip, port);
    }

    @Override
    public void show() {
        super.show();

        TextButton startButton = createButton("START", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO check to see if enough players are connected
                GameScreen.scheduleSync(() -> client.startGame(game), 0);
            }
        });
        stage.addActor(startButton);
        stage.addActor(new Actor());
    }
}
