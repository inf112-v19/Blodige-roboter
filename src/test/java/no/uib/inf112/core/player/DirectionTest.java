package no.uib.inf112.core.player;

import no.uib.inf112.core.util.Direction;
import org.junit.Before;
import org.junit.Test;

import static no.uib.inf112.core.util.Direction.*;
import static org.junit.Assert.assertEquals;

public class DirectionTest {

    private Direction dir;

    @Before
    public void setup() {
        dir = NORTH;
    }

    //INVERSE()
    @Test
    public void NORTHInvertedShouldReturnSOUTH() {
        assertEquals(SOUTH, dir.inverse());
    }

    @Test
    public void NORTHInvertedTwiceShouldReturnNORTH() {
        assertEquals(NORTH, dir.inverse().inverse());
    }

    @Test
    public void EASTInvertedShouldReturnWEST() {
        dir = EAST;
        assertEquals(WEST, dir.inverse());
    }


    // LEFT()
    @Test
    public void NORTHTurnedLeftShouldReturnWest() {
        assertEquals(WEST, dir.turnLeft());
    }

    @Test
    public void NORTHTurnedLeftFourTimesShouldReturnNorth() {
        assertEquals(NORTH, dir.turnLeft().turnLeft().turnLeft().turnLeft());
    }


    // RIGHT()
    @Test
    public void NORTHTurnedRightShouldReturnEast() {
        assertEquals(EAST, dir.turnRight());
    }

    @Test
    public void NORTHTurnedRightFourTimesShouldReturnNorth() {
        assertEquals(NORTH, dir.turnRight().turnRight().turnRight().turnRight());
    }


    @Test
    public void InvertingShouldReturnTheSameAsTurningTwice() {
        Direction turnedLeft = NORTH.turnLeft().turnLeft();
        Direction turnedRight = NORTH.turnRight().turnRight();

        assertEquals(turnedLeft, dir.inverse());
        assertEquals(turnedRight, dir.inverse());
    }
}
