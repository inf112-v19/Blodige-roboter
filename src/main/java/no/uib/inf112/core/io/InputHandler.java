package no.uib.inf112.core.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.ColorfulOrthogonalTiledMapRenderer;
import no.uib.inf112.core.player.UserPlayer;

import java.util.Stack;

public class InputHandler extends InputAdapter {

    private Stack<Integer> logger = new Stack<>();

    public InputHandler() {
        GameGraphics.getInputMultiplexer().addProcessor(this);
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

            UserPlayer player = GameGraphics.getRoboRally().getPlayerHandler().mainPlayer();

            if (GameGraphics.getUiHandler().isDrawnCardsVisible()) {
                player.endDrawCards();
            }
            return true;
        }
        logger.push(keycode);
        if (logger.size() > 9 &&
                logger.elementAt(0) == Input.Keys.UP &&
                logger.elementAt(1) == Input.Keys.UP &&
                logger.elementAt(2) == Input.Keys.DOWN &&
                logger.elementAt(3) == Input.Keys.DOWN &&
                logger.elementAt(4) == Input.Keys.LEFT &&
                logger.elementAt(5) == Input.Keys.RIGHT &&
                logger.elementAt(6) == Input.Keys.LEFT &&
                logger.elementAt(7) == Input.Keys.RIGHT &&
                logger.elementAt(8) == Input.Keys.B &&
                logger.elementAt(9) == Input.Keys.A) {
            logger.clear();
            ColorfulOrthogonalTiledMapRenderer.PARTY = true;

        }
        return false;
    }
}
