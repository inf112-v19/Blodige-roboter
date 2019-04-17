package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.ui.SoundPlayer;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameGraphics extends Game {

    private static RoboRally roboRally;
    public static boolean HEADLESS;
    private static SoundPlayer soundPlayer;

    public static final String MAP_FOLDER = "maps";
    //  public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "checkmate.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "dizzy_dash.tmx";
    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "island_hop.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "chop_shop_challenge.tmx";

    public static int flagCount;

    private SpriteBatch batch;

    private static InputMultiplexer inputMultiplexer;
    private static UIHandler uiHandler;
    private static ControlPanelEventHandler cpEventHandler;
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void create() {

        batch = new SpriteBatch();

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        cpEventHandler = new ControlPanelEventHandler();

        getRoboRally();
        findFlags();
        uiHandler = new UIHandler();
        new InputHandler(); //this must be after UIHandler to allow dragging of cards
        getRoboRally().getPlayerHandler().startTurn();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        super.render();

        batch.begin();

        roboRally.getCurrentMap().update(Gdx.graphics.getDeltaTime());
        roboRally.getCurrentMap().render(batch);

        uiHandler.update();

        batch.end();
    }


    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        //font.dispose();
        uiHandler.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        roboRally.getCurrentMap().resize(width, height);
        uiHandler.resize();
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
    public static UIHandler getUiHandler() {
        return uiHandler;
    }

    public static SoundPlayer getSoundPlayer() {
        if (null == soundPlayer) {
            createSoundPlayer();
        }
        return soundPlayer;
    }

    private static synchronized void createSoundPlayer() {
        soundPlayer = new SoundPlayer();
    }

    public static RoboRally getRoboRally() {
        if (null == roboRally) {
            createRoboRally(FALLBACK_MAP_FILE_PATH, 8);
        }
        return roboRally;
    }

    public static synchronized RoboRally createRoboRally(String map, int playerCount) {
        roboRally = new RoboRally(map, playerCount);
        return roboRally;
    }

    /**
     * This method will always run the runnable on the main thread
     *
     * @param runnable The code to run
     * @param msDelay  How long, in milliseconds, to wait before executing the runnable
     */
    public static void scheduleSync(@NotNull Runnable runnable, long msDelay) {
        if (msDelay <= 0) {
            Gdx.app.postRunnable(runnable);
        } else {
            GameGraphics.executorService.schedule(() ->
                    Gdx.app.postRunnable(runnable), msDelay, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * @param runnable The code to run
     * @param msDelay  How long, in milliseconds, to wait before executing the runnable
     */
    public static void scheduleAsync(@NotNull Runnable runnable, long msDelay) {
        GameGraphics.executorService.schedule(() ->
                runnable, msDelay, TimeUnit.MILLISECONDS);
    }

    /**
     * Looks through every tile in 'flags' layer to count the number of flags in map
     * Assumes that the flag layer is called 'flags' and that there are no other tiles in flag layer that flags and null
     */
    private void findFlags() {
        MapHandler map = getRoboRally().getCurrentMap();
        for (int y = 0; y < map.getMapHeight(); y++) {
            for (int x = 0; x < map.getMapWidth(); x++) {
                if (map.getTile(MapHandler.FLAG_LAYER_NAME, x, y) != null) {
                    flagCount++;
                }
            }
        }
    }
}
