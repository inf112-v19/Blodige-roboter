package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.multiplayer.dtos.NewGameDto;
import no.uib.inf112.core.multiplayer.dtos.PlayerDto;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MultiPlayerHandlerTest extends TestGraphics {

    private static RoboRally roboRally;
    private static MapHandler map;
    private NewGameDto newGameDto;

    @BeforeClass
    public static void beforeClass() {
        List<PlayerDto> playerDtos = new ArrayList<>();
        playerDtos.add(new PlayerDto());
        playerDtos.add(new PlayerDto());
        NewGameDto newGameDto = new NewGameDto(TEST_MAP_FOLDER + File.separatorChar + "player_test_map", playerDtos, 0);
        roboRally = GameGraphics.createRoboRallyMultiplayer(newGameDto, null);
        map = roboRally.getCurrentMap();
    }

    @Before
    public void setUp() {
        List<PlayerDto> playerDtos = new ArrayList<>();
        playerDtos.add(new PlayerDto());
        playerDtos.add(new PlayerDto());
        newGameDto = new NewGameDto(TEST_MAP_FOLDER + File.separatorChar + "player_test_map", playerDtos, 0);
    }

    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerCount2() {
        IPlayerHandler testHandler = new MultiPlayerHandler(newGameDto, map, null);
        assertEquals(2, testHandler.getPlayerCount());
    }


    @Test(expected = IllegalArgumentException.class)
    public void creatingHandlerWith9PlayersShouldThrowException() {
        for (int i = 0; i < 9 - 2; i++) {
            newGameDto.players.add(new PlayerDto());
        }
        new MultiPlayerHandler(newGameDto, map, null);
    }

    @Test
    public void creatingHandlerWith2PlayersShouldResultInPlayerListOfCapacity2() {
        IPlayerHandler testHandler = new MultiPlayerHandler(newGameDto, map, null);
        assertEquals(2, testHandler.getPlayers().size());
    }

    @Test
    public void generating5PlayersShouldResultInListOfPlayersOfSize5() {
        for (int i = 0; i < 5 - 2; i++) {
            newGameDto.players.add(new PlayerDto());
        }
        IPlayerHandler testHandler = new MultiPlayerHandler(newGameDto, map, null);
        assertEquals(5, testHandler.getPlayers().size());
    }

    @Test
    public void whenGeneratedEachPlayerShouldHaveAUniqueDock() {
        for (int i = 0; i < 8 - 2; i++) {
            newGameDto.players.add(new PlayerDto());
        }
        IPlayerHandler testHandler = new MultiPlayerHandler(newGameDto, map, null);

        ArrayList<Integer> playerDocks = new ArrayList<>();
        for (IPlayer player : testHandler.getPlayers()) {
            assertFalse(playerDocks.contains(player.getDock()));
            playerDocks.add(player.getDock());
        }
    }

    @Test
    public void whenEveryOneRegistersAllFlagsGameShouldEnd() {
        RoboRally roboRally = GameGraphics.createRoboRallyMultiplayer(newGameDto, null);
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
        RoboRally roboRally = GameGraphics.createRoboRallyMultiplayer(newGameDto, null);
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        for (IPlayer player : players) {
            player.destroy();
        }
        roboRally.getPlayerHandler().checkGameOver();
        assertTrue(roboRally.getPlayerHandler().isGameOver());
        assertEquals(roboRally.getPlayerHandler().getWonPlayers().size(), roboRally.getPlayerHandler().getPlayerCount());
    }

}