package no.uib.inf112.core.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Player;

public class InputHandler extends InputAdapter {

    public InputHandler() {
        RoboRally.getInputMultiplexer().addProcessor(this);
    }

    @Override
    public boolean scrolled(int direction) {
        RoboRally.getCurrentMap().zoomCamera(direction);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        if (character == '+') {
            RoboRally.getCurrentMap().zoomCamera(-1);
        } else if (character == '-') {
            RoboRally.getCurrentMap().zoomCamera(1);
        } else {
            return false;
        }
        return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        RoboRally.getCurrentMap().moveCamera(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Input.Keys.ENTER == keycode) {

            Player player = RoboRally.getPlayerHandler().getCurrentPlayer();

            if (RoboRally.getUiHandler().isDrawnCardsVisible()) {
                player.endDrawCards();
            }
            return true;
        }
        return false;
    }
}
