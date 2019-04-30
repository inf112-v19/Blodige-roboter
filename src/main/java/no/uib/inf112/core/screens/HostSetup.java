package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
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

        portField = createDigitInputField("Enter port", 5);
        portField.setPosition(7 * stage.getWidth() / 10, (3 * stage.getHeight() / 4) - portField.getHeight() * 1.5f);

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

