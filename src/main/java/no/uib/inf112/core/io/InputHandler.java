package no.uib.inf112.core.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import no.uib.inf112.core.RoboRally;

public class InputHandler extends InputAdapter {

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
}
