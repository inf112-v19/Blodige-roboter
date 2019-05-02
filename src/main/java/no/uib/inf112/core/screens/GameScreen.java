package no.uib.inf112.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.io.InputHandler;
import no.uib.inf112.core.multiplayer.IClient;
import no.uib.inf112.core.multiplayer.dtos.NewGameDto;
import no.uib.inf112.core.screens.menuscreens.EndScreen;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import org.jetbrains.annotations.NotNull;

public class GameScreen implements Screen {

    private GameGraphics game;

    private static InputMultiplexer inputMultiplexer;
    private static UIHandler uiHandler;
    private static ControlPanelEventHandler cpEventHandler;


    public GameScreen(GameGraphics game) {
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        cpEventHandler = new ControlPanelEventHandler();


        uiHandler = new UIHandler();
        new InputHandler(); //this must be after UIHandler to allow dragging of cards

        this.game = game;
    }

    public GameScreen(GameGraphics game, NewGameDto setup, IClient client) {

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        cpEventHandler = new ControlPanelEventHandler();

        GameGraphics.createRoboRallyMultiplayer(setup, client);
        uiHandler = new UIHandler();
        new InputHandler(); //this must be after UIHandler to allow dragging of cards

        this.game = game;
    }


    @Override
    public void show() {
        GameGraphics.getRoboRally().getPlayerHandler().startTurn();
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            GameGraphics.getRoboRally().getPlayerHandler().setGameOver(true);
            game.setScreen(new EndScreen(game));
        } else if (GameGraphics.getRoboRally().getPlayerHandler().isGameOver()) {
            game.setScreen(new EndScreen(game));
        }

        game.batch.begin();

        GameGraphics.getRoboRally().getCurrentMap().update(Gdx.graphics.getDeltaTime());
        GameGraphics.getRoboRally().getCurrentMap().render(game.batch);

        uiHandler.update();

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        GameGraphics.getRoboRally().getCurrentMap().resize(width, height);
        uiHandler.resize();
    }

    @Override
    public void pause() {
        //Should not do anything
    }

    @Override
    public void resume() {
        //Should not do anything
    }

    @Override
    public void hide() {
        //Should not do anything
    }

    @Override
    public void dispose() {
        uiHandler.dispose();
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
