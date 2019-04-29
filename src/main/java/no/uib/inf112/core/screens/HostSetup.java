package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.uib.inf112.core.GameGraphics;

public class HostSetup extends AbstractSetupScreen {

    private TextField portField;

    public HostSetup(GameGraphics game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        portField = createDigitInputField("Enter port", 5);
        portField.setPosition(7 * stage.getWidth() / 10, (3 * stage.getHeight() / 4) - portField.getHeight() * 1.5f);

        stage.addActor(portField);
    }

}

