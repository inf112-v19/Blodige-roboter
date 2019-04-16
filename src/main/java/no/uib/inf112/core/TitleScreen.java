package no.uib.inf112.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


public class TitleScreen implements Screen {

    private Texture header = new Texture("header.png");
    private Texture play_on = new Texture("play_on.png");
    private Texture play_off = new Texture("play_off.png");

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

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }

        game.batch.setTransformMatrix(camera.view);
        game.batch.setProjectionMatrix(camera.projection);

        game.batch.begin();

        game.batch.draw(header, 0, camera.viewportHeight / 2 + header.getHeight() / 3f);

        float playX = -(play_on.getWidth() / 2f);
        float playY = -(play_on.getHeight() / 2f);


        if (Gdx.input.getX() >= width / 2 + playX && Gdx.input.getX() <= width / 2 + playX + play_on.getWidth() &&
                Gdx.input.getY() >= height / 2 + playY && Gdx.input.getY() <= height / 2 + playY + play_on.getHeight()) {

            game.batch.draw(play_on, camera.viewportWidth / 2 + playX, camera.viewportHeight / 2 + playY);
        } else {
            game.batch.draw(play_off, camera.viewportWidth / 2 + playX, camera.viewportHeight / 2 + playY);
        }

        game.batch.end();

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
