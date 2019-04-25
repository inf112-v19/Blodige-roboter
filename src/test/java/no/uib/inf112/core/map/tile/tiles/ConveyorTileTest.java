package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


/**
 * Coordinates for important tiles on the test map for the testing of conveyors:
 * <ul>
 *  <li>(1,0) : single step north CONVEYOR</li>
 *  <li>(1,1) : single step south CONVEYOR</li>
 *  <li>(2,0) : single step east CONVEYOR</li>
 *  <li>(2,1) : single step west CONVEYOR</li>
 *  <li>(1,4) to (1,5) : single step left turn CONVEYOR (robot should be rotated)</li>
 *  <li>(1,6) to (1,7) : single step right turn CONVEYOR (robot should be rotated)</li>
 *  <li>(5,0)-(5,1) : double step north CONVEYOR</li>
 *  <li>(5,3)-(5,2) : double step south CONVEYOR</li>
 *  <li>(7,1)-(6,1) : double step west CONVEYOR</li>
 *  <li>(6,0)-(7,0) : double step east CONVEYOR</li>
 *  <li>(6,2) : double step north CONVEYOR moving onto normal board tile (robot should only move one step)</li>
 *  <li>(5,6) : double step right turn CONVEYOR (robot should be rotated)</li>
 * </ul>
 */
public class ConveyorTileTest extends TestGraphics {


    private RoboRally roboRally;
    private IPlayer testPlayer;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
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
}
