package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class RobotHoleTest extends TestGraphics {


    private Robot testBot;
    private Player player;

    private static RoboRally roboRally;


    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.getRoboRally();
        roboRally.changeMap(TEST_MAP_FOLDER + File.separatorChar + "player_hole_test_map.tmx");
    }

    @Before
    public void setup() {
        roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        testBot = player.getRobot();
        testBot.teleport(0, 0);
        testBot.setDirection(Direction.NORTH);
        player.setBackup(0, 0);
    }


    @Test
    public void movingOntoHoleShouldTeleportRobotToBackup() {
        player.moveRobot(Movement.MOVE_1);
        Vector2Int backup = player.getBackup();
        assertEquals(backup.x, testBot.getX());
        assertEquals(backup.y, testBot.getY());
    }

    @Test
    public void movingOntoHoleShouldReduceLifeByOne() {
        int livesBefore = player.getLives();
        player.moveRobot(Movement.MOVE_1);
        assertEquals(livesBefore - 1, player.getLives());
    }

    @Test
    public void movingOverAHoleShouldTeleportRobotToBackup() {
        //Hole is one step from robot, default tile is two tiles from robot
        player.moveRobot(Movement.MOVE_2);
        Vector2Int backup = player.getBackup();
        assertEquals(backup.x, testBot.getX());
        assertEquals(backup.y, testBot.getY());
    }
}
