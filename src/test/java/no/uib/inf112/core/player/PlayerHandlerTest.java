package no.uib.inf112.core.player;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerHandlerTest {

    private PlayerHandler testHandler;

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
        testHandler.generatePlayers(true);
        assertEquals(2, testHandler.getPlayers().size());
    }

    @Test
    public void creatingHandlerShouldInitializeProgramDeck() {
        testHandler = new PlayerHandler(2);
        assertNotNull(testHandler.getDeck());
    }

    @Test
    public void generating5PlayersShouldResultInListOfPlayersOfSize5() {
        testHandler = new PlayerHandler(5);
        testHandler.generatePlayers(true);
        assertEquals(5, testHandler.getPlayers().size());
    }

    @Test
    public void whenGeneratedEachPlayerShouldHaveAUniqueDock() {
        testHandler = new PlayerHandler(8);
        testHandler.generatePlayers(true);

        ArrayList<Integer> playerDocks = new ArrayList<>();
        for (Player player : testHandler.getPlayers()) {
            assertFalse(playerDocks.contains(player.getDock()));
            playerDocks.add(player.getDock());
        }
    }
}
