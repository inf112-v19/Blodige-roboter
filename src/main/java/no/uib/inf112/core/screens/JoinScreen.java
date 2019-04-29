package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.uib.inf112.core.GameGraphics;


public class JoinScreen extends AbstractMenuScreen {

    private GameGraphics game;
    TextButton joinButton;
    TextField portField;

    public JoinScreen(GameGraphics game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        TextButton returnButton = createReturnButton(60);
        returnButton.setPosition(stage.getWidth() / 2 - returnButton.getWidth() - 10, stage.getHeight() / 20);

        joinButton = createButton("JOIN", 80);
        joinButton.getStyle().disabledFontColor = new Color(0, 0, 0, 0.4f);
        joinButton.setDisabled(true);
        joinButton.setPosition(stage.getWidth() / 2 + 10, stage.getHeight() / 20);

        TextField nameField = createInputField("Enter name", 13);
        nameField.setPosition(stage.getWidth() / 2 - nameField.getWidth() / 2, stage.getHeight() / 2 + nameField.getHeight() / 2);

        portField = createDigitInputField("Enter port", 5);
        portField.setPosition(stage.getWidth() / 2 - portField.getWidth() / 2, stage.getHeight() / 2 - portField.getHeight());


        stage.addActor(returnButton);
        stage.addActor(joinButton);
        stage.addActor(nameField);
        stage.addActor(portField);
    }

    @Override
    public void render(float v) {
        super.render(v);

        int portNb;
        try {
            portNb = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            portNb = -1;
        }
        if (49152 <= portNb && 65535 >= portNb) {
            joinButton.setDisabled(false);
        } else {
            joinButton.setDisabled(true);
        }
    }

}
