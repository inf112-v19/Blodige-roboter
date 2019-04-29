package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PusherTileTest extends TestGraphics {

    private IPlayer player;
    private RoboRally roboRally;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "pusher_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        player.setDirection(Direction.NORTH);
    }

    private PusherTile getPusherTile(int x, int y) {
        return (PusherTile) roboRally.getCurrentMap().getTile(MapHandler.COLLIDABLES_LAYER_NAME, x, y);
    }

    @Test
    public void oneThreeFivePusherSouthPushesInPhaseOne() {
        PusherTile pTile = getPusherTile(0, 1);
        player.teleport(0, 1);
        pTile.action(player);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

}
