package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.desktop.Main;
import no.uib.inf112.desktop.TestGraphics;
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


public class PlayerTest extends TestGraphics {

    private Player testPlayer;
    private int health;
    private int lives;
    private RoboRally roboRally;

    @Before
    public void setup() {
        Main.HEADLESS = true;
        roboRally = new RoboRally();
        GameGraphics.SetRoboRally(roboRally);
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
        testPlayer.getRobot().teleport(0, 0);
        testPlayer.getRobot().setDirection(Direction.NORTH);
        health = testPlayer.getHealth();
        lives = testPlayer.getLives();
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
