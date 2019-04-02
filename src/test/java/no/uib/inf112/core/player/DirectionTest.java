package no.uib.inf112.core.player;

import no.uib.inf112.core.util.Direction;
import org.junit.Before;
import org.junit.Test;

import static no.uib.inf112.core.util.Direction.*;
import static org.junit.Assert.assertEquals;

public class DirectionTest {

    private Direction dir;

    @Before
    public void setUp() {
        dir = NORTH;
    }

    //INVERSE()
    @Test
    public void northInvertedShouldReturnSOUTH() {
        assertEquals(SOUTH, dir.inverse());
    }

    @Test
    public void northInvertedTwiceShouldReturnNORTH() {
        assertEquals(NORTH, dir.inverse().inverse());
    }

    @Test
    public void eastInvertedShouldReturnWEST() {
        dir = EAST;
        assertEquals(WEST, dir.inverse());
    }


    // LEFT()
    @Test
    public void northTurnedLeftShouldReturnWest() {
        assertEquals(WEST, dir.turnLeft());
    }

    @Test
    public void northTurnedLeftFourTimesShouldReturnNorth() {
        assertEquals(NORTH, dir.turnLeft().turnLeft().turnLeft().turnLeft());
    }


    // RIGHT()
    @Test
    public void northTurnedRightShouldReturnEast() {
        assertEquals(EAST, dir.turnRight());
    }

    @Test
    public void northTurnedRightFourTimesShouldReturnNorth() {
        assertEquals(NORTH, dir.turnRight().turnRight().turnRight().turnRight());
    }


    @Test
    public void invertingShouldReturnTheSameAsTurningTwice() {
        Direction turnedLeft = NORTH.turnLeft().turnLeft();
        Direction turnedRight = NORTH.turnRight().turnRight();

        assertEquals(turnedLeft, dir.inverse());
        assertEquals(turnedRight, dir.inverse());
    }
}
