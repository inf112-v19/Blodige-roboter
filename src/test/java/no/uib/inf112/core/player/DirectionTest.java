package no.uib.inf112.core.player;

import org.junit.Test;

import static no.uib.inf112.core.player.Direction.*;
import static org.junit.Assert.*;

public class DirectionTest {




    @Test
    public void NORTHInvertedShouldReturnSOUTH() {
        Direction dir = NORTH;
        assertEquals(SOUTH, dir.inverse());
    }

    @Test
    public void NORTHInvertedTwiceShouldReturnNORTH() {
        Direction dir = NORTH;
        assertEquals(NORTH, dir.inverse().inverse());
    }

    @Test
    public void EASTInvertedShouldReturnWEST() {
        Direction dir = EAST;
        assertEquals(WEST, dir.inverse());
    }
}