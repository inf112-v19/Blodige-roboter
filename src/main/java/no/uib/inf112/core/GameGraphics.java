package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.google.gson.Gson;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.multiplayer.Client;
import no.uib.inf112.core.multiplayer.Server;
import no.uib.inf112.core.multiplayer.jsonClasses.NewGameDto;
import no.uib.inf112.core.player.MultiPlayerHandler;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.screens.TitleScreen;
import no.uib.inf112.core.testutils.HeadlessMapHandler;
import no.uib.inf112.core.ui.Sound;

import java.io.File;

public class GameGraphics extends Game {

    private static RoboRally roboRally;
    public static boolean HEADLESS;

    public static final String MAP_FOLDER = "maps" + File.separatorChar;
    public static final String MAP_EXTENSION = ".tmx";

    public static final String SCREEN_FONT = "screen_font.ttf";
    public static final String SCREEN_FONT_BOLD = "screen_font_bold.ttf";

    public static String mapFileName = "risky_exchange";

    public static Music backgroundMusic;
    public static boolean soundMuted = false;
    public static int players;

    public static Gson gson = new Gson();

    public static String mainPlayerName = "default name";

    public static final int MIN_PORT = 49152;
    public static final int MAX_PORT = 65535;

    public SpriteBatch batch;

    private static Server server;
    private static Client client;

    public static RoboRally createRoboRallyMultiplayer(NewGameDto setup, Client client) {
        String mapPath = MAP_FOLDER + setup.map + MAP_EXTENSION;
        MapHandler mapHandler = !HEADLESS ? new TiledMapHandler(mapPath) : new HeadlessMapHandler(mapPath);
        roboRally = new RoboRally(mapHandler, new MultiPlayerHandler(setup, mapHandler, client));
        return roboRally;
    }

    @Override
    public void create() {
        players = 2;
        batch = new SpriteBatch();
        setScreen(new TitleScreen(this));

        backgroundMusic = Sound.getBackgroundMusic();
        backgroundMusic.setVolume(1f);
        //backgroundMusic.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        closeResources();
    }

    private void closeResources() {
        if (server != null) {
            server.close();
        }
        if (client != null) {
            client.closeConnection();
        }
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * Method to change the map that will be used to create roborally. The parameter string comes from the selectbox in
     * AbstractSetupScreen and can therefore not have other values than the cases deal with.
     *
     * @param newMapFile The name of the map (not file name) that we want to use
     */
    public static void setMap(String newMapFile) {
        mapFileName = newMapFile;
    }

    public static RoboRally getRoboRally() {
        if (null == roboRally) {
            createRoboRally(MAP_FOLDER + mapFileName + MAP_EXTENSION, players);
        }
        return roboRally;
    }

    public static void resetRoborally() {
        roboRally = null;
    }

    public static synchronized RoboRally createRoboRally(String map, int playerCount) {
        MapHandler mapHandler = !HEADLESS ? new TiledMapHandler(map) : new HeadlessMapHandler(map);
        roboRally = new RoboRally(mapHandler, new PlayerHandler(playerCount, mapHandler));
        return roboRally;
    }

    public static BitmapFont generateFont(String fontFile, int size) {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(fontFile));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        return fontGenerator.generateFont(parameter);
    }


    public Label createLabel(String text, float x, float y, int fontSize) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = generateFont(GameGraphics.SCREEN_FONT, fontSize);

        Label label = new Label(text, labelStyle);
        label.setColor(Color.BLACK);

        label.setPosition(x, y);
        return label;
    }

    /**
     * TODO write this
     *
     * @param newServer
     */
    public static void setServer(Server newServer) {
        server = newServer;
    }

    //TODO write this
    public static void setClient(Client newClient) {
        client = newClient;
    }

}
