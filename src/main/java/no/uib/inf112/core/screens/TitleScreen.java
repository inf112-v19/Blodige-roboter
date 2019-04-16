package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import no.uib.inf112.core.GameGraphics;

import java.io.File;


public class TitleScreen implements Screen {

    private final String TITLE_SCREEN_FOLDER = "titlescreen" + File.separatorChar;

    private final Texture HEADER = new Texture(TITLE_SCREEN_FOLDER + "header.png");
    private final Texture PLAY_ON = new Texture(TITLE_SCREEN_FOLDER + "play_on.png");
    private final Texture PLAY_OFF = new Texture(TITLE_SCREEN_FOLDER + "play_off.png");
    private final Texture QUIT_ON = new Texture(TITLE_SCREEN_FOLDER + "quit_on.png");
    private final Texture QUIT_OFF = new Texture(TITLE_SCREEN_FOLDER + "quit_off.png");
    private final Texture OPTIONS_ON = new Texture(TITLE_SCREEN_FOLDER + "options_on.png");
    private final Texture OPTIONS_OFF = new Texture(TITLE_SCREEN_FOLDER + "options_off.png");

    private GameGraphics game;
    private OrthographicCamera camera;

    private float width;
    private float height;

    public TitleScreen(GameGraphics game) {
        this.game = game;
    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();


    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        game.batch.draw(HEADER, 0, camera.viewportHeight / 2 + HEADER.getHeight() / 3f);

        // Doing play button logic
        if (mouseOn(PLAY_ON, 0)) {
            draw(PLAY_ON, 1);
            if (Gdx.input.justTouched()) {
                game.setScreen(new GameScreen(game));
            }
        } else {
            draw(PLAY_OFF, 1);
        }


        if (mouseOn(OPTIONS_ON, 1)) {
            draw(OPTIONS_ON, 3);
            if (Gdx.input.justTouched()) {
                game.setScreen(new OptionsScreen(game));
            }
        } else {
            draw(OPTIONS_OFF, 3);
        }

        // Doing quit button logic
        if (mouseOn(QUIT_ON, 2)) {
            draw(QUIT_ON, 5);
            if (Gdx.input.justTouched()) {
                System.exit(0);
            }
        } else {
            draw(QUIT_OFF, 5);
        }


        game.batch.end();

    }

    private void draw(Texture toDraw, int scaler) {
        game.batch.draw(toDraw, camera.viewportWidth / 2 - (toDraw.getWidth() / 2f), camera.viewportHeight / 2 - ((scaler * toDraw.getHeight()) / 2f));
    }

    private boolean mouseOn(Texture currChoice, int placement) {
        float playX = currChoice.getWidth() / 2f;
        float playY = currChoice.getHeight() / 2f;

        return Gdx.input.getX() >= width / 2 - playX &&
                Gdx.input.getX() <= width / 2 + playX &&
                Gdx.input.getY() >= (placement * currChoice.getHeight()) + height / 2 - playY &&
                Gdx.input.getY() <= (placement * currChoice.getHeight()) + height / 2 + playY;
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
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

    }
}
