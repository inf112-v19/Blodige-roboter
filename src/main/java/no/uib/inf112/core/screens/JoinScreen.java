package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
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

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = game.generateFont("screen_font.ttf", 50);
        textFieldStyle.fontColor = Color.BLACK;
        //textFieldStyle.disabledFontColor
        //TextField nameField = new TextField("", )

    }
}
