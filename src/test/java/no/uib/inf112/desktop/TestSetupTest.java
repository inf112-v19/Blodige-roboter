package no.uib.inf112.desktop;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.Before;
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

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generatePlayers();
    }

    @Test
    public void MoveShouldMovePlayer() {
        Robot robot = GameGraphics.getRoboRally().getPlayerHandler().testPlayer();
        Vector2Int pos = new Vector2Int(robot.getX(), robot.getY());
        robot.move(Movement.MOVE_1, 0);

        assertNotEquals(pos, new Vector2Int(robot.getX(), robot.getY()));
    }

    @Test
    public void MapShouldGetLoaded() {
        Robot robot = roboRally.getPlayerHandler().testPlayer();
        Vector2Int pos = new Vector2Int(robot.getX(), robot.getY());
        Tile tile = roboRally.getCurrentMap().getTile(MapHandler.BOARD_LAYER_NAME, pos.x, pos.y);
        assertNotNull(tile);
        assertEquals(TileType.DEFAULT, tile.getTileType());
    }
}
