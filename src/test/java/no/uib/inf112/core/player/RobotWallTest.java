package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class RobotWallTest extends TestGraphics {

    private static AbstractPlayer player;
    private static Robot testBot;
    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_wall_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        testBot = player;
        testBot.teleport(0, 0);
        player.setBackup(0, 0);
    }

    @Test
    public void movingToWalltileWhereWallIsOnOppositeEdgeShouldWork() {
        testBot.setDirection(Direction.NORTH);
        player.move(Movement.MOVE_1);
        assertEquals(0, testBot.getX());
        assertEquals(1, testBot.getY());
    }

    //    TODO this test will fail until we fix issue 99 (supporting moving into walls in map)
    @Test
    public void movingToWalltileWhereWallIsOnClosestEdgeShouldNotWork() {
        testBot.setDirection(Direction.EAST);
        player.move(Movement.MOVE_1);
        assertEquals(0, testBot.getX());
        assertEquals(0, testBot.getY());

    }


    //    TODO this test will fail until we fix issue 73
    @Test
    public void movingTwoStepsWithWallBetweenShouldMakeRobotStopAfterOneStep() {
        testBot.setDirection(Direction.NORTH);
        player.move(Movement.MOVE_2);
        assertEquals(0, testBot.getX());
        assertEquals(1, testBot.getY()); //Only moved one step
    }
}
