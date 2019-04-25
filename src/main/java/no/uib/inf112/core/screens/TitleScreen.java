package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;

import java.io.File;


public class TitleScreen extends AbstractMenuScreen {

    private final String TITLE_SCREEN_FOLDER = "titlescreen" + File.separatorChar;

    private final Drawable HEADER = new TextureRegionDrawable(new Texture(TITLE_SCREEN_FOLDER + "header.png"));

    private boolean startGame = false;
    private boolean setupScreen = false;
    private boolean optionsScreen = false;

    public TitleScreen(GameGraphics game) {
        super(game);
    }


    @Override
    public void show() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        TextButton play = createButton("PLAY", 1);
        positionButton(play, 1);

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame = true;
            }
        });

        TextButton setup = createButton("SETUP", 3);
        positionButton(setup, 3);
        setup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setupScreen = true;
            }
        });

        TextButton options = createButton("OPTIONS", 5);
        positionButton(options, 5);
        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionsScreen = true;
            }
        });

        TextButton quit = createButton("QUIT", 7);
        positionButton(quit, 7);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        stage.addActor(play);
        stage.addActor(setup);
        stage.addActor(options);
        stage.addActor(quit);

    }


    private void positionButton(TextButton button, int relativePosition) {
        button.setPosition(width / 2 - (button.getWidth() / 2), height / 2 - (relativePosition * button.getHeight() / 2));
    }

    @Override
    public void render(float v) {
        super.render(v);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        HEADER.draw(game.batch, 0, 2 * camera.viewportHeight / 3f - 20, camera.viewportWidth, camera.viewportHeight / 4f);
        game.batch.end();

        if (startGame) {
            game.setScreen(new GameScreen(game));
        }
        if (setupScreen) {
            game.setScreen(new SetupScreen(game));
        }
        if (optionsScreen) {
            game.setScreen(new OptionsScreen(game));
        }
    }
}
