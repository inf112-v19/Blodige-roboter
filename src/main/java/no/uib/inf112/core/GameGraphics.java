package no.uib.inf112.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.map.MapInteractOnUser;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameGraphics extends Game {
    private RoboRally roboRally = new RoboRally();

    private SpriteBatch batch;
    private BitmapFont font;

    private static InputMultiplexer inputMultiplexer;
    private static UIHandler uiHandler;
    private static ControlPanelEventHandler cpEventHandler;
    public static ScheduledExecutorService executorService;

    @Override
    public void create() {

        executorService = Executors.newSingleThreadScheduledExecutor();

        batch = new SpriteBatch();
        font = new BitmapFont();

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        cpEventHandler = new ControlPanelEventHandler();
        roboRally.map = new TiledMapHandler(roboRally.FALLBACK_MAP_FILE_PATH);

        roboRally.playerHandler = new PlayerHandler(3);
        roboRally.playerHandler.generatePlayers(false);
        roboRally.mapInteractOnUser = new MapInteractOnUser();
        uiHandler = new UIHandler();
        new InputHandler(); //this must be after UIHandler to allow dragging of cards

        roboRally.playerHandler.doTurn();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        super.render();

        batch.begin();

        roboRally.map.update(Gdx.graphics.getDeltaTime());
        roboRally.map.render(batch);

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
        roboRally.map.resize();
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

}