package no.uib.inf112.core.player;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlagRegistrationTest {

    Player player = new Player(0,0, Direction.NORTH, true);

    @Test
    public void playerHasNoFlags(){
        assertEquals(0, player.getFlags());
    }
}
