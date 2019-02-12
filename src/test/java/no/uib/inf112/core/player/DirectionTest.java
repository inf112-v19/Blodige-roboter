package no.uib.inf112.core.player;

import org.junit.Test;

import static no.uib.inf112.core.player.Direction.NORTH;
import static no.uib.inf112.core.player.Direction.SOUTH;
import static org.junit.Assert.*;

public class DirectionTest {




    @Test
    public void NORTHInvertedShouldReturnSOUTH() {
        Direction dir = NORTH;
        assertEquals(SOUTH, dir.inverse());
    }


}