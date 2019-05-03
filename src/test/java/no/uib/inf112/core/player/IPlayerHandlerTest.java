package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.GameMapTest;
import no.uib.inf112.core.util.Direction;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class IPlayerHandlerTest extends GameMapTest {

    private IPlayerHandler ph;

    @Before
    public void before() {
        RoboRally rr = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "end_game_test_map.tmx", 4);
        ph = rr.getPlayerHandler();
        List<IPlayer> players = ph.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            IPlayer player = players.get(i);
            player.teleport(i, 0);
            player.setBackup(i, 0);
            player.setDirection(Direction.NORTH);
        }
    }


    @Test
    public void mostFlagsWinTest() {
        ph.mainPlayer().registerFlagVisit();
        List<IPlayer> ranks = ph.rankPlayers();
        assertEquals(ph.mainPlayer(), ranks.get(0));

        ph.checkGameOver();
        assertFalse(ph.isGameOver());
    }

    @Test
    public void equalFlagsOldestWin() {
        List<IPlayer> players = ph.getPlayers();
        //all get destroyed at once
        for (IPlayer player : players) {
            player.destroy();
        }
        List<IPlayer> ranks = ph.rankPlayers();
        for (int i = 0; i < ranks.size(); i++) {
            assertEquals(players.get(i), ranks.get(i));
        }

        ph.checkGameOver();
        assertTrue(ph.isGameOver());
    }

    @Test
    public void rememberOldTheDead() {
        ph.rankPlayers();
        ph.removeMainPlayer();
        List<IPlayer> ranks = ph.rankPlayers();
        for (IPlayer player : ranks) {
            assertNotNull(player);
        }
        ph.checkGameOver();
        assertFalse(ph.isGameOver());
    }

    @Test
    public void lastManStandingWin() {
        List<IPlayer> players = ph.getPlayers();
        List<IPlayer> ranks = null;
        for (IPlayer player : players) {
            player.destroy();
            ranks = ph.rankPlayers();
        }
        assertNotNull(ranks);
        for (int i = 0; i < ranks.size(); i++) {
            assertEquals(players.get(i), ranks.get(i));
        }
        ph.checkGameOver();
        assertTrue(ph.isGameOver());
    }

}
