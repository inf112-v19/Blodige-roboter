package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.player.Robot;

public class RoboRally extends Game {

    private SpriteBatch batch;
    private BitmapFont font;

    public static final String FALLBACK_MAP_FILE = "assets/daniels_testmap.tmx";

    private TiledMapHandler map;
    private OrthographicCamera camera;
    private Robot robot;
    private Robot robot2;


    /**
     * @return The current map in play
     */
    public TiledMapHandler getCurrentMap() {
        return map;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        camera = new OrthographicCamera();
        camera.update();

        Gdx.input.setInputProcessor(new InputHandler(this));

        map = new TiledMapHandler(FALLBACK_MAP_FILE);
        robot = new Robot(5, 5, map);
        robot2 = new Robot(1, 1, map);
    }


    @Override
    public void render() {
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        batch.begin();

        map.update(Gdx.graphics.getDeltaTime());
        map.render(camera);

        camera.update();

        batch.end();
    }


    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
