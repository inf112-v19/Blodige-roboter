package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.desktop.Main;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerHandlerTest extends TestGraphics {

    private PlayerHandler testHandler;
    private RoboRally roboRally;

    @Before
    public void setUp() {
        Main.HEADLESS = true;
        roboRally = new RoboRally();
        GameGraphics.SetRoboRally(roboRally);
    }

    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerCount2() {
        testHandler = new PlayerHandler(2);
        assertEquals(2, testHandler.getPlayerCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingHandlerWith1PlayerShouldThrowException() {
        testHandler = new PlayerHandler(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingHandlerWith9PlayersShouldThrowException() {
        testHandler = new PlayerHandler(9);
    }

    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerListOfCapacity2() {
        testHandler = new PlayerHandler(2);
        assertEquals(2, testHandler.getPlayers().size());
    }

    /*@Test
    public void creatingHandlerShouldInitializeProgramDeck() {
        testHandler = new PlayerHandler(2);
        assertNotNull(testHandler.getDeck());
    }*/

    @Test
    public void generating5PlayersShouldResultInListOfPlayersOfSize5() {
        testHandler = new PlayerHandler(5);
        assertEquals(5, testHandler.getPlayers().size());
    }

    @Test
    public void whenGeneratedEachPlayerShouldHaveAUniqueDock() {
        testHandler = new PlayerHandler(8);

        ArrayList<Integer> playerDocks = new ArrayList<>();
        for (Player player : testHandler.getPlayers()) {
            assertFalse(playerDocks.contains(player.getDock()));
            playerDocks.add(player.getDock());
        }
    }
}
