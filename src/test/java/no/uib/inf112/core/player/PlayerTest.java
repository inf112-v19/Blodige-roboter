package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PlayerTest extends TestGraphics {

    private Player testPlayer;
    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.getRoboRally();
    }

    @Before
    public void setup() {
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
        testPlayer.getRobot().teleport(0, 0);
        testPlayer.getRobot().setDirection(Direction.NORTH);
        /* Old cool but advanced testing
        PowerMockito.mockStatic(RoboRally.class);
        PowerMockito.mockStatic(PlayerHandler.class);

        PlayerHandler ph = mock(PlayerHandler.class);
        when(ph.mainPlayer()).thenReturn(testPlayer);

        MapHandler map = Mockito.mock(TiledMapHandler.class);
        when(map.getMapHeight()).thenReturn(RobotTest.HEIGHT);
        when(map.getMapWidth()).thenReturn(RobotTest.WIDTH);
        when(map.isOutsideBoard(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod();

        when(GameGraphics.getRoboRally().getPlayerHandler()).thenReturn(ph);
        when(GameGraphics.getRoboRally().getCurrentMap()).thenReturn(map);*/
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

}
