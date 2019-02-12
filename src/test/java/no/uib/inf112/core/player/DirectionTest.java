package no.uib.inf112.core.player;

import org.junit.Before;
import org.junit.Test;

import static no.uib.inf112.core.player.Direction.*;
import static org.junit.Assert.*;

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
        assertEquals(WEST, dir.left());
    }

    @Test
    public void NORTHTurnedLeftFourTimesShouldReturnNorth() {
        assertEquals(NORTH, dir.left().left().left().left());
    }


    // RIGHT()
    @Test
    public void NORTHTurnedRightShouldReturnEast() {
        assertEquals(EAST, dir.right());
    }

    @Test
    public void NORTHTurnedRightFourTimesShouldReturnNorth() {
        assertEquals(NORTH, dir.right().right().right().right());
    }
}