package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DockTileTest extends TestGraphics {

    static Map<Integer, Vector2Int> dockPositions;
    static RoboRally roboRally;


    @BeforeClass
    public static void before() {
        dockPositions = new HashMap<>();
        dockPositions.put(1, new Vector2Int(4, 4));
        dockPositions.put(2, new Vector2Int(3, 4));
        dockPositions.put(3, new Vector2Int(2, 4));
        dockPositions.put(4, new Vector2Int(1, 4));
        dockPositions.put(5, new Vector2Int(3, 3));
        dockPositions.put(6, new Vector2Int(3, 2));
        dockPositions.put(7, new Vector2Int(3, 1));
        dockPositions.put(8, new Vector2Int(3, 0));
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_docks_test_map.tmx", 8);
    }


    @Test
    public void testPlayerDocks() {
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        for (IPlayer player : players) {
            Entity entity = (Entity) player;
            assertEquals(dockPositions.get(player.getDock()), new Vector2Int(entity.getX(), entity.getY()));
        }
    }
}
