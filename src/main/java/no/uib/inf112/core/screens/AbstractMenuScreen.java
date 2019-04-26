package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    public void show() {

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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    TextButton createButton(String name, int fontSize) {
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

    void setPositionCentered(TextButton button, int relativeXPosition, int relativeYPosition) {
        button.setPosition(stage.getWidth() / 2 - (relativeXPosition * button.getWidth() / 2), stage.getHeight() / 2 - (relativeYPosition * button.getHeight() / 2));
    }

    /**
     * This method is to create a return button for submenu screens (like setup screen and options screen)
     *
     * @return A TextButton that returns to title screen. Will be centered horizontally and at the bottom vertically
     */
    TextButton createReturnButton(int fontSize) {
        TextButton returnButton = createButton("RETURN", fontSize);
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TitleScreen(game));
            }
        });
        return returnButton;
    }
}
