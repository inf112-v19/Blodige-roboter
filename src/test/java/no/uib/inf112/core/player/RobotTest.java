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
}