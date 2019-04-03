package no.uib.inf112.desktop;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TestSetupTest extends TestGraphics {
    private static RoboRally roboRally;

    @BeforeClass
    public static void before() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 8);
    }

    @Test
    public void moveShouldMovePlayer() {
        IPlayer robot = GameGraphics.getRoboRally().getPlayerHandler().testPlayer();
        Vector2Int pos = new Vector2Int(robot.getX(), robot.getY());
        robot.move(Movement.MOVE_1, 0);

        assertNotEquals(pos, new Vector2Int(robot.getX(), robot.getY()));
    }

    @Test
    public void mapShouldGetLoaded() {
        IPlayer robot = roboRally.getPlayerHandler().testPlayer();
        Vector2Int pos = new Vector2Int(robot.getX(), robot.getY());
        Tile tile = roboRally.getCurrentMap().getTile(MapHandler.BOARD_LAYER_NAME, pos.x, pos.y);
        assertNotNull("Could not find any tile at " + pos, tile);
        assertEquals(TileType.CONVEYOR, tile.getTileType());
    }
}
