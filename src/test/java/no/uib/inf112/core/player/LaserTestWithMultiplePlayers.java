package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.round.phase.LaserPhase;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class LaserTestWithMultiplePlayers extends TestGraphics {
    private static RoboRally roboRally;
    private static MapHandler map;
    private static List<IPlayer> players;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "laser_test_map.tmx", 2);
        map = roboRally.getCurrentMap();
        players = roboRally.getPlayerHandler().getPlayers();
    }

    @Test
    public void twoPlayersFacingEachotherShouldDecreaseBothHealthByOne() {
        IPlayer first = players.get(0);
        int firstHealthBefore = first.getHealth();
        IPlayer secound = players.get(1);
        int secondHealthBefore = secound.getHealth();
        first.teleport(0, 0);
        secound.teleport(0, 1);
        secound.move(Movement.U_TURN);
        new LaserPhase(0).startPhase(map);
        assertEquals(firstHealthBefore - 1, first.getHealth());
        assertEquals(secondHealthBefore - 1, secound.getHealth());
    }

    @Test
    public void onePlayerFacingOtherPlayerShouldDecreaseOnesHealthByOne() {
        IPlayer first = players.get(0);
        int firstHealthBefore = first.getHealth();
        IPlayer secound = players.get(1);
        int secondHealthBefore = secound.getHealth();
        first.teleport(0, 0);
        secound.teleport(0, 1);
        new LaserPhase(0).startPhase(map);
        assertEquals(firstHealthBefore, first.getHealth());
        assertEquals(secondHealthBefore - 1, secound.getHealth());
    }
}
