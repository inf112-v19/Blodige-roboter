package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerHandlerTest extends TestGraphics {

    private PlayerHandler testHandler;
    private MapHandler map = GameGraphics.getRoboRally().getCurrentMap();


    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerCount2() {
        testHandler = new PlayerHandler(2, map);
        assertEquals(2, testHandler.getPlayerCount());
    }


    @Test(expected = IllegalArgumentException.class)
    public void creatingHandlerWith9PlayersShouldThrowException() {
        testHandler = new PlayerHandler(9, map);
    }

    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerListOfCapacity2() {
        testHandler = new PlayerHandler(2, map);
        assertEquals(2, testHandler.getPlayers().size());
    }

    @Test
    public void generating5PlayersShouldResultInListOfPlayersOfSize5() {
        testHandler = new PlayerHandler(5, map);
        assertEquals(5, testHandler.getPlayers().size());
    }

    @Test
    public void whenGeneratedEachPlayerShouldHaveAUniqueDock() {
        testHandler = new PlayerHandler(8, map);

        ArrayList<Integer> playerDocks = new ArrayList<>();
        for (IPlayer player : testHandler.getPlayers()) {
            assertFalse(playerDocks.contains(player.getDock()));
            playerDocks.add(player.getDock());
        }
    }
}
