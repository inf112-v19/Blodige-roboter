package no.uib.inf112.core.map;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;

public class ConveryorInteractOnUserTest extends TestGraphics {

    private static RoboRally roboRally;
    private static Player player;
    private static Robot testBot;
    private int roboX, roboY;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_interaction_test_map.tmx", 1);
    }

    @Before
    public void setup() {
        roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        testBot = player.getRobot();
    }

    public void moveTestBotTo(int x, int y) {
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
