package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import no.uib.inf112.core.screens.TitleScreen;
import no.uib.inf112.core.ui.Sound;

import java.io.File;

public class GameGraphics extends Game {

    private static RoboRally roboRally;
    public static boolean HEADLESS;

    public static final String MAP_FOLDER = "maps";
    public static String mapName = "Risky Exchange";
    public static String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
    public static Music backgroundMusic;

    public SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new TitleScreen(this));

        backgroundMusic = Sound.getBackgroundMusic();
        backgroundMusic.setVolume(1f);
        backgroundMusic.play();

    }

    @Override
    public void render() {
        super.render(); // Calling the render method of the active screen
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * Method to change the map that will be used to create roborally. The parameter string comes from the selectbox in
     * SetupScreen and can therefore not have other values than the cases deal with.
     *
     * @param newMapName The name of the map (not file name) that we want to use
     */
    public static void setMap(String newMapName) {
        switch (newMapName) {
            case "Risky Exchange":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "risky_exchange.tmx";
                mapName = newMapName;
                return;
            case "Checkmate":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "checkmate.tmx";
                mapName = newMapName;
                return;
            case "Dizzy Dash":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "dizzy_dash.tmx";
                mapName = newMapName;
                return;
            case "Island Hop":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "island_hop.tmx";
                mapName = newMapName;
                return;
            case "Chop Shop Challenge":
                FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "chop_shop_challenge.tmx";
                mapName = newMapName;
                return;
            default:
        }
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

    public BitmapFont generateFont(String fontFile, int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        return fontGenerator.generateFont(parameter);
    }

}
