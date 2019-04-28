package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import no.uib.inf112.core.GameGraphics;

public abstract class AbstractMenuScreen implements Screen {

    protected final GameGraphics game;
    protected int width;
    protected int height;
    protected Stage stage;
    protected OrthographicCamera camera;

    public AbstractMenuScreen(GameGraphics game) {
        this.game = game;
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        stage.getViewport().update(width, height, true);
        camera.update();
    }

    @Override
    public void pause() {
        //No standard implementation
    }

    @Override
    public void resume() {
        //No standard implementation
    }

    @Override
    public void hide() {
        //No standard implementation
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected TextButton createButton(String name, int fontSize) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        BitmapFont font = game.generateFont(GameGraphics.SCREEN_FONT, fontSize);
        BitmapFont boldFont = game.generateFont(GameGraphics.SCREEN_FONT_BOLD, fontSize);
        style.font = font;
        style.fontColor = Color.BLACK;
        TextButton button = new TextButton(name, style);
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.font = boldFont;
                button.setStyle(style);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.font = font;
                button.setStyle(style);
            }
        });
        button.setHeight(font.getCapHeight());
        button.padBottom(5);
        return button;
    }

    protected void setPositionCentered(TextButton button, int relativeXPosition, int relativeYPosition) {
        button.setPosition(stage.getWidth() / 2 - (relativeXPosition * button.getWidth() / 2), stage.getHeight() / 2 - (relativeYPosition * button.getHeight() / 2));
    }

    /**
     * This method is to create a return button for submenu screens (like setup screen and options screen)
     *
     * @return A TextButton that returns to title screen. Will be centered horizontally and at the bottom vertically
     */
    protected TextButton createReturnButton(int fontSize) {
        TextButton returnButton = createButton("RETURN", fontSize);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        });
        return returnButton;
    }

    protected TextField createNameInputField() {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = GameGraphics.generateFont(GameGraphics.SCREEN_FONT, 30);
        textFieldStyle.fontColor = Color.BLACK;

        Pixmap myPixMap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        myPixMap.setColor(1, 1, 1, 0.5f);
        myPixMap.fillRectangle(0, 0, 1, 1);
        textFieldStyle.cursor = new TextureRegionDrawable(new Texture(myPixMap));
        textFieldStyle.disabledFontColor = Color.GRAY;
        textFieldStyle.background = new TextureRegionDrawable(new Texture(myPixMap));
        TextField nameField = new TextField("Enter name", textFieldStyle);
        nameField.setWidth(stage.getWidth() / 5);
        nameField.setAlignment(Align.center);
        nameField.setMaxLength(13);


        return nameField;
    }
}
