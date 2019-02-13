package no.uib.inf112.core.player;

import jdk.nashorn.internal.objects.annotations.Setter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    private Robot testBot;
    private int roboX;
    private int roboY;

    @Before
    public void setup() {
        testBot = new Robot(5, 5, Direction.NORTH, true);
        roboX = testBot.getX();
        roboY = testBot.getY();

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
}
