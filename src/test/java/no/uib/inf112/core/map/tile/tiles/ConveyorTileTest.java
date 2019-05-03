package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.round.phase.ConveyorPhase;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


/**
 * Coordinates for important tiles on the test map for the testing of conveyors:
 * <ul>
 * <li>(1,0) : single step north CONVEYOR</li>
 * <li>(1,1) : single step south CONVEYOR</li>
 * <li>(2,0) : single step east CONVEYOR</li>
 * <li>(2,1) : single step west CONVEYOR</li>
 * <li>(1,4) to (1,5) : single step left turn CONVEYOR (robot should be rotated)</li>
 * <li>(1,6) to (1,7) : single step right turn CONVEYOR (robot should be rotated)</li>
 * <li>(5,0)-(5,1) : double step north CONVEYOR</li>
 * <li>(5,3)-(5,2) : double step south CONVEYOR</li>
 * <li>(7,1)-(6,1) : double step west CONVEYOR</li>
 * <li>(6,0)-(7,0) : double step east CONVEYOR</li>
 * <li>(6,2) : double step north CONVEYOR moving onto normal board tile (robot should only move one step)</li>
 * <li>(5,6) : double step right turn CONVEYOR (robot should be rotated)</li>
 * </ul>
 */
public class ConveyorTileTest extends TestGraphics {


    private RoboRally roboRally;
    private IPlayer testPlayer;
    private ConveyorPhase phase;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
        phase = new ConveyorPhase(0);
    }

    private ConveyorTile getConveyorTile(int x, int y) {
        return (ConveyorTile) roboRally.getCurrentMap().getTile(MapHandler.BOARD_LAYER_NAME, x, y);
    }

    private void conveyorTileAction(int startX, int startY) {
        testPlayer.teleport(startX, startY);
        ConveyorTile conveyor = getConveyorTile(startX, startY);
        conveyor.action(testPlayer);
    }

    private void runPhase() {
        phase.startPhase(roboRally.getCurrentMap());
    }

    private void testPhase(int startX, int startY, Direction startDir, int endX, int endY, Direction endDir) {
        testPlayer.teleport(startX, startY);
        testPlayer.setDirection(startDir);
        roboRally.getCurrentMap().update(0);

        runPhase();

        assertEquals("Different x", endX, testPlayer.getX());
        assertEquals("Different y", endY, testPlayer.getY());
        assertEquals("Different direction", endDir, testPlayer.getDirection());
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

    @Test
    public void expressNorthShouldChangeRobotsYByTwo() {
        testPhase(5, 0, Direction.NORTH, 5, 2, Direction.NORTH);
        testPhase(5, 0, Direction.SOUTH, 5, 2, Direction.SOUTH);
        testPhase(5, 0, Direction.EAST, 5, 2, Direction.EAST);
        testPhase(5, 0, Direction.WEST, 5, 2, Direction.WEST);
    }

    @Test
    public void expressSouthShouldChangeRobotsYByTwo() {
        testPhase(5, 3, Direction.NORTH, 5, 1, Direction.NORTH);
        testPhase(5, 3, Direction.SOUTH, 5, 1, Direction.SOUTH);
        testPhase(5, 3, Direction.EAST, 5, 1, Direction.EAST);
        testPhase(5, 3, Direction.WEST, 5, 1, Direction.WEST);
    }

    @Test
    public void expressEastShouldChangeRobotsXByTwo() {
        testPhase(6, 0, Direction.NORTH, 8, 0, Direction.NORTH);
        testPhase(6, 0, Direction.SOUTH, 8, 0, Direction.SOUTH);
        testPhase(6, 0, Direction.EAST, 8, 0, Direction.EAST);
        testPhase(6, 0, Direction.WEST, 8, 0, Direction.WEST);
    }

    @Test
    public void expressWestShouldChangeRobotsXByTwo() {
        testPhase(7, 1, Direction.NORTH, 5, 1, Direction.NORTH);
        testPhase(7, 1, Direction.SOUTH, 5, 1, Direction.SOUTH);
        testPhase(7, 1, Direction.EAST, 5, 1, Direction.EAST);
        testPhase(7, 1, Direction.WEST, 5, 1, Direction.WEST);
    }

    @Test
    public void expressStopIfNormalTileIsMet() {
        //conveyors facing north
        testPhase(6, 2, Direction.NORTH, 6, 3, Direction.NORTH);
        testPhase(6, 2, Direction.SOUTH, 6, 3, Direction.SOUTH);
        testPhase(6, 2, Direction.EAST, 6, 3, Direction.EAST);
        testPhase(6, 2, Direction.WEST, 6, 3, Direction.WEST);
    }
}
