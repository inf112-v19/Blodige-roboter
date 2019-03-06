package no.uib.inf112.desktop;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.TileType;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSetupTest extends TestGraphics {
    private static RoboRally roboRally;

    @BeforeClass
    public static void before() {
        roboRally = GameGraphics.getRoboRally();
    }

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generatePlayers();
    }

    @Test
    public void MoveShouldMovePlayer() {
        Robot robot = GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().getRobot();
        Vector2Int pos = new Vector2Int(robot.getX(), robot.getY());
        assertTrue(robot.move(Movement.MOVE_1));

        assertNotEquals(pos, new Vector2Int(robot.getX(), robot.getY()));
    }

    @Test
    public void MapShouldGetLoaded() {
        Robot robot = roboRally.getPlayerHandler().mainPlayer().getRobot();
        Vector2Int pos = new Vector2Int(robot.getX(), robot.getY());
        assertEquals(TileType.DEFAULT_TILE, roboRally.getCurrentMap().getBoardLayerTile(pos.x, pos.y));
    }
}
