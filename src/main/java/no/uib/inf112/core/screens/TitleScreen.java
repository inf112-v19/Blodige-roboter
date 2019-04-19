package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import no.uib.inf112.core.GameGraphics;

import java.io.File;


public class TitleScreen implements Screen {

    private final String TITLE_SCREEN_FOLDER = "titlescreen" + File.separatorChar;

    private final Drawable HEADER = new TextureRegionDrawable(new Texture(TITLE_SCREEN_FOLDER + "header.png"));

    private GameGraphics game;
    private Stage stage;
    private OrthographicCamera camera;

    private BitmapFont screenFont;
    private BitmapFont screenFontBold;

    private float width;
    private float height;

    private boolean startGame = false;
    private boolean optionsScreen = false;

    public TitleScreen(GameGraphics game) {
        this.game = game;
        camera = new OrthographicCamera();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Gdx.input.setInputProcessor(stage);
        screenFont = game.generateFont("screen_font.ttf", 70);
        screenFontBold = game.generateFont("screen_font_bold.ttf", 70);
    }


    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        TextButton play = game.createButton("PLAY", 70);
        positionButton(play, 1);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame = true;
            }
        });

        TextButton options = game.createButton("OPTIONS", 70);
        positionButton(options, 3);
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsScreen = true;
            }
        });

        TextButton credits = game.createButton("CREDITS", 70);
        positionButton(credits, 5);
        TextButton quit = game.createButton("QUIT", 70);
        positionButton(quit, 7);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        stage.addActor(play);
        stage.addActor(options);
        stage.addActor(credits);
        stage.addActor(quit);

    }

    private void positionButton(TextButton button, int relativePosition) {
        button.setPosition(width / 2 - (button.getWidth() / 2), height / 2 - (relativePosition * button.getHeight() / 2));
    }
    

    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        HEADER.draw(game.batch, 0, 2 * camera.viewportHeight / 3f - 20, camera.viewportWidth, camera.viewportHeight / 4f);
        game.batch.end();

        if (startGame) {
            game.setScreen(new GameScreen(game));
        }
        if (optionsScreen) {
            game.setScreen(new OptionsScreen(game));
        }
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
}
