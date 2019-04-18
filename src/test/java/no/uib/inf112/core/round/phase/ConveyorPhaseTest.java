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

@SuppressWarnings("PointlessArithmeticExpression")
public class ConveyorPhaseTest extends TestGraphics {

    private RoboRally roboRally;
    private IPlayer player;
    private ConveyorPhase phase;

    private static final int EXPRESS_DIST = 4;

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

        assertEquals("Different x", endX, player.getX());
        assertEquals("Different y", endY, player.getY());
        assertEquals("Different direction", endDir, player.getDirection());
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
        testPhase(1, 10, WEST, 2, 10, NORTH);
    }


    @Test
    public void fromEastGoNorth() {
        int ttx = 4; //testing tile x
        int tty = 5; //testing tile y

        //normal conveyors
        testPhase(ttx + 0, tty - 1, NORTH, ttx, tty, NORTH);
        testPhase(ttx + 1, tty + 0, EAST, ttx, tty, SOUTH);

        //express conveyors
        //the added +1 in endY is there as the express conveyor wil run twice
        testPhase(ttx + 0, tty - 1 + EXPRESS_DIST, NORTH, ttx, tty + EXPRESS_DIST + 1, NORTH);
        testPhase(ttx + 1, tty + 0 + EXPRESS_DIST, EAST, ttx, tty + EXPRESS_DIST + 1, SOUTH);
    }

    @Test
    public void fromWestGoSouth() {
        int ttx = 8; //testing tile x
        int tty = 4; //testing tile y
        Direction ttd = SOUTH; //testing tile direction
        Direction fromDir = WEST;

        int dx1 = 0; //delta x 1
        int dy1 = 1; //delta y 1

        int dx2 = -1; //delta x 2
        int dy2 = 0; //delta y 2

        //normal conveyors
        testPhase(ttx + dx1, tty + dy1, NORTH, ttx, tty, NORTH);
        testPhase(ttx + dx2, tty + dy2, fromDir, ttx, tty, SOUTH);

        //express conveyors
        //the added +1 in endY is there as the express conveyor wil run twice
        int expressDelta = EXPRESS_DIST + ttd.getDy() + ttd.getDx();
        testPhase(ttx + dx1, tty + dy1 + EXPRESS_DIST, NORTH, ttx, tty + expressDelta, NORTH);
        testPhase(ttx + dx2, tty + dy2 + EXPRESS_DIST, fromDir, ttx, tty + expressDelta, SOUTH);
    }

    @Test
    public void fromEastGoSouth() {
        int ttx = 10; //testing tile x
        int tty = 4; //testing tile y
        Direction ttd = SOUTH; //testing tile direction
        Direction fromDir = EAST;

        int dx1 = 0; //delta x 1
        int dy1 = 1; //delta y 1

        int dx2 = 1; //delta x 2
        int dy2 = 0; //delta y 2

        //normal conveyors
        testPhase(ttx + dx1, tty + dy1, NORTH, ttx, tty, NORTH);
        testPhase(ttx + dx2, tty + dy2, fromDir, ttx, tty, SOUTH);

        //express conveyors
        //the added +1 in endY is there as the express conveyor wil run twice
        testPhase(ttx + dx1, tty + dy1 + EXPRESS_DIST, NORTH, ttx + ttd.getDx(), tty + EXPRESS_DIST + ttd.getDy(),
                  NORTH);
        testPhase(ttx + dx2, tty + dy2 + EXPRESS_DIST, fromDir, ttx + ttd.getDx(), tty + EXPRESS_DIST + ttd.getDy(),
                  ttd);
    }

    @Test
    public void fromNorthGoEast() {
        int ttx = 14; //testing tile x
        int tty = 4; //testing tile y
        Direction ttd = EAST; //testing tile direction
        Direction fromDir = NORTH;

        int dx1 = 0; //delta x 1
        int dy1 = 1; //delta y 1

        int dx2 = -1; //delta x 2
        int dy2 = 0; //delta y 2

        //normal conveyors
        testPhase(ttx + dx1, tty + dy1, NORTH, ttx, tty, NORTH);
        testPhase(ttx + dx2, tty + dy2, fromDir, ttx, tty, SOUTH);

        //express conveyors
        //the added +1 in endY is there as the express conveyor wil run twice
        testPhase(ttx + dx1, tty + dy1 + EXPRESS_DIST, NORTH, ttx + ttd.getDx(), tty + EXPRESS_DIST + ttd.getDy(),
                  NORTH);
        testPhase(ttx + dx2, tty + dy2 + EXPRESS_DIST, fromDir, ttx + ttd.getDx(), tty + EXPRESS_DIST + ttd.getDy(),
                  ttd);
    }

    @Test
    public void horizontalGoNorth() {
        int ttx = 11; //testing tile x
        int tty = 1; //testing tile y
        Direction ttd = NORTH;

        //normal conveyors
        testPhase(ttx - 1, tty, EAST, ttx, tty, ttd);
        testPhase(ttx + 1, tty, WEST, ttx, tty, ttd);

        //express conveyors
        testPhase(ttx - 1 + EXPRESS_DIST, tty, EAST, ttx + EXPRESS_DIST, tty + ttd.getDy(), ttd);
        testPhase(ttx + 1 + EXPRESS_DIST, tty, WEST, ttx + EXPRESS_DIST, tty + ttd.getDy(), ttd);
    }

    @Test
    public void horizontalGoSouth() {
        int ttx = 19; //testing tile x
        int tty = 1; //testing tile y
        Direction ttd = SOUTH;

        //normal conveyors
        testPhase(ttx - 1, tty, EAST, ttx, tty, ttd);
        testPhase(ttx + 1, tty, WEST, ttx, tty, ttd);

        //express conveyors
        testPhase(ttx - 1 + EXPRESS_DIST, tty, EAST, ttx + EXPRESS_DIST, tty + ttd.getDy(), ttd);
        testPhase(ttx + 1 + EXPRESS_DIST, tty, WEST, ttx + EXPRESS_DIST, tty + ttd.getDy(), ttd);
    }

    @Test
    public void verticalGoWest() {
        int ttx = 25; //testing tile x
        int tty = 4; //testing tile y
        Direction ttd = WEST;

        //normal conveyors
        testPhase(ttx, tty + 1, SOUTH, ttx, tty, ttd);
        testPhase(ttx, tty - 1, NORTH, ttx, tty, ttd);

        //express conveyors
        testPhase(ttx, tty + 1 + EXPRESS_DIST, SOUTH, ttx + ttd.getDx(), tty + EXPRESS_DIST, ttd);
        testPhase(ttx, tty - 1 + EXPRESS_DIST, NORTH, ttx + ttd.getDx(), tty + EXPRESS_DIST, ttd);
    }

    @Test
    public void verticalGoEast() {
        int ttx = 27; //testing tile x
        int tty = 4; //testing tile y
        Direction ttd = EAST;

        //normal conveyors
        testPhase(ttx, tty + 1, SOUTH, ttx, tty, ttd);
        testPhase(ttx, tty - 1, NORTH, ttx, tty, ttd);

        //express conveyors
        testPhase(ttx, tty + 1 + EXPRESS_DIST, SOUTH, ttx + ttd.getDx(), tty + EXPRESS_DIST, ttd);
        testPhase(ttx, tty - 1 + EXPRESS_DIST, NORTH, ttx + ttd.getDx(), tty + EXPRESS_DIST, ttd);
    }
}
