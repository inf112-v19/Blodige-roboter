package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RoboRally extends Game {

    public static final String MAP_FOLDER = "maps";
    public static final int PHASES_PER_ROUND = 5;
    //DO NOT PUT ASSET HERE!!! only this directory should be specified in the in the working directory
    //see https://github.com/inf112-v19/Blodtorstige-robotet/wiki/Run-with-IntelliJ
    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "test.tmx";

    private SpriteBatch batch;
    private BitmapFont font;

    private static TiledMapHandler map;

    private static PlayerHandler playerHandler;

    private static InputMultiplexer inputMultiplexer;
    private UIHandler uiHandler;


    private static ControlPanelEventHandler cpEventHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        uiHandler = new UIHandler();
        new InputHandler();
        cpEventHandler = new ControlPanelEventHandler();
        map = new TiledMapHandler(FALLBACK_MAP_FILE_PATH);
        playerHandler = new PlayerHandler(2);
    }

    public static void round() {
        for (int i = 0; i < PHASES_PER_ROUND; i++) {
            for (Player player : playerHandler.getPlayers()) {
                // Decide which robot moves
                // Move robots in order

                if (player.isPoweredDown()) {
                    System.out.println("Player is powered down");
                    continue;
                }

                player.doTurn();
                System.out.println("moved!");
            }
            // End of robot movement

            // Activate lasers

            // Move rotation gears

            // Move assembly lines

            //Should wait some time
        }
        //User plans next round
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
        uiHandler.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        map.resize();
        uiHandler.resize();
    }

    /**
     * @return The current map in play
     */
    @NotNull
    public static MapHandler getCurrentMap() {
        return map;
    }

    @NotNull
    public static InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }

    @NotNull
    public static ControlPanelEventHandler getCPEventHandler() {
        return cpEventHandler;
    }

    @NotNull
    public static PlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}
