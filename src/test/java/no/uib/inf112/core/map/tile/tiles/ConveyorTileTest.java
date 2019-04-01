package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.core.round.phase.ConveyorPhase;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


/**
 * Coordinates for impotant tiles on the test map for the testing of conveyors:
 * - (0,0) : single step north conveyor
 * - (0,1) : single step south conveyor
 * - (1,0) : singel step east conveyor
 * - (1,1) : singel step west conveyor
 * - (0,6) : singel step right turn conveyor (robot should be rotated)
 * - (5,0)-(5,1) : double step north conveyor
 * - (5,3)-(5,2) : double step south conveyor
 * - (7,1)-(6,1) : double step west conveyor
 * - (6,0)-(7,0) : double step east conveyor
 * - (6,2) : double step north conveyor moving onto normal board tile (robot should only move one step)
 * - (5,5) : double step north conveyor moving onto right turn (robot should be rotated)
 */

public class ConveyorTileTest extends TestGraphics {

    private static RoboRally roboRally;
    private static ConveyorPhase testPhase;
    private AbstractPlayer testPlayer;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "conveyor_tile_test_map.tmx", 1);
        testPhase = new ConveyorPhase(0);
    }

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generateOnePlayer();
        testPlayer = roboRally.getPlayerHandler().testPlayer();
    }

    private ConveyorTile getConveyorTile(int x, int y) {
        return (ConveyorTile) roboRally.getCurrentMap().getTile("board", x, y);
    }

    private void conveyorTileAction(int startX, int startY, int endX, int endY) {
        testPlayer.teleport(startX, startY);
        ConveyorTile conveyor = getConveyorTile(startX, startY);
        conveyor.action(testPlayer);
        assertEquals(endX, testPlayer.getX());
        assertEquals(endY, testPlayer.getY());
    }

    @Test
    public void singleStepNorthShouldMoveRobotOneStep() {
        conveyorTileAction(0, 0, 0, 1);
    }

    @Test
    public void singleStepSouthShouldMoveRobotOneStep() {
        conveyorTileAction(0, 1, 0, 0);
    }

    @Test
    public void singleStepWestShouldMoveRobotOneStep() {
        conveyorTileAction(1, 1, 0, 1);
    }

    @Test
    public void singleStepEastShouldMoveRobotOneStep() {
        conveyorTileAction(1, 0, 2, 0);
    }

    @Test
    public void movingFromTurningConveyorShouldTurnRobot() {
        testPlayer.setDirection(Direction.NORTH);
        conveyorTileAction(0, 6, 1, 6);
        assertEquals(Direction.EAST, testPlayer.getDirection());
    }

}
