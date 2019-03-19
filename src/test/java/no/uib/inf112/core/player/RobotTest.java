package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.TileType;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RobotTest extends TestGraphics {

    private Robot testBot;
    private int roboX;
    private int roboY;
    private Player player;

    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;
    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 1);
    }

    @Before
    public void setup() {
        roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        testBot = player.getRobot();
        testBot.teleport(HEIGHT / 2, WIDTH / 2);
        testBot.setDirection(Direction.NORTH);

        roboX = testBot.getX();
        roboY = testBot.getY();
    }

    @Test
    public void movingOneFacingNorthShouldNotChangeX() {
        player.moveRobot(Movement.MOVE_1);
        assertEquals(roboX, testBot.getX());
    }

    @Test
    public void movingOneFacingNorthShouldIncrementY() {
        player.moveRobot(Movement.MOVE_1);
        assertEquals(roboY + 1, testBot.getY());
    }

    @Test
    public void movingThreeFacingNorthShouldIncreaseYWithThree() {
        player.moveRobot(Movement.MOVE_3);
        assertEquals(roboY + 3, testBot.getY());
    }

    @Test
    public void movingTwoFacingEastShouldNotChangeY() {
        testBot.setDirection(Direction.EAST);
        player.moveRobot(Movement.MOVE_2);
        assertEquals(roboY, testBot.getY());
    }

    @Test
    public void movingTwoFacingEastShouldIncreaseXWithTwo() {
        testBot.setDirection(Direction.EAST);
        player.moveRobot(Movement.MOVE_2);
        assertEquals(roboX + 2, testBot.getX());
    }

    @Test
    public void backingUpWhileFacingNorthShouldDecrementY() {
        player.moveRobot(Movement.BACK_UP);
        assertEquals(roboY - 1, testBot.getY());
    }

    @Test
    public void backingUpShouldNotAffectDirectionOfRobot() {
        Direction facing = testBot.getDirection();
        player.moveRobot(Movement.BACK_UP);
        assertEquals(facing, testBot.getDirection());
    }

    @Test
    public void movingForwardShouldNotAffectDirectionOfRobot() {
        Direction facing = testBot.getDirection();
        player.moveRobot(Movement.MOVE_2);
        player.moveRobot(Movement.MOVE_1);
        player.moveRobot(Movement.MOVE_3);
        assertEquals(facing, testBot.getDirection());
    }

    @Test
    public void turningLeftWhileFacingNorthShouldResultInWest() {
        testBot.setDirection(Direction.NORTH); //Just in case setup is changed
        player.moveRobot(Movement.LEFT_TURN);
        assertEquals(Direction.WEST, testBot.getDirection());
    }

    @Test
    public void turningLeftShouldNotChangeXOrY() {
        player.moveRobot(Movement.LEFT_TURN);
        assertEquals(roboX, testBot.getX());
        assertEquals(roboY, testBot.getY());
    }

    @Test
    public void turningRightTwiceShouldHaveTheSameResultAsAUTurn() {
        Robot testBot2 = new Robot(5, 5, testBot.getDirection());
        player.moveRobot(Movement.RIGHT_TURN);
        player.moveRobot(Movement.RIGHT_TURN);
        player.moveRobot(Movement.U_TURN);
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
            player.moveRobot(Movement.MOVE_2);
            player.moveRobot(Movement.LEFT_TURN);
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
        player.setBackup(testBot.getX(), testBot.getY());
        testBot.teleport(0, 0);
        testBot.setDirection(Direction.SOUTH);
        player.moveRobot(Movement.MOVE_1);

        assertEquals(roboX, testBot.getX());
        assertEquals(roboY, testBot.getY());
    }

    @Test
    public void movingOutOfBoundReduceLifeByOne() {
        player.getLives();
        testBot.teleport(0, 0);
        testBot.setDirection(Direction.SOUTH);
        player.moveRobot(Movement.MOVE_1);

        assertTrue(testBot.shouldUpdate());

        assertEquals(Player.MAX_HEALTH, player.getHealth());
        assertEquals(Player.MAX_LIVES - 1, player.getLives());

    }

}
