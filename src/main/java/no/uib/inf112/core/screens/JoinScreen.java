package no.uib.inf112.core.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;


public class JoinScreen extends AbstractMenuScreen {

    private GameGraphics game;

    public JoinScreen(GameGraphics game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        TextButton returnButton = createReturnButton(70);
        returnButton.setPosition(stage.getWidth() / 2 - returnButton.getWidth(), stage.getHeight() / 20);

        TextField nameField = createNameInputField();
        nameField.setPosition(stage.getWidth() / 2 - nameField.getWidth() / 2, stage.getHeight() / 2 - nameField.getHeight() / 2);
        TextField ipField = createNameInputField();
        nameField.setPosition(stage.getWidth() / 2 - ipField.getWidth() / 2, stage.getHeight() / 3 - ipField.getHeight() / 2);

        TextButton startButton = createButton("Join", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameGraphics.mainPlayerName = nameField.getText();
                game.setScreen(new LobbyScreen(game, false, ipField.getText(), 1100));
                stage.clear();
            }
        });
        startButton.setPosition(3 * stage.getWidth() / 4 + 20, stage.getHeight() / 20);
        stage.addActor(ipField);
        stage.addActor(startButton);
        stage.addActor(returnButton);
        stage.addActor(nameField);
    }

}
