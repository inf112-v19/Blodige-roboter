package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import no.uib.inf112.core.GameGraphics;


public class ErrorScreen extends AbstractMenuScreen {

    private String errorMessage;

    public ErrorScreen(GameGraphics game, String errorMessage) {
        super(game);
        this.errorMessage = errorMessage;
    }

    @Override
    public void show() {
        Label error = game.createLabel(errorMessage, 0, stage.getHeight() / 2, 30);
        error.setX(stage.getWidth() / 2 - error.getWidth() / 2);

        TextButton returnButton = createReturnButton(70);
        returnButton.setPosition(stage.getWidth() / 2 - returnButton.getWidth() / 2, stage.getHeight() / 20);


        stage.addActor(error);
        stage.addActor(returnButton);
    }
}
