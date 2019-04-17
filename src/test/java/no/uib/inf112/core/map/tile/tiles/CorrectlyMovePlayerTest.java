package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.round.phase.ConveyorPhase;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author Elg
 */
public class CorrectlyMovePlayerTest extends TestGraphics {

    private static Robot robot;
    private static MapHandler map;
    private static ConveyorPhase phase;

    @Before
    public void setUpPlr() {

        GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 1);
        map = GameGraphics.getRoboRally().getCurrentMap();

        phase = new ConveyorPhase(0);
        robot = (Robot) GameGraphics.getRoboRally().getCurrentMap().getTile(MapHandler.ENTITY_LAYER_NAME, 0, 0);
        map.update(0);
        assertFalse(robot.shouldUpdate());
    }

    @Test
    public void mapHandlerHaveCorrectPlayer() {
        //noinspection SuspiciousMethodCalls
        assertTrue(GameGraphics.getRoboRally().getPlayerHandler().getPlayers().contains(robot));
        assertEquals(GameGraphics.getRoboRally().getPlayerHandler().testPlayer(), robot);

        phase.startPhase(map);
        map.update(0);

        //noinspection SuspiciousMethodCalls
        assertTrue(GameGraphics.getRoboRally().getPlayerHandler().getPlayers().contains(robot));
        assertEquals(GameGraphics.getRoboRally().getPlayerHandler().testPlayer(), robot);
    }

    @Test
    public void horizontalConveyorMovesRight() {
        //start position
        final int x = 0;
        final int y = 0;

        //length of CONVEYOR track
        final int length = 5;

        robot.teleport(x, y);

        Tile tile = GameGraphics.getRoboRally().getCurrentMap().getTile(MapHandler.BOARD_LAYER_NAME, x, y);
        assertTrue("Failed to find any tracks at " + x + ", " + y, tile instanceof ConveyorTile);

        map.update(0);

        for (int i = 1; i < length; i++) {
            phase.startPhase(map);
            map.update(0);
            assertFalse(robot.shouldUpdate());

            tile = GameGraphics.getRoboRally().getCurrentMap().getTile(MapHandler.ENTITY_LAYER_NAME, x + i, y);
            assertTrue("Failed to find robot at " + x + ", " + y, tile instanceof Robot);
            assertEquals(robot, tile);

            assertEquals(x + i, robot.getX());
            assertEquals(y, robot.getY());
        }

        //should be at the end of the conveyors
        phase.startPhase(map);
        assertEquals(x + length, robot.getX());
        assertEquals(y, robot.getY());
    }
}
