package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.uib.inf112.core.GameGraphics;

public class HostSetup extends AbstractSetupScreen {

    private TextField portField;

    public HostSetup(GameGraphics game) {
        super(game);
    }

    @Override
    protected void startGame(String mainPlayerName) {
        GameGraphics.mainPlayerName = mainPlayerName;
        game.setScreen(new LobbyScreen(game, true, "localHost", 1100));
        stage.clear();
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
            // TODO join game at port portNb
        } else {
            startGame = false;
        }

    }

}

