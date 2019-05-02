package no.uib.inf112.core.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tiled.CustomOrthogonalTiledMapRenderer;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.screens.GameScreen;

import java.util.Stack;

public class InputHandler extends InputAdapter {

    private Stack<Integer> logger = new Stack<>();

    public InputHandler() {
        GameScreen.getInputMultiplexer().addProcessor(this);
    }

    @Override
    public boolean scrolled(int direction) {
        GameGraphics.getRoboRally().getCurrentMap().zoomCamera(direction);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == '+') {
            GameGraphics.getRoboRally().getCurrentMap().zoomCamera(-1);
        } else if (character == '-') {
            GameGraphics.getRoboRally().getCurrentMap().zoomCamera(1);
        } else {
            return false;
        }
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        GameGraphics.getRoboRally().getCurrentMap().moveCamera(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
        return true;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.ENTER == keycode) {

            Player player = (Player) GameGraphics.getRoboRally().getPlayerHandler().mainPlayer();

            if (GameScreen.getUiHandler().isDrawnCardsVisible()) {
                player.endDrawCards();
            }
            return true;
        }
        logger.push(keycode);
        if (logger.size() > 9 &&
                logger.elementAt(logger.size() - 10) == Input.Keys.UP &&
                logger.elementAt(logger.size() - 9) == Input.Keys.UP &&
                logger.elementAt(logger.size() - 8) == Input.Keys.DOWN &&
                logger.elementAt(logger.size() - 7) == Input.Keys.DOWN &&
                logger.elementAt(logger.size() - 6) == Input.Keys.LEFT &&
                logger.elementAt(logger.size() - 5) == Input.Keys.RIGHT &&
                logger.elementAt(logger.size() - 4) == Input.Keys.LEFT &&
                logger.elementAt(logger.size() - 3) == Input.Keys.RIGHT &&
                logger.elementAt(logger.size() - 2) == Input.Keys.B &&
                logger.elementAt(logger.size() - 1) == Input.Keys.A) {
            logger.clear();
            enableMode();
        }
        return false;
    }

    /**
     * Enable the party mode
     * Changes music and adds party to the renderer
     */
    public static void enableMode() {
        if (!CustomOrthogonalTiledMapRenderer.PARTY) {
            GameGraphics.getBackgroundMusic().start(Gdx.audio.newMusic(Gdx.files.internal("sound/techno.wav")));
            CustomOrthogonalTiledMapRenderer.PARTY = true;
            if (GameGraphics.getClient() != null) {
                GameGraphics.getClient().setPartyModeOn();
            }
        }
    }
}
