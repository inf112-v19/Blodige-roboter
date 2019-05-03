package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;


public class PlayerTest extends TestGraphics {

    private static IPlayer testPlayer;
    private static RoboRally roboRally;
    private static MapHandler map;

    @BeforeClass
    public static void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 1);
        map = roboRally.getCurrentMap();
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
    }

    @Before
    public void initialize() {
        testPlayer.teleport(0, 0);
        testPlayer.setDirection(Direction.NORTH);
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
        assertEquals(IPlayer.MAX_HEALTH, testPlayer.getHealth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void healingNegativeAmountShouldThrowException() {
        testPlayer.heal(-1);
    }


    @Test
    public void healingWhenHealthIsFullShouldNotAffectHealth() {
        testPlayer.heal(IPlayer.MAX_HEALTH);
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
        NonPlayer player = new NonPlayer(1, 1, Direction.NORTH, map, new ComparableTuple<>("Black", Color.BLACK));

        //noinspection unchecked
        ComparableTuple<Card, IPlayer>[] cards = (ComparableTuple<Card, IPlayer>[]) new ComparableTuple[5];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = player.getNextCard(i);
        }
        for (ComparableTuple<Card, IPlayer> card : cards) {
            assertNotNull("Could not get 5 player cards", card);
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
                try {
                    testPlayer.setBackup(x, y);
                } catch (IllegalArgumentException e) {
                    fail("Player backup threw exception when not expected, x=" + x + ", y=" + y);
                }
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
                try {
                    new PlayerImpl(x, y);
                } catch (IllegalArgumentException e) {
                    fail("Player constructor threw exception when not expected x=" + x + ", y=" + y);
                }
            }
        }
    }

    private class PlayerImpl extends AbstractPlayer {

        PlayerImpl(int x, int y) {
            super(x, y, Direction.NORTH, map, new ComparableTuple<>("Black", Color.BLACK));
        }

        @Override
        public ComparableTuple<Card, IPlayer> getNextCard(int id) {
            return null;
        }

        @Override
        public int getId() {
            return -1;
        }

        @Override
        public void clean(@NotNull Tile tile) {
            //This method does not do anything for this test class
        }

        @Nullable
        @Override
        public List<Attribute> requiredAttributes() {
            return null;
        }

        @Nullable
        @Override
        public List<Class<? extends Tile>> requiredSuperClasses() {
            return null;
        }

        @Override
        public boolean canDoAction(@NotNull Tile tile) {
            return false;
        }
    }
}
