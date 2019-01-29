package no.uib.inf112.core.io;

import com.badlogic.gdx.InputAdapter;
import no.uib.inf112.core.RoboRally;

public class InputHandler extends InputAdapter {

    private RoboRally rr;

    public InputHandler(RoboRally rr) {
        this.rr = rr;
    }

    @Override
    public boolean scrolled(int direction) {
        rr.getCurrentMap().zoom(direction);
        return true;
    }
}
