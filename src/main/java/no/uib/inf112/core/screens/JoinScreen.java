package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;


public class JoinScreen extends AbstractMenuScreen {

    private GameGraphics game;
    private TextButton joinButton;
    private TextField portField;
    private boolean joinGame;

    public JoinScreen(GameGraphics game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        TextButton returnButton = createReturnButton(60);
        returnButton.setPosition(stage.getWidth() / 2 - returnButton.getWidth() - 10, stage.getHeight() / 20);


        TextField nameField = createInputField("Enter name", 13);
        nameField.setPosition(stage.getWidth() / 2 - nameField.getWidth() / 2, stage.getHeight() / 2 + nameField.getHeight() / 2);

        portField = createDigitInputField(Integer.toString(GameGraphics.port), 5);
        portField.setPosition(stage.getWidth() / 2 - portField.getWidth() / 2, stage.getHeight() / 2 - portField.getHeight());


        TextField ipField = createInputField("Enter ip", 15);
        ipField.setPosition(stage.getWidth() / 2 - ipField.getWidth() / 2, stage.getHeight() / 2 - 5 * ipField.getHeight() / 2);


        joinButton = createButton("JOIN", 80);
        joinButton.getStyle().disabledFontColor = new Color(0, 0, 0, 0.4f);
        joinButton.setDisabled(true);
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameGraphics.mainPlayerName = nameField.getText();
                game.setScreen(new LobbyScreen(game, false, ipField.getText(), 1100));
                stage.clear();
            }
        });
        joinButton.setPosition(stage.getWidth() / 2 + 20, stage.getHeight() / 20);

        stage.addActor(ipField);

        stage.addActor(returnButton);
        stage.addActor(joinButton);
        stage.addActor(nameField);
        stage.addActor(portField);
    }

    @Override
    public void render(float v) {
        super.render(v);

        joinButton.setDisabled(!checkValidPort(portField.getText()));

    }

}
