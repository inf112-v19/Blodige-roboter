package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;

public class GearTileTest extends TestGraphics {

    private static RoboRally roboRally;
    private AbstractPlayer testPlayer;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "rotation_gear_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generateOnePlayer();
        testPlayer = roboRally.getPlayerHandler().testPlayer();
    }

}
