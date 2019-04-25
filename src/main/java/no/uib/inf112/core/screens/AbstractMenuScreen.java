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

    protected final BitmapFont screenFont;
    protected final BitmapFont screenFontBold;
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
        screenFont = game.generateFont("screen_font.ttf", 70);
        screenFontBold = game.generateFont("screen_font_bold.ttf", 70);
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

    protected TextButton createButton(String name, int relativePosition) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = screenFont;
        style.fontColor = Color.BLACK;
        TextButton button = new TextButton(name, style);
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.font = screenFontBold;
                button.setStyle(style);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.font = screenFont;
                button.setStyle(style);
            }
        });
        button.setHeight(screenFont.getLineHeight());
        button.setPosition(width / 2 - (button.getWidth() / 2), height / 2 - (relativePosition * button.getHeight() / 2));
        button.pad(2);
        return button;
    }

//    protected TextButton createReturnButton(String name, )
}
