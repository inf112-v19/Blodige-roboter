package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void robotOnCounterClockwiseRotationGear() {
        testPlayer.teleport(0, 0);  //Location of counter clockwise rotation gear on test map
        GearTile gear = (GearTile) roboRally.getCurrentMap().getTile("board", 0, 0);
        if (gear != null) {
            rotateCClockwise(Direction.NORTH, Direction.WEST, gear);
            rotateCClockwise(Direction.WEST, Direction.SOUTH, gear);
            rotateCClockwise(Direction.SOUTH, Direction.EAST, gear);
            rotateCClockwise(Direction.EAST, Direction.NORTH, gear);
        } else {
            fail();
        }
    }

    private void rotateCClockwise(Direction from, Direction to, GearTile gear) {
        testPlayer.setDirection(from);
        gear.action(testPlayer);
        assertEquals(to, testPlayer.getDirection());
    }

}
