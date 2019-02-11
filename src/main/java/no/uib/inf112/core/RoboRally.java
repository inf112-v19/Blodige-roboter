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
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Robot;
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

    //FIXME Issue #33: create a robot handler that handles all the players (as we can have between 2 and N robots)
    private static Robot robot;
    private Robot robot2;

    private boolean waitingForUser;

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

        map = new TiledMapHandler(FALLBACK_MAP_FILE_PATH);
        robot = new Robot(5, 5, Direction.NORTH);
        robot2 = new Robot(1, 1, Direction.SOUTH);

        cpEventHandler = new ControlPanelEventHandler();

//        RoboRally.getControlPanelEventHandler().registerListener(CardClickedEvent.class,
//                                                                 (ControlPanelEventListener<CardClickedEvent>)
//                                                                 event -> System.out
//                                                                     .println("Clicked card nr " + event.getId()));
//
//
//        RoboRally.getControlPanelEventHandler().registerListener(PowerDownEvent.class,
//                                                                 (ControlPanelEventListener<PowerDownEvent>) event
//                                                                 -> System.out
//                                                                     .println("Clicked power down!"));
    }

    public static void round() {
        for (int i = 0; i < PHASES_PER_ROUND; i++) {
            // Decide which robot moves
            // Move robots in order
            move();
            System.out.println("moved!");
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

    public static void move() {
        try {
            robot.move(-1, 0);
        } catch (IllegalArgumentException ex) {
            robot.move(5, 0);
        }
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

    public static ControlPanelEventHandler getControlPanelEventHandler() {
        return cpEventHandler;
    }
}
