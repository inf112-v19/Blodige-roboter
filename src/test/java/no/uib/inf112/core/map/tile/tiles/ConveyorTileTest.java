package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;


/**
 * Coordinates for impotant tiles on the test map for the testing of conveyors:
 * - (0,0) : single step north conveyor
 * - (0,1) : single step south conveyor
 * - (1,0) : singel step east conveyor
 * - (1,1) : singel step west conveyor
 * - (0,5) : singel step north conveyor moving onto right turn (robot should be rotated)
 * - (5,0)-(5,1) : double step north conveyor
 * - (5,3)-(5,2) : double step south conveyor
 * - (7,1)-(6,1) : double step west conveyor
 * - (6,0)-(7,0) : double step east conveyor
 * - (6,2) : double step north conveyor moving onto normal board tile (robot should only move one step)
 * - (5,5) : double step north conveyor moving onto right turn (robot should be rotated)
 */
public class ConveyorTileTest extends TestGraphics {

    private static RoboRally roboRally;
    //private static Player player;
    private static Robot testBot;
    private int roboX, roboY;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_interaction_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generateOnePlayer();
        testBot = roboRally.getPlayerHandler().testPlayer();
    }

    private void moveTestBotTo(int x, int y) {
        roboX = x;
        roboY = y;
        testBot.teleport(x, y);
    }

    //TODO issue 100, all these tests will fail until we support conveyor belts. Add tests for double step conveyor belts (find out how these work at the edges)

//    @Test
//    public void singleStepUpConveyorShouldMoveRobotOneUp() {
//        moveTestBotTo(0, 0);
//        roboRally.mapInteractOnUser.scan(roboRally.getCurrentMap().getEntities());
//
//        assertEquals(roboX, testBot.getX());
//        assertEquals(roboY + 1, testBot.getY());
//    }
//
//    @Test
//    public void singleStepLeftConveyorShouldMoveRobotOneLeft() {
//        moveTestBotTo(1, 0);
//        roboRally.mapInteractOnUser.scan(roboRally.getCurrentMap().getEntities());
//
//        assertEquals(roboX - 1, testBot.getX());
//        assertEquals(roboY, testBot.getY());
//    }
//
//    @Test
//    public void singleStepRightConveyorShouldMoveRobotOneLeft() {
//        moveTestBotTo(2, 0);
//        roboRally.mapInteractOnUser.scan(roboRally.getCurrentMap().getEntities());
//
//        assertEquals(roboX + 1, testBot.getX());
//        assertEquals(roboY, testBot.getY());
//    }

}