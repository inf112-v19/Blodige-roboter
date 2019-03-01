package no.uib.inf112.core.player;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlagRegistrationTest {

    Player player = new Player(0,0, Direction.NORTH, true);

    @Test
    public void playerHasNoFlags(){
        assertEquals(0, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor1Flag(){
        player.registerFlagVisit();
        assertEquals(1, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor2Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(2, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor3Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(3, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor4Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(4, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor5Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(5, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor6Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(6, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor7Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(7, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor8Flags(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(8, player.getFlags());
    }

    @Test
    public void canGetFlag1Test(){
        assertTrue(player.canGetFlag(1));
    }

    @Test
    public void canGetFlag2Test(){
        player.registerFlagVisit();
        assertTrue(player.canGetFlag(2));
    }

    @Test
    public void canGetFlag3Test(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertTrue(player.canGetFlag(3));
    }

    @Test
    public void canGetFlag4Test(){
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertTrue(player.canGetFlag(4));
    }
}
