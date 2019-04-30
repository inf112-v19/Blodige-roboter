package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import no.uib.inf112.core.GameGraphics;

import java.io.IOException;


public class JoinScreen extends AbstractMenuScreen {

    private GameGraphics game;
    private TextButton joinButton;
    private TextField portField;
    private TextField ipField;
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

        joinButton = createButton("JOIN", 80);
        joinButton.getStyle().disabledFontColor = new Color(0, 0, 0, 0.4f);
        joinButton.setDisabled(true);
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinGame = true;
                GameGraphics.mainPlayerName = nameField.getText();
            }
        });
        joinButton.setPosition(stage.getWidth() / 2 + 10, stage.getHeight() / 20);


        portField = createDigitInputField("Enter port", 5);
        portField.setPosition(stage.getWidth() / 2 - portField.getWidth() / 2, stage.getHeight() / 2 - portField.getHeight());


        ipField = createInputField("Enter ip", 15);
        nameField.setPosition(stage.getWidth() / 2 - ipField.getWidth() / 2, stage.getHeight() / 3 - ipField.getHeight() / 2);

        /*TextButton startButton = createButton("Join", 80);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameGraphics.mainPlayerName = nameField.getText();
                game.setScreen(new LobbyScreen(game, false, ipField.getText(), 1100));
                stage.clear();
            }
        });
        startButton.setPosition(3 * stage.getWidth() / 4 + 20, stage.getHeight() / 20);*/
        stage.addActor(ipField);
        //stage.addActor(startButton);

        stage.addActor(returnButton);
        stage.addActor(joinButton);
        stage.addActor(nameField);
        stage.addActor(portField);
    }

    @Override
    public void render(float v) {
        super.render(v);

        joinButton.setDisabled(!checkValidPort(portField.getText()));

        if (joinGame && !joinButton.isDisabled()) {
            try {
                LobbyScreen lobbyScreen = new LobbyScreen(game, false, ipField.getText(), Integer.parseInt(portField.getText()));
                game.setScreen(lobbyScreen);
                stage.clear();
            } catch (IOException e) {
                e.printStackTrace();
                //TODO Handle not able to connect
            }
        } else {
            joinGame = false;
        }
    }

}
