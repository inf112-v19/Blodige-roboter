package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;

import java.io.File;

public class RobotWallTest extends TestGraphics {

    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.getRoboRally();
        roboRally.changeMap(TEST_MAP_FOLDER + File.separatorChar + "player_wall_test_map.tmx");
    }

}
