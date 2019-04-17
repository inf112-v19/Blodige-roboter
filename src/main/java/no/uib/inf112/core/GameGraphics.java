package no.uib.inf112.core;

import com.badlogic.gdx.Game;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.screens.TitleScreen;
=======
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.MapHandler;
>>>>>>> 52-control-panel-texture
import no.uib.inf112.core.ui.SoundPlayer;

import java.io.File;

public class GameGraphics extends Game {

    private static RoboRally roboRally;
    public static boolean HEADLESS;
    private static SoundPlayer soundPlayer;

    public static final String MAP_FOLDER = "maps";
<<<<<<< HEAD
    public static String mapName = "risky_exchange";
    public static String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
=======
    //  public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
>>>>>>> 52-control-panel-texture
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "checkmate.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "dizzy_dash.tmx";
    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "island_hop.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "chop_shop_challenge.tmx";

<<<<<<< HEAD
    public SpriteBatch batch;
    public BitmapFont font;

    public GameScreen gameScreen;

//    private static InputMultiplexer inputMultiplexer;
//    private static UIHandler uiHandler;
//    private static ControlPanelEventHandler cpEventHandler;
//    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void setMap(String newMapName) {
        switch (newMapName) {
            case "Risky Exchange":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
                mapName = "risky_exchange";
                return;
            case "Checkmate":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "checkmate.tmx";
                mapName = "checkmate";
                return;
            case "Dizzy Dash":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "dizzy_dash.tmx";
                mapName = "dizzy_dash";
                return;
            case "Island Hop":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "island_hop.tmx";
                mapName = "island_hop";
                return;
            case "Chop Shop Challenge":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "chop_shop_challenge.tmx";
                mapName = "chop_shop_challenge";
                return;
            default:
        }
    }
=======
    public static int flagCount;

    private SpriteBatch batch;

    private static InputMultiplexer inputMultiplexer;
    private static UIHandler uiHandler;
    private static ControlPanelEventHandler cpEventHandler;
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
>>>>>>> 52-control-panel-texture

    @Override
    public void create() {

        batch = new SpriteBatch();
<<<<<<< HEAD
        font = new BitmapFont();

//        inputMultiplexer = new InputMultiplexer();
//        Gdx.input.setInputProcessor(inputMultiplexer);
//
//        cpEventHandler = new ControlPanelEventHandler();
//
//
//        getRoboRally();
//        uiHandler = new UIHandler();
//        new InputHandler(); //this must be after UIHandler to allow dragging of cards
//        getRoboRally().getPlayerHandler().startTurn();

//        gameScreen = new GameScreen(this);
//        setScreen(gameScreen);

        setScreen(new TitleScreen(this));
=======

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        cpEventHandler = new ControlPanelEventHandler();

        getRoboRally();
        findFlags();
        uiHandler = new UIHandler();
        new InputHandler(); //this must be after UIHandler to allow dragging of cards
        getRoboRally().getPlayerHandler().startTurn();
>>>>>>> 52-control-panel-texture
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
<<<<<<< HEAD
        font.dispose();
=======
        //font.dispose();
        uiHandler.dispose();
>>>>>>> 52-control-panel-texture
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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

<<<<<<< HEAD
=======
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
>>>>>>> 52-control-panel-texture
}
