package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FlagRegistrationTest extends TestGraphics {

    private AbstractPlayer player;
    private static RoboRally roborally;

    @BeforeClass
    public static void beforeClass() {
        roborally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "flag_test_map.tmx", 1);

    }

    @Before
    public void setUp() {
        GameGraphics.getRoboRally().getPlayerHandler().generateOnePlayer();
        MapHandler map = GameGraphics.getRoboRally().getCurrentMap();
        player = new NonPlayer(0, 0, Direction.NORTH, map);
    }

    @Test
    public void playerHasNoFlags() {
        assertEquals(0, player.getFlags());
    }

    @Test
    public void checkIfLandOnFlag1RegistersFlag() {
        roborally.getPlayerHandler().generateOnePlayer();
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());

        assertEquals(1, roborally.getPlayerHandler().getPlayers().get(0).getFlags());
    }


    @Test
    public void checkIfLandOnFlag1and2RegistersFlag() {
        roborally.getPlayerHandler().generateOnePlayer();
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());

        assertEquals(2, roborally.getPlayerHandler().getPlayers().get(0).getFlags());
    }

    @Test
    public void checkIfLandOnFlag1and2and3RegistersFlag() {
        roborally.getPlayerHandler().generateOnePlayer();
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());

        assertEquals(3, roborally.getPlayerHandler().getPlayers().get(0).getFlags());
    }

    @Test
    public void checkIfLandOnFlag1and2and3and4RegistersFlag() {
        roborally.getPlayerHandler().generateOnePlayer();
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_1);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());

        assertEquals(4, roborally.getPlayerHandler().getPlayers().get(0).getFlags());
    }

    @Test
    public void checkIfLandOnFlag2Before1DoesNotRegister() {
        roborally.getPlayerHandler().generateOnePlayer();
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_2);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());

        assertEquals(0, roborally.getPlayerHandler().getPlayers().get(0).getFlags());
    }

    @Test
    public void checkIfLandOnFlag3Before1DoesNotRegister() {
        roborally.getPlayerHandler().generateOnePlayer();
        roborally.getPlayerHandler().getPlayers().get(0).move(Movement.MOVE_3);
        roborally.mapInteractOnUser.scan(roborally.getCurrentMap().getEntities());

        assertEquals(0, roborally.getPlayerHandler().getPlayers().get(0).getFlags());
    }


    @Test
    public void registerFlagVisitFor1Flag() {
        player.registerFlagVisit();
        assertEquals(1, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor2Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(2, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor3Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(3, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor4Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(4, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor5Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(5, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor6Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(6, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor7Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(7, player.getFlags());
    }

    @Test
    public void registerFlagVisitFor8Flags() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertEquals(8, player.getFlags());
    }

    @Test
    public void registerFlagVisitsDoesNotRegisterFlagIfNotCalled() {
        player.registerFlagVisit();
        assertFalse(player.getFlags() == 2);
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
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertTrue(player.canGetFlag(3));
    }

    @Test
    public void canGetFlag4Test() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertTrue(player.canGetFlag(4));
    }

    @Test
    public void cantGetFlag5Test() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertFalse(player.canGetFlag(5));
    }

    @Test
    public void cantGetFlag6Test() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertFalse(player.canGetFlag(6));
    }

    @Test
    public void cantGetFlag7Test() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertFalse(player.canGetFlag(7));
    }

    @Test
    public void cantGetFlag8Test() {
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        player.registerFlagVisit();
        assertFalse(player.canGetFlag(8));
    }
}
