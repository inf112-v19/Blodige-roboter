package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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
    private Stage stage;

    BitmapFont screenFont;
    BitmapFont screenFontBold;

    private float width;
    private float height;

    private boolean startGame = false;
    private boolean optionsScreen = false;

    public TitleScreen(GameGraphics game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        screenFont = generateFont("screen_font.ttf");
        screenFontBold = generateFont("screen_font_bold.ttf");
    }

    private BitmapFont generateFont(String fontFile) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 70;
        return fontGenerator.generateFont(parameter);
    }

    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        TextButton play = creatButton("PLAY", 1);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame = true;
            }
        });

        TextButton options = creatButton("OPTIONS", 3);
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsScreen = true;
            }
        });

        TextButton quit = creatButton("QUIT", 5);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        stage.addActor(play);
        stage.addActor(options);
        stage.addActor(quit);


    }

    private TextButton creatButton(String name, int relativePosition) {
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

        button.setPosition(width / 2 - (button.getWidth() / 2), height / 2 - (relativePosition * button.getHeight() / 2));
        button.pad(2);
        return button;
    }


    @Override
    public void render(float v) {

        Gdx.gl.glClearColor(0.5f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(v);
        stage.draw();

        game.batch.begin();
        game.batch.draw(HEADER, 0, camera.viewportHeight / 2 + HEADER.getHeight() / 3f);
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
