package no.uib.inf112.desktop;


import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import no.uib.inf112.core.RoboRally;

public class HeadlessLauncher {
    public static void main(String[] args) {
        HeadlessApplicationConfiguration cfg = new HeadlessApplicationConfiguration();

        new HeadlessApplication(new RoboRally(), cfg);
    }
}
