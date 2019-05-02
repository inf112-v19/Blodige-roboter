package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.round.phase.ActionPhase;
import no.uib.inf112.core.round.phase.Phase;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FlagRegistrationTest extends TestGraphics {

    private IPlayer player;
    private RoboRally roborally;
    private MapHandler map;
    private Phase phase;

    @Before
    public void setUp() {
        roborally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "flag_test_map.tmx", 1);
        map = GameGraphics.getRoboRally().getCurrentMap();
        player = roborally.getPlayerHandler().mainPlayer();
        phase = new ActionPhase(TileType.FLAG, 0);
    }

    @Test
    public void analyzingTestMapShouldReturnFlagCountIs4() {
        assertEquals(4, roborally.getPlayerHandler().getFlagCount());
    }

    @Test
    public void playerHasNoFlags() {
        assertEquals(0, player.getFlags());
    }

    @Test
    public void checkIfLandOnFlagRegistersFlag() {
        for (int i = 1; i < 5; i++) {
            player.move(Movement.MOVE_1);
            map.update(0);
            phase.startPhase(map);
            assertEquals(i, player.getFlags());
        }
    }

    @Test
    public void checkIfLandOnFlag2Before1DoesNotRegister() {
        player.move(Movement.MOVE_2);
        map.update(0);
        phase.startPhase(map);
        assertEquals(0, player.getFlags());
    }

    @Test
    public void checkIfLandOnFlag3Before1DoesNotRegister() {
        player.move(Movement.MOVE_3);
        map.update(0);
        phase.startPhase(map);
        assertEquals(0, player.getFlags());
    }


    @Test
    public void registerFlagVisitFor1Flag() {
        player.registerFlagVisit();
        assertEquals(1, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor2Flags() {
        registerFlagVisits(2, player);
        assertEquals(2, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor8Flags() {
        registerFlagVisits(8, player);
        assertEquals(8, player.getFlags());
    }

    @Test
    public void registerFlagVisitsDoesNotRegisterFlagIfNotCalled() {
        player.registerFlagVisit();
        assertNotEquals(2, player.getFlags());
    }

    @Test
    public void canGetFlag1Test() {
        assertTrue(player.canGetFlag(1));
    }

    @Test
    public void canGetFlag2Test() {
        player.registerFlagVisit();
        assertTrue(player.canGetFlag(2));
    }

    @Test
    public void canGetFlag3Test() {
        registerFlagVisits(2, player);
        assertTrue(player.canGetFlag(3));
    }

    @Test
    public void canGetFlag4Test() {
        registerFlagVisits(3, player);
        assertTrue(player.canGetFlag(4));
    }

    @Test
    public void cantGetFlag5Test() {
        registerFlagVisits(3, player);
        assertFalse(player.canGetFlag(5));
    }

    @Test
    public void cantGetFlag6Test() {
        registerFlagVisits(4, player);
        assertFalse(player.canGetFlag(6));
    }

    @Test
    public void cantGetFlag7Test() {
        registerFlagVisits(5, player);
        assertFalse(player.canGetFlag(7));
    }

    @Test
    public void cantGetFlag8Test() {
        registerFlagVisits(6, player);
        assertFalse(player.canGetFlag(8));
    }
}
