package no.uib.inf112.core.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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

        stage.addActor(returnButton);
        stage.addActor(createNameInputField());
    }

    private TextField createNameInputField() {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = game.generateFont("screen_font.ttf", 50);
        textFieldStyle.fontColor = Color.WHITE;

        Pixmap myPixMap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        myPixMap.setColor(1, 1, 1, 0.5f);
        myPixMap.fillRectangle(0, 0, 1, 1);
        textFieldStyle.cursor = new TextureRegionDrawable(new Texture(myPixMap));
        textFieldStyle.disabledFontColor = Color.GRAY;
        textFieldStyle.background = new TextureRegionDrawable(new Texture(myPixMap));
        TextField nameField = new TextField("Enter name", textFieldStyle);
        nameField.setWidth(stage.getWidth() / 3);
        nameField.setAlignment(Align.center);
        nameField.setMaxLength(13);
        nameField.setPosition(stage.getWidth() / 2 - nameField.getWidth() / 2, stage.getHeight() / 2 - nameField.getHeight() / 2);

        return nameField;
    }
}
