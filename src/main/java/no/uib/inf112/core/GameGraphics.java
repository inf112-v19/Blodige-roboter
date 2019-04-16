package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.screens.TitleScreen;
import no.uib.inf112.core.ui.SoundPlayer;

import java.io.File;

public class GameGraphics extends Game {

    private static RoboRally roboRally;
    public static boolean HEADLESS;
    private static SoundPlayer soundPlayer;

    public static final String MAP_FOLDER = "maps";
    public static String mapName = "risky_exchange";
    public static String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "checkmate.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "dizzy_dash.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "island_hop.tmx";
//    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "chop_shop_challenge.tmx";

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

    @Override
    public void create() {

        batch = new SpriteBatch();
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
    }

    @Override
    public void render() {
        super.render();
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
            createRoboRally(FALLBACK_MAP_FILE_PATH, 2);
        }
        return roboRally;
    }

    public static synchronized RoboRally createRoboRally(String map, int playerCount) {
        roboRally = new RoboRally(map, playerCount);
        return roboRally;
    }

}
