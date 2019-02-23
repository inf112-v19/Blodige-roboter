package no.uib.inf112.core.player;

import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RoboRally.class)
public class PlayerTest {

    private Player testPlayer;
    private int health;
    private int lives;

    @Before
    public void setup() {
        testPlayer = new Player(0, 0, Direction.NORTH, true);
        health = testPlayer.getHealth();
        lives = testPlayer.getLives();

        PowerMockito.mockStatic(RoboRally.class);
        PowerMockito.mockStatic(PlayerHandler.class);

        PlayerHandler ph = mock(PlayerHandler.class);
        when(ph.mainPlayer()).thenReturn(testPlayer);

        MapHandler map = Mockito.mock(TiledMapHandler.class);
        when(map.getMapHeight()).thenReturn(RobotTest.HEIGHT);
        when(map.getMapWidth()).thenReturn(RobotTest.WIDTH);
        when(map.isOutsideBoard(Mockito.anyInt(), Mockito.anyInt())).thenCallRealMethod();

        when(RoboRally.getPlayerHandler()).thenReturn(ph);
        when(RoboRally.getCurrentMap()).thenReturn(map);
    }

    @Test
    public void dealingOneDamageShouldDecreaseHealthByOne() {
        testPlayer.damage(1);
        assertEquals(health - 1, testPlayer.getHealth());
    }

    @Test(expected = IllegalArgumentException.class)
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

    @Test(expected = IllegalArgumentException.class)
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
