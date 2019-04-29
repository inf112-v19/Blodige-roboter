package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static no.uib.inf112.core.GameGraphics.MAP_FOLDER;
import static org.junit.Assert.*;

public class PlayerHandlerTest extends TestGraphics {

    private static RoboRally roboRally;
    private PlayerHandler testHandler;
    public static MapHandler map;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 1);
        map = roboRally.getCurrentMap();
    }

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

    @Test
    public void whenEveryOneRegistersAllFlagsGameShouldEnd() {
        roboRally = GameGraphics.createRoboRally(MAP_FOLDER + File.separatorChar + "checkmate.tmx", 8);
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        for (IPlayer player : players) {
            registerFlagVisits(roboRally.getPlayerHandler().getFlagCount(), player);
        }
        roboRally.getPlayerHandler().checkGameOver();
        assertTrue(roboRally.getPlayerHandler().isGameOver());
        assertEquals(roboRally.getPlayerHandler().getWonPlayers().size(), roboRally.getPlayerHandler().getPlayerCount());
    }

    @Test
    public void whenEveryoneIsDestroyedGameShouldEnd() {
        testHandler = new PlayerHandler(8, map);
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        for (IPlayer player : players) {
            player.destroy();
        }
        roboRally.getPlayerHandler().checkGameOver();
        assertTrue(roboRally.getPlayerHandler().isGameOver());
        assertEquals(roboRally.getPlayerHandler().getWonPlayers().size(), roboRally.getPlayerHandler().getPlayerCount());
    }

//    @Test
//    public void dfads() {
//        roboRally = GameGraphics.createRoboRally(MAP_FOLDER + File.separatorChar + "checkmate.tmx", 3);
//        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
//        players.get(2).setName("First");
//        players.get(2).registerFlagVisits(2);
//        roboRally.getPlayerHandler().checkGameOver();
//        players.get(1).setName("Secound");
//        players.get(1).registerFlagVisits(2);
//        roboRally.getPlayerHandler().checkGameOver();
//        String[] strings = roboRally.getPlayerHandler().rankPlayers();
//        for (String string : strings) {
//            System.out.println(string);
//        }
//    }
//
//    @Test
//    public void dadf() {
//        roboRally = GameGraphics.createRoboRally(MAP_FOLDER + File.separatorChar + "checkmate.tmx", 4);
//        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
//        players.get(0).setName("Last");
//        players.get(0).destroy();
//        roboRally.getPlayerHandler().checkGameOver();
//        players.get(1).setName("First");
//        players.get(1).registerFlagVisits(2);
//        roboRally.getPlayerHandler().checkGameOver();
//        players.get(0).setName("Third");
//        players.get(0).registerFlagVisit();
//        roboRally.getPlayerHandler().checkGameOver();
//        players.get(1).setName("Secound");
//        players.get(1).registerFlagVisits(2);
//        roboRally.getPlayerHandler().checkGameOver();
//
//        String[] strings = roboRally.getPlayerHandler().rankPlayers();
//        for (String string : strings) {
//            System.out.println(string);
//        }
//    }
}
