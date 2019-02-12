package no.uib.inf112.core.player;

import no.uib.inf112.core.RoboRally;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player testPlayer;

    @Before
    public void setup() {
        testPlayer = new Player(0,0, Direction.NORTH);
    }

    @Test
    public void dealingOneDamageToShouldDecreaseHealthByOne() {
        int health = testPlayer.getHealth();
        testPlayer.damage(1);
        assertEquals(health-1, testPlayer.getHealth());
    }

}
