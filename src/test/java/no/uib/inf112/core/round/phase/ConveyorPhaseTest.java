package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static no.uib.inf112.core.util.Direction.*;
import static org.junit.Assert.assertEquals;

public class ConveyorPhaseTest extends TestGraphics {
    private RoboRally roboRally;
    private IPlayer player;
    private ConveyorPhase phase;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        phase = new ConveyorPhase(0);
    }


    private void runPhase(){
        roboRally.getCurrentMap().update(0);
        phase.startPhase(roboRally.getCurrentMap());
    }

    @Test
    public void rotateLeftCircleNormal() {
        player.teleport(1, 8);
        player.setDirection(SOUTH);
        runPhase();

        assertEquals(1, player.getX());
        assertEquals(7, player.getY());
        assertEquals(EAST, player.getDirection());

        runPhase();

        assertEquals(2, player.getX());
        assertEquals(7, player.getY());
        assertEquals(NORTH, player.getDirection());

        runPhase();

        assertEquals(2, player.getX());
        assertEquals(8, player.getY());
        assertEquals(WEST, player.getDirection());

        runPhase();

        assertEquals(1, player.getX());
        assertEquals(8, player.getY());
        assertEquals(SOUTH, player.getDirection());
    }
    @Test
    public void rotateRightCircleNormal() {
        player.teleport(3, 8);
        player.setDirection(EAST);
        runPhase();

        assertEquals(4, player.getX());
        assertEquals(8, player.getY());
        assertEquals(SOUTH, player.getDirection());

        runPhase();

        assertEquals(4, player.getX());
        assertEquals(7, player.getY());
        assertEquals(WEST, player.getDirection());

        runPhase();

        assertEquals(3, player.getX());
        assertEquals(7, player.getY());
        assertEquals(NORTH, player.getDirection());

        runPhase();

        assertEquals(3, player.getX());
        assertEquals(8, player.getY());
        assertEquals(EAST, player.getDirection());
    }

    @Test
    public void rotateLeftCircleExpress() {
        player.teleport(5, 8);
        player.setDirection(SOUTH);

        runPhase();

        assertEquals(6, player.getX());
        assertEquals(7, player.getY());
        assertEquals(NORTH, player.getDirection());

        runPhase();

        assertEquals(5, player.getX());
        assertEquals(8, player.getY());
        assertEquals(SOUTH, player.getDirection());
    }

    @Test
    public void rotateLeftCircleExpress2() {
        player.teleport(5, 7);
        player.setDirection(WEST);

        runPhase();

        assertEquals(6, player.getX());
        assertEquals(8, player.getY());
        assertEquals(EAST, player.getDirection());

        runPhase();

        assertEquals(5, player.getX());
        assertEquals(7, player.getY());
        assertEquals(WEST, player.getDirection());
    }

    @Test
    public void rotateRightCircleExpress() {
        player.teleport(7, 8);
        player.setDirection(EAST);

        runPhase();

        assertEquals(8, player.getX());
        assertEquals(7, player.getY());
        assertEquals(WEST, player.getDirection());

        runPhase();

        assertEquals(7, player.getX());
        assertEquals(8, player.getY());
        assertEquals(EAST, player.getDirection());
    }

    @Test
    public void rotateRightCircleExpress2() {
        player.teleport(7, 7);
        player.setDirection(NORTH);

        runPhase();

        assertEquals(8, player.getX());
        assertEquals(8, player.getY());
        assertEquals(SOUTH, player.getDirection());

        runPhase();

        assertEquals(7, player.getX());
        assertEquals(7, player.getY());
        assertEquals(NORTH, player.getDirection());
    }
}
