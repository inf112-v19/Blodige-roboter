package no.uib.inf112.core.player;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerHandlerTest {

    private PlayerHandler testHandler;

    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerCount2() {
        testHandler = new PlayerHandler(2);
        assertEquals(2, testHandler.getPlayerCount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void creatingHandlerWith1PlayerShouldThrowException() {
        testHandler = new PlayerHandler(1);
    }

    @Test (expected = IllegalArgumentException.class)
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

    

    //TODO Test if constructor works as expected.
    //TODO Test if generatePlayers() words as expected.
}
