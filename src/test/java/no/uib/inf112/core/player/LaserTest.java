package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class LaserTest extends TestGraphics {

    private static IPlayer testPlayer;
    private static RoboRally roboRally;
    private static MapHandler map;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "laser_test_map.tmx", 1);
        map = roboRally.getCurrentMap();
    }


    @Before
    public void setup() {
        roboRally.getPlayerHandler().generateOnePlayer();
        testPlayer = roboRally.getPlayerHandler().testPlayer();
        testPlayer.teleport(0, 0);
        testPlayer.setDirection(Direction.NORTH);
    }


    @Test
    public void standingOnlaserShouldDecreaseHealthByOne() {
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(Movement.MOVE_1);
        
    }
}
