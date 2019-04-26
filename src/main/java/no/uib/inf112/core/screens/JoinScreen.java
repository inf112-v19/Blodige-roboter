package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.uib.inf112.core.GameGraphics;


public class JoinScreen extends AbstractMenuScreen {

    private GameGraphics game;

    public JoinScreen(GameGraphics game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        TextButton returnButton = createReturnButton(70);
        returnButton.setPosition(stage.getWidth() / 2 - returnButton.getWidth(), stage.getHeight() / 20);

        TextField nameField = createNameInputField();
        nameField.setPosition(stage.getWidth() / 2 - nameField.getWidth() / 2, stage.getHeight() / 2 - nameField.getHeight() / 2);
        stage.addActor(returnButton);
        stage.addActor(nameField);
    }

}
