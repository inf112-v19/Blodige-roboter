package no.uib.inf112.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.uib.inf112.core.HelloWorld;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Robo Rally";
        cfg.width = 480;
        cfg.height = 320;

        new LwjglApplication(new HelloWorld(), cfg);
    }
}