package no.uib.inf112.core.map.tile.api;

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
 * @author Elg
 */
public class BackupableTileTest extends TestGraphics {

    private IPlayer player;
    private IPlayer player2;
    private MapHandler map;

    @Before
    public void setUp() {
        RoboRally roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "valid_spawn_test_map.tmx", 2);
        map = roboRally.getCurrentMap();
        player = roboRally.getPlayerHandler().getPlayers().get(0);
        player2 = roboRally.getPlayerHandler().getPlayers().get(1);
    }

    @Test
    public void originalSpawnpointWhenWhenNoEnt() {
        player.setBackup(0, 0);
        player.kill();
        map.update(0);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void validWhenOrgBackupIsOccupied() {
        player2.teleport(4, 0);
        map.update(0);
        assertEquals(player2, map.getTile(MapHandler.ENTITY_LAYER_NAME, 4, 0));

        player.setBackup(4, 0);
        player.kill();
        map.update(0);

        assertEquals(player, map.getTile(MapHandler.ENTITY_LAYER_NAME, 3, 0));
        assertEquals(player2, map.getTile(MapHandler.ENTITY_LAYER_NAME, 4, 0));
    }

    @Test
    public void validWhenOrgBackupIsOccupied2() {
        int x = 0;
        int y = 4;
        player2.teleport(x, y);
        map.update(0);
        assertEquals(player2, map.getTile(MapHandler.ENTITY_LAYER_NAME, x, y));

        player.setBackup(x, y);
        player.kill();
        map.update(0);

        assertEquals(player2, map.getTile(MapHandler.ENTITY_LAYER_NAME, x, y));
        System.out.println("map.getTile(MapHandler.BOARD_LAYER_NAME,0,3) = " + map.getTile(MapHandler.BOARD_LAYER_NAME, 0, 3));
        assertEquals(player, map.getTile(MapHandler.ENTITY_LAYER_NAME, x + 1, y));
    }

    @Test(expected = IllegalStateException.class)
    public void throwsWhenNoValidBackup() {
        int x = 4;
        int y = 4;
        player2.teleport(x, y);
        map.update(0);
        assertEquals(player2, map.getTile(MapHandler.ENTITY_LAYER_NAME, x, y));

        player.setBackup(x, y);
        player.kill();
        map.update(0);
    }
}
