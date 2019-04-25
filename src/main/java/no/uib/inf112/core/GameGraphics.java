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

    public static final String MAP_FOLDER = "maps" + File.separatorChar;
    public static final String MAP_EXTENSION = ".tmx";


    public static String mapFileName = "risky_exchange";

    public static Music backgroundMusic;
    public static boolean soundMuted = false;

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
     * @param newMapFile The name of the map (not file name) that we want to use
     */
    public static void setMap(String newMapFile) {
        mapFileName = newMapFile;
    }

    public static RoboRally getRoboRally() {
        if (null == roboRally) {
            createRoboRally(MAP_FOLDER + mapFileName + MAP_EXTENSION, 2);
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
