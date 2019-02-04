package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Robot;
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

    //FIXME create a robot handler that handles all the players (as we can have between 2 and N robots)
    private Robot robot;
    private Robot robot2;


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

        Gdx.input.setInputProcessor(new InputHandler());

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
