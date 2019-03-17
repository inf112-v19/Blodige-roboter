package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest extends TestGraphics {

    private IPlayer testPlayer;
    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.getRoboRally();
        roboRally.changeMap(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx");
    }

    @Before
    public void setup() {
        testPlayer = roboRally.getPlayerHandler().testPlayer();
        testPlayer.getRobot().teleport(0, 0);
        testPlayer.getRobot().setDirection(Direction.NORTH);
    }

    @Test
    public void dealingOneDamageShouldDecreaseHealthByOne() {
        testPlayer.heal(1);
        int health = testPlayer.getHealth();
        int damage = 1;
        testPlayer.damage(damage);
        assertEquals(health - damage, testPlayer.getHealth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeDamageAmountShouldThrowException() {
        testPlayer.damage(-1);
    }

    @Test
    public void dealingMoreDamageThanHealthShouldDecreaseLivesByOne() {
        int health = testPlayer.getHealth();
        int lives = testPlayer.getLives();
        testPlayer.damage(health + 1);
        assertEquals(lives - 1, testPlayer.getLives());
    }

    @Test
    public void dealingLessDamageThanHealthShouldNotAffectLives() {
        int health = testPlayer.getHealth();
        int lives = testPlayer.getLives();
        testPlayer.damage(health - 1);
        assertEquals(lives, testPlayer.getLives());
    }

    @Test
    public void afterLoosingALifeHealthShouldBeRestoredToMax() {
        testPlayer.damage(testPlayer.getHealth() + 1);
        assertEquals(Player.MAX_HEALTH, testPlayer.getHealth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void healingNegativeAmountShouldThrowException() {
        testPlayer.heal(-1);
    }


    @Test
    public void healingWhenHealthIsFullShouldNotAffectHealth() {
        testPlayer.heal(Player.MAX_HEALTH);
        int health = testPlayer.getHealth();
        testPlayer.heal(10);
        assertEquals(health, testPlayer.getHealth());
    }

    @Test
    public void healingOneToDamagedPlayerShouldIncreaseHealthByOne() {
        testPlayer.damage(testPlayer.getHealth() - 1);
        int health = testPlayer.getHealth();
        testPlayer.heal(1);
        assertEquals(health + 1, testPlayer.getHealth());
    }

    @Test
    public void getFiveCardsFromNonPlayerShouldBePossible() {
        testPlayer = roboRally.getPlayerHandler().getPlayers().get(1);
        PlayerCard[] cards = new PlayerCard[5];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = testPlayer.getNextCard(i);
        }
        for (int i = 0; i < cards.length; i++) {
            assertTrue("Could not get 5 player cards", cards[i] instanceof PlayerCard);
        }

    }

}
