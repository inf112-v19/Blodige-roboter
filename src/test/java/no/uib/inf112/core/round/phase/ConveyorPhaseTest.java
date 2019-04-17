package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Direction;
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
        roboRally = GameGraphics
                .createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_complex_rotation_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        phase = new ConveyorPhase(0);
    }


    private void runPhase() {
        phase.startPhase(roboRally.getCurrentMap());
    }

    private void testPhase(int startX, int startY, Direction startDir, int endX, int endY, Direction endDir) {
        player.teleport(startX, startY);
        player.setDirection(startDir);
        roboRally.getCurrentMap().update(0);

        runPhase();

        assertEquals(endX, player.getX());
        assertEquals(endY, player.getY());
        assertEquals(endDir, player.getDirection());
    }

    @Test
    public void rotateLeftCircleNormal() {
        player.teleport(1, 2);
        player.setDirection(SOUTH);
        runPhase();

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(EAST, player.getDirection());

        runPhase();

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(NORTH, player.getDirection());

        runPhase();

        assertEquals(2, player.getX());
        assertEquals(2, player.getY());
        assertEquals(WEST, player.getDirection());

        runPhase();

        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
        assertEquals(SOUTH, player.getDirection());
    }

    @Test
    public void rotateRightCircleNormal() {
        player.teleport(3, 2);
        player.setDirection(EAST);
        runPhase();

        assertEquals(4, player.getX());
        assertEquals(2, player.getY());
        assertEquals(SOUTH, player.getDirection());

        runPhase();

        assertEquals(4, player.getX());
        assertEquals(1, player.getY());
        assertEquals(WEST, player.getDirection());

        runPhase();

        assertEquals(3, player.getX());
        assertEquals(1, player.getY());
        assertEquals(NORTH, player.getDirection());

        runPhase();

        assertEquals(3, player.getX());
        assertEquals(2, player.getY());
        assertEquals(EAST, player.getDirection());
    }

    @Test
    public void rotateLeftCircleExpress() {
        player.teleport(5, 2);
        player.setDirection(SOUTH);
        roboRally.getCurrentMap().update(0);

        runPhase();

        assertEquals(6, player.getX());
        assertEquals(1, player.getY());
        assertEquals(NORTH, player.getDirection());

        runPhase();

        assertEquals(5, player.getX());
        assertEquals(2, player.getY());
        assertEquals(SOUTH, player.getDirection());
    }

    @Test
    public void rotateLeftCircleExpress2() {
        player.teleport(5, 1);
        player.setDirection(WEST);
        roboRally.getCurrentMap().update(0);

        runPhase();

        assertEquals(6, player.getX());
        assertEquals(2, player.getY());
        assertEquals(EAST, player.getDirection());

        runPhase();

        assertEquals(5, player.getX());
        assertEquals(1, player.getY());
        assertEquals(WEST, player.getDirection());
    }

    @Test
    public void rotateRightCircleExpress() {
        player.teleport(7, 2);
        player.setDirection(EAST);
        roboRally.getCurrentMap().update(0);

        runPhase();

        assertEquals(8, player.getX());
        assertEquals(1, player.getY());
        assertEquals(WEST, player.getDirection());

        runPhase();

        assertEquals(7, player.getX());
        assertEquals(2, player.getY());
        assertEquals(EAST, player.getDirection());
    }

    @Test
    public void rotateRightCircleExpress2() {
        player.teleport(7, 1);
        player.setDirection(NORTH);
        roboRally.getCurrentMap().update(0);

        runPhase();

        assertEquals(8, player.getX());
        assertEquals(2, player.getY());
        assertEquals(SOUTH, player.getDirection());

        runPhase();

        assertEquals(7, player.getX());
        assertEquals(1, player.getY());
        assertEquals(NORTH, player.getDirection());
    }

    @Test
    public void fromWestGoNorth() {
        //normal conveyors
        testPhase(2, 4, NORTH, 2, 5, NORTH);
        testPhase(1, 5, WEST, 2, 5, NORTH);

        //express conveyors
        testPhase(2, 9, SOUTH, 2, 10, NORTH);
        testPhase(1, 9, WEST, 2, 10, NORTH);
    }

    @Test
    public void fromEastGoNorth() {
        //normal conveyors
        testPhase(3, 4, NORTH, 3, 5, NORTH);
        testPhase(4, 5, EAST, 3, 5, NORTH);

        //express conveyors
        testPhase(3, 9, NORTH, 3, 10, NORTH);
        testPhase(4, 9, EAST, 3, 10, NORTH);
    }

    @Test
    public void fromHorizontalGoNorth() {
        //normal conveyors
        testPhase(20, 5, WEST, 21, 5, NORTH);
        testPhase(22, 5, EAST, 21, 5, NORTH);

        //express conveyors
        testPhase(20, 9, WEST, 21, 10, NORTH);
        testPhase(22, 9, EAST, 21, 10, NORTH);
    }

    @Test
    public void fromHorizontalGoSouth() {
        //normal conveyors
        testPhase(20, 4, WEST, 21, 4, SOUTH);
        testPhase(22, 4, EAST, 21, 4, SOUTH);

        //express conveyors
        testPhase(20, 8, WEST, 21, 3, SOUTH);
        testPhase(22, 8, EAST, 21, 3, SOUTH);
    }
}
