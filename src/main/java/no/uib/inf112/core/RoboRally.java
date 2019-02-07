package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.ui.UIHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RoboRally extends Game {

    public static final String MAP_FOLDER = "maps";

    //DO NOT PUT ASSET HERE!!! only this directory should be specified in the in the working directory
    //see https://github.com/inf112-v19/Blodtorstige-robotet/wiki/Run-with-IntelliJ
    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "test.tmx";

    private SpriteBatch batch;
    private BitmapFont font;


    private static TiledMapHandler map;
    private OrthographicCamera camera; //use this for UI

    //FIXME Issue #33: create a robot handler that handles all the players (as we can have between 2 and N robots)
    private Robot robot;
    private Robot robot2;

    InputMultiplexer inputMultiplexer;

    UIHandler uiHandler;


    /**
     * @return The current map in play
     */
    @NotNull
    public static MapHandler getCurrentMap() {
        return map;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        inputMultiplexer = new InputMultiplexer();

        uiHandler = new UIHandler();
        inputMultiplexer.addProcessor(uiHandler.getStage());
        inputMultiplexer.addProcessor(new InputHandler());

        Gdx.input.setInputProcessor(inputMultiplexer);

        map = new TiledMapHandler(FALLBACK_MAP_FILE_PATH);
        robot = new Robot(5, 5, Direction.NORTH);
        robot2 = new Robot(1, 1, Direction.SOUTH);
    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        super.render();

        batch.begin();

        map.update(Gdx.graphics.getDeltaTime());
        map.render(batch);

        uiHandler.update();

        batch.end();
    }


    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        map.resize();
    }
}
