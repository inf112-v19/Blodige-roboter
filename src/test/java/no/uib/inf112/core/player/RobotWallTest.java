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

    private static IPlayer player;
    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_wall_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        //roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        player.teleport(0, 0);
        player.setBackup(0, 0);
    }

    @Test
    public void movingToWalltileWhereWallIsOnOppositeEdgeShouldWork() {
        player.setDirection(Direction.NORTH);
        player.move(Movement.MOVE_1);
        assertEquals(0, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void movingToWalltileWhereWallIsOnClosestEdgeShouldNotWork() {
        player.setDirection(Direction.EAST);
        player.move(Movement.MOVE_1);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());

    }

    @Test
    public void movingTwoStepsWithWallBetweenShouldMakeRobotStopAfterOneStep() {
        player.setDirection(Direction.NORTH);
        player.move(Movement.MOVE_2);
        assertEquals(0, player.getX());
        assertEquals(1, player.getY()); //Only moved one step
    }
}
