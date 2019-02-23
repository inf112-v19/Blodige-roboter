package no.uib.inf112.core.player;

import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TileType;
import no.uib.inf112.core.map.TiledMapHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RoboRally.class)
public class RobotTest {

    private Robot testBot;
    private int roboX;
    private int roboY;
    private Player player;

    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;


    @Before
    public void setup() {
        player = new Player(HEIGHT / 2, WIDTH / 2, Direction.NORTH, true);
        testBot = player.getRobot();
        roboX = testBot.getX();
        roboY = testBot.getY();

        PowerMockito.mockStatic(RoboRally.class);
        PowerMockito.mockStatic(PlayerHandler.class);

        PlayerHandler ph = mock(PlayerHandler.class);
        when(ph.mainPlayer()).thenReturn(player);

        MapHandler map = Mockito.mock(TiledMapHandler.class);
        when(map.getMapHeight()).thenReturn(RobotTest.HEIGHT);
        when(map.getMapWidth()).thenReturn(RobotTest.WIDTH);
        when(map.isOutsideBoard(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod();

        when(RoboRally.getPlayerHandler()).thenReturn(ph);
        when(RoboRally.getCurrentMap()).thenReturn(map);
    }

    @Test
    public void movingOneFacingNorthShouldNotChangeX() {
        testBot.move(Movement.MOVE_1);
        assertEquals(roboX, testBot.getX());
    }

    @Test
    public void movingOneFacingNorthShouldIncrementY() {
        testBot.move(Movement.MOVE_1);
        assertEquals(roboY + 1, testBot.getY());
    }

    @Test
    public void movingThreeFacingNorthShouldIncreaseYWithThree() {
        testBot.move(Movement.MOVE_3);
        assertEquals(roboY + 3, testBot.getY());
    }

    @Test
    public void movingTwoFacingEastShouldNotChangeY() {
        testBot.setDirection(Direction.EAST);
        testBot.move(Movement.MOVE_2);
        assertEquals(roboY, testBot.getY());
    }

    @Test
    public void movingTwoFacingEastShouldIncreaseXWithTwo() {
        testBot.setDirection(Direction.EAST);
        testBot.move(Movement.MOVE_2);
        assertEquals(roboX + 2, testBot.getX());
    }

    @Test
    public void backingUpWhileFacingNorthShouldDecrementY() {
        testBot.move(Movement.BACK_UP);
        assertEquals(roboY - 1, testBot.getY());
    }

    @Test
    public void backingUpShouldNotAffectDirectionOfRobot() {
        Direction facing = testBot.getDirection();
        testBot.move(Movement.BACK_UP);
        assertEquals(facing, testBot.getDirection());
    }

    @Test
    public void movingForwardShouldNotAffectDirectionOfRobot() {
        Direction facing = testBot.getDirection();
        testBot.move(Movement.MOVE_2);
        testBot.move(Movement.MOVE_1);
        testBot.move(Movement.MOVE_3);
        assertEquals(facing, testBot.getDirection());
    }

    @Test
    public void turningLeftWhileFacingNorthShouldResultInWest() {
        testBot.setDirection(Direction.NORTH); //Just in case setup is changed
        testBot.move(Movement.LEFT_TURN);
        assertEquals(Direction.WEST, testBot.getDirection());
    }

    @Test
    public void turningLeftShouldNotChangeXOrY() {
        testBot.move(Movement.LEFT_TURN);
        assertEquals(roboX, testBot.getX());
        assertEquals(roboY, testBot.getY());
    }

    @Test
    public void turningRightTwiceShouldHaveTheSameResultAsAUTurn() {
        Robot testBot2 = new Robot(5, 5, testBot.getDirection(), true);
        testBot.move(Movement.RIGHT_TURN);
        testBot.move(Movement.RIGHT_TURN);
        testBot2.move(Movement.U_TURN);
        assertEquals(testBot.getDirection(), testBot2.getDirection());
    }

    @Test
    public void movingRobotInASquareShouldResultInRobotBeingBackAtStartingPosition() {
        Direction facing = testBot.getDirection();
        for (int i = 0; i < 100; i++) {
            if (i % 4 == 0) {
                assertEquals(roboX, testBot.getX());
                assertEquals(roboY, testBot.getY());
                assertEquals(facing, testBot.getDirection());
            } else {
                assertFalse(roboX == testBot.getX() && roboY == testBot.getY());
                assertNotEquals(facing, testBot.getDirection());
            }
            testBot.move(Movement.MOVE_2);
            testBot.move(Movement.LEFT_TURN);
        }
    }

    @Test
    public void getTileType() {
        for (Direction dir : Direction.values()) {
            testBot.setDirection(dir);
            assertNotNull(testBot.getTileType());

            assertEquals(TileType.Group.ROBOT, testBot.getTileType().getGroup());

            String[] name = testBot.getTileType().name().split("_");
            assertEquals(dir.name(), name[name.length - 1]);
        }
    }

    @Test
    public void movingOutOfBoundTeleportToBackup() {
        testBot.teleport(0, 0);
        testBot.setDirection(Direction.SOUTH);
        testBot.move(Movement.MOVE_1);

        assertEquals(roboX, testBot.getX());
        assertEquals(roboY, testBot.getY());
    }

    @Test
    public void movingOutOfBoundReduceLifeByOne() {
        testBot.teleport(0, 0);
        player.damage(1);

        testBot.setDirection(Direction.SOUTH);
        testBot.move(Movement.MOVE_1);

        assertTrue(testBot.shouldUpdate());

        assertEquals(Player.MAX_HEALTH, player.getHealth());
        assertEquals(Player.MAX_LIVES - 1, player.getLives());

    }
}
