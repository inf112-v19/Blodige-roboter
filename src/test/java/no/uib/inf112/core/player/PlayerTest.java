package no.uib.inf112.core.player;

import no.uib.inf112.core.RoboRally;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player testPlayer;
    private int health;
    private int lives;

    @Before
    public void setup() {
        testPlayer = new Player(0,0, Direction.NORTH, true);
        health = testPlayer.getHealth();
        lives = testPlayer.getLives();
    }

    @Test
    public void dealingOneDamageShouldDecreaseHealthByOne() {
        testPlayer.damage(1);
        assertEquals(health - 1, testPlayer.getHealth());
    }

    @Test (expected = IllegalArgumentException.class)
    public void negativeDamageAmountShouldThrowException() {
        testPlayer.damage(-1);
    }

    @Test
    public void dealingMoreDamageThanHealthShouldDecreaseLivesByOne() {
        testPlayer.damage(health + 1);
        assertEquals(lives - 1, testPlayer.getLives());
    }

    @Test
    public void dealingLessDamageThanHealthShouldNotAffectLives() {
        testPlayer.damage(health - 1);
        assertEquals(lives, testPlayer.getLives());
    }

    @Test
    public void afterLoosingALifeHealthShouldBeRestoredToMax() {
        testPlayer.damage(health + 1);
        assertEquals(health, testPlayer.getHealth());
    }

    @Test (expected = IllegalArgumentException.class)
    public void healingNegativeAmountShouldThrowException() {
        testPlayer.heal(-1);
    }

    @Test
    public void healingWhenHealthIsFullShouldNotAffectHealth() {
        testPlayer.heal(10);
        assertEquals(health, testPlayer.getHealth());
    }

    @Test
    public void healingOneToDamagedPlayerShouldIncreaseHealthByOne() {
        testPlayer.damage(health - 1);
        health = testPlayer.getHealth();
        testPlayer.heal(1);
        assertEquals(health + 1, testPlayer.getHealth());
    }


}
