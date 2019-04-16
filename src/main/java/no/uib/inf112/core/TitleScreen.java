package no.uib.inf112.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.io.File;


public class TitleScreen implements Screen {

    private final String TITLE_SCREEN_FOLDER = "titlescreen" + File.separatorChar;

    private final Texture HEADER = new Texture(TITLE_SCREEN_FOLDER + "header.png");
    private final Texture PLAY_ON = new Texture(TITLE_SCREEN_FOLDER + "play_on.png");
    private final Texture PLAY_OFF = new Texture(TITLE_SCREEN_FOLDER + "play_off.png");
    private final Texture QUIT_ON = new Texture(TITLE_SCREEN_FOLDER + "quit_on.png");
    private final Texture QUIT_OFF = new Texture(TITLE_SCREEN_FOLDER + "quit_off.png");

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

        camera = new OrthographicCamera(width, height);//width * (height / width));
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();


    }

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setTransformMatrix(camera.view);
        game.batch.setProjectionMatrix(camera.projection);

        game.batch.begin();

        game.batch.draw(HEADER, 0, camera.viewportHeight / 2 + HEADER.getHeight() / 3f);

        if (mouseOn(PLAY_ON, 0)) {
            game.batch.draw(PLAY_ON, camera.viewportWidth / 2 - (PLAY_ON.getWidth() / 2f), camera.viewportHeight / 2 - (PLAY_ON.getHeight() / 2f));
            if (Gdx.input.justTouched()) {
                game.setScreen(game.gameScreen);
            }
        } else {
            game.batch.draw(PLAY_OFF, camera.viewportWidth / 2 - (PLAY_OFF.getWidth() / 2f), camera.viewportHeight / 2 - (PLAY_OFF.getHeight() / 2f));
        }

        if (mouseOn(QUIT_ON, 2)) {
            game.batch.draw(QUIT_ON, camera.viewportWidth / 2 - (QUIT_ON.getWidth() / 2f), camera.viewportHeight / 2 - ((5 * QUIT_ON.getHeight()) / 2f));
            if (Gdx.input.justTouched()) {
                System.exit(0);
            }
        } else {
            game.batch.draw(QUIT_OFF, camera.viewportWidth / 2 - (QUIT_OFF.getWidth() / 2f), camera.viewportHeight / 2 - ((5 * QUIT_OFF.getHeight()) / 2f));
        }


        game.batch.end();

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
