package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameScreen implements Screen {

    private GameGraphics game;
    private RoboRally roboRally;

    private static InputMultiplexer inputMultiplexer;
    private static UIHandler uiHandler;
    private static ControlPanelEventHandler cpEventHandler;
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public GameScreen(GameGraphics game) {

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        cpEventHandler = new ControlPanelEventHandler();

        roboRally = GameGraphics.getRoboRally();

        uiHandler = new UIHandler();
        new InputHandler(); //this must be after UIHandler to allow dragging of cards

        this.game = game;
    }


    @Override
    public void show() {
        roboRally.getPlayerHandler().startTurn();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));



        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new EndScreen(game));
        }


        game.batch.begin();

        roboRally.getCurrentMap().update(Gdx.graphics.getDeltaTime());
        roboRally.getCurrentMap().render(game.batch);

        uiHandler.update();

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        roboRally.getCurrentMap().resize(width, height);
        uiHandler.resize();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        uiHandler.dispose();
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
            GameScreen.executorService.schedule(() ->
                    Gdx.app.postRunnable(runnable), msDelay, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * @param runnable The code to run
     * @param msDelay  How long, in milliseconds, to wait before executing the runnable
     */
    public static void scheduleAsync(@NotNull Runnable runnable, long msDelay) {
        GameScreen.executorService.schedule(() ->
                runnable, msDelay, TimeUnit.MILLISECONDS);
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
