package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerTest extends TestGraphics {

    private Player testPlayer;
    private static RoboRally roboRally;
    private MapHandler map = roboRally.getCurrentMap();

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.getRoboRally();
    }

    @Before
    public void setup() {
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
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
        testPlayer = (NonPlayer) roboRally.getPlayerHandler().getPlayers().get(1);
        PlayerCard[] cards = new PlayerCard[5];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = testPlayer.getNextCard(i);
        }
        for (int i = 0; i < cards.length; i++) {
            assertTrue("Could not get 5 player cards", cards[i] instanceof PlayerCard);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void backupThrowsWhenXIsNegativeOne() {
        testPlayer.setBackup(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void backupThrowsWhenYIsNegativeOne() {
        testPlayer.setBackup(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void backupThrowsWhenXIsMapWidth() {
        int width = roboRally.getCurrentMap().getMapWidth();
        testPlayer.setBackup(width, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void backupThrowsWhenYIsMapHeight() {
        int height = roboRally.getCurrentMap().getMapHeight();
        testPlayer.setBackup(0, height);
    }

    @Test
    public void backupSucceedForAllValidPos() {
        int width = roboRally.getCurrentMap().getMapWidth();
        int height = roboRally.getCurrentMap().getMapHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                testPlayer.setBackup(x, y);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void playerConstructorThrowsWhenXIsNegativeOne() {
        new PlayerImpl(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void playerConstructorThrowsWhenYIsNegativeOne() {
        new PlayerImpl(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void playerConstructorThrowsWhenXIsMapWidth() {
        int width = roboRally.getCurrentMap().getMapWidth();
        new PlayerImpl(width, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void playerConstructorThrowsWhenYIsMapHeight() {
        int height = roboRally.getCurrentMap().getMapHeight();
        new PlayerImpl(0, height);
    }

    @Test
    public void playerConstructorSucceedForAllValidPos() {
        int width = roboRally.getCurrentMap().getMapWidth();
        int height = roboRally.getCurrentMap().getMapHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                new PlayerImpl(x, y);
            }
        }
    }

    private class PlayerImpl extends Player {

        PlayerImpl(int x, int y) {
            super(x, y, Direction.NORTH, map);
        }

        @Override
        public PlayerCard getNextCard(int id) {
            return null;
        }
    }
}
