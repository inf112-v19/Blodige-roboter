package no.uib.inf112.core.player;

import org.junit.Test;

import static no.uib.inf112.core.player.Direction.*;
import static org.junit.Assert.*;

public class DirectionTest {

    private Direction dir;

    //INVERSE()
    @Test
    public void NORTHInvertedShouldReturnSOUTH() {
        dir = NORTH;
        assertEquals(SOUTH, dir.inverse());
    }

    @Test
    public void NORTHInvertedTwiceShouldReturnNORTH() {
        dir = NORTH;
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
        dir = NORTH;
        assertEquals(WEST, dir.left());
    }

    @Test
    public void NORTHTurnedLeftFourTimesShouldReturnNorth() {
        dir = NORTH;
        assertEquals(NORTH, dir.left().left().left().left());
    }
    
}