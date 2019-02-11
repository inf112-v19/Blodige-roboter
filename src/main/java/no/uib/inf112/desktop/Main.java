package no.uib.inf112.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.uib.inf112.core.RoboRally;


public class Main {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 1280;
        cfg.height = 720;
        cfg.samples = 16; //max out the samples as this isn't a very heavy game.

        new LwjglApplication(new RoboRally(), cfg);
    }
}