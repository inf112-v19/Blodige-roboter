package no.uib.inf112.core.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.Player;

public class InputHandler extends InputAdapter {

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

            Player player = GameGraphics.getRoboRally().getPlayerHandler().mainPlayer();

            if (GameGraphics.getUiHandler().isDrawnCardsVisible()) {
                player.endDrawCards();
            }
            return true;
        }
        return false;
    }
}
