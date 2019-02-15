package no.uib.inf112.core.player;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

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


    //TODO Test if constructor works as expected.
    //TODO Test if generatePlayers() words as expected.
}
