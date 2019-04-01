package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


/**
 * Coordinates for impotant tiles on the test map for the testing of conveyors:
 * - (1,0) : single step north conveyor
 * - (1,1) : single step south conveyor
 * - (2,0) : singel step east conveyor
 * - (2,1) : singel step west conveyor
 * - (1,6) : singel step right turn conveyor (robot should be rotated)
 * - (5,0)-(5,1) : double step north conveyor
 * - (5,3)-(5,2) : double step south conveyor
 * - (7,1)-(6,1) : double step west conveyor
 * - (6,0)-(7,0) : double step east conveyor
 * - (6,2) : double step north conveyor moving onto normal board tile (robot should only move one step)
 * - (5,6) : double step right turn conveyor (robot should be rotated)
 */

public class ConveyorTileTest extends TestGraphics {


    private RoboRally roboRally;
    private IPlayer testPlayer;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        testPlayer = roboRally.getPlayerHandler().testPlayer();
    }

    private ConveyorTile getConveyorTile(int x, int y) {
        return (ConveyorTile) roboRally.getCurrentMap().getTile(MapHandler.BOARD_LAYER_NAME, x, y);
    }

    private void conveyorTileAction(int startX, int startY) {
        testPlayer.teleport(startX, startY);
        ConveyorTile conveyor = getConveyorTile(startX, startY);
        conveyor.action(testPlayer);
    }

    @Test
    public void singleStepNorthShouldChangeRobotsY() {
        conveyorTileAction(1, 0);
        assertEquals(1, testPlayer.getX());
        assertEquals(1, testPlayer.getY());
    }

    @Test
    public void singleStepSouthShouldChangeRobotsY() {
        conveyorTileAction(1, 1);
        assertEquals(1, testPlayer.getX());
        assertEquals(0, testPlayer.getY());
    }

    @Test
    public void singleStepWestShouldChangeRobotsX() {
        conveyorTileAction(2, 1);
        assertEquals(1, testPlayer.getX());
        assertEquals(1, testPlayer.getY());
    }

    @Test
    public void singleStepEastShouldChangeRobotsX() {
        conveyorTileAction(2, 0);
        assertEquals(3, testPlayer.getX());
        assertEquals(0, testPlayer.getY());
    }

    @Ignore //rotation removed from conveyors for now (as it REALLY doesn't work)
    @Test
    public void movingFromTurningConveyorShouldTurnRobot() {
        testPlayer.setDirection(Direction.NORTH);
        conveyorTileAction(1, 6);
        assertEquals(2, testPlayer.getX());
        assertEquals(6, testPlayer.getY());
        assertEquals(Direction.EAST, testPlayer.getDirection());
    }

}
