package no.uib.inf112.desktop;

import no.uib.inf112.core.RoboRally;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ExampleTest extends TestGraphics{
    private RoboRally roboRally;

    @Before
    public void setUp() {
        Main.HEADLESS = true;
        roboRally = new RoboRally();
    }

    @Test
    public void exampleTest() {
        assertNotNull(roboRally.getCurrentMap());
    }
}
