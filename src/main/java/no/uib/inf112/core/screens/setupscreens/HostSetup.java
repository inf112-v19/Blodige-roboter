package no.uib.inf112.core.screens.setupscreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.screens.menuscreens.ErrorScreen;
import no.uib.inf112.core.screens.menuscreens.HostLobbyScreen;
import no.uib.inf112.core.screens.menuscreens.LobbyScreen;

import java.io.IOException;

public class HostSetup extends AbstractSetupScreen {

    private TextField portField;
    private int port;

    public HostSetup(GameGraphics game) {
        super(game);
    }

    @Override
    protected void startGame(String mainPlayerName) {
        GameGraphics.mainPlayerName = mainPlayerName;
        try {
            LobbyScreen lobbyScreen = new HostLobbyScreen(game, true, "localHost", port);
            game.setScreen(lobbyScreen);
            stage.clear();
        } catch (IOException e) {
            game.setScreen(new ErrorScreen(game, e.toString()));
        }
    }

    @Override
    public void show() {
        super.show();
        startButton.getStyle().disabledFontColor = new Color(0, 0, 0, 0.4f);
        startButton.setDisabled(true);

        portField = createDigitInputField(Integer.toString(GameGraphics.port), 5);
        portField.setPosition(7 * stage.getWidth() / 10, (3 * stage.getHeight() / 4) - portField.getHeight() * 1.5f);

        Label portFieldLabel = game.createLabel("Port: ", 7 * stage.getWidth() / 10 - portField.getWidth() / 2, (3 * stage.getHeight() / 4) - portField.getHeight() * 1.5f, 30);

        stage.addActor(portFieldLabel);
        stage.addActor(portField);
    }

    @Override
    public void render(float v) {
        super.render(v);

        startButton.setDisabled(!checkValidPort(portField.getText()));

        if (startGame && !startButton.isDisabled()) {
            port = Integer.parseInt(portField.getText());
            startGame(GameGraphics.mainPlayerName);
        } else {
            startGame = false;
        }
    }
}

