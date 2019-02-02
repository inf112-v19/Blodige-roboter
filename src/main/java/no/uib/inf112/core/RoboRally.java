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

public class RoboRally extends Game {

    private SpriteBatch batch;
    private BitmapFont font;

    //DO NOT PUT ASSET HERE!!! only this directory should be specified in the in the working directory
    //see https://github.com/inf112-v19/Blodtorstige-robotet/wiki/Run-with-IntelliJ
    public static final String FALLBACK_MAP_FILE = "test_map.tmx";

    private static TiledMapHandler map;
    private OrthographicCamera camera;

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

        Gdx.input.setInputProcessor(new InputHandler(this));

        map = new TiledMapHandler(FALLBACK_MAP_FILE);
        robot = new Robot(5, 5, Direction.NORTH);
        robot2 = new Robot(1, 1, Direction.SOUTH);
    }


    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
}
