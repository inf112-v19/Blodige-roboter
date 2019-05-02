package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PusherTileTest extends TestGraphics {

    private static IPlayer player;
    private static RoboRally roboRally;

    @BeforeClass
    public static void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "pusher_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        player.setDirection(Direction.NORTH);
    }

    private PusherTile getPusherTile(int x, int y) {
        return (PusherTile) roboRally.getCurrentMap().getTile(MapHandler.COLLIDABLES_LAYER_NAME, x, y);
    }

    private void setupAndPush(int x, int y) {
        PusherTile pTile = getPusherTile(x, y);
        player.teleport(x, y);
        pTile.action(player);

    }

    @Test
    public void oneThreeFivePusherSouthPushesRobotSouth() {
        setupAndPush(0, 1);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void twoFourPusherSouthPushesRobotSouth() {
        setupAndPush(1, 1);
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void oneThreeFivePusherNorthPushesRobotNorth() {
        setupAndPush(2, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void twoFourPusherNorthPushesRobotNorth() {
        setupAndPush(3, 0);
        assertEquals(3, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void oneThreeFivePusherEastPushesRobotEast() {
        setupAndPush(4, 0);
        assertEquals(5, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void twoFourPusherEastPushesRobotEast() {
        setupAndPush(4, 1);
        assertEquals(5, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void oneThreeFivePusherWestPushesRobotWest() {
        setupAndPush(6, 0);
        assertEquals(5, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void twoFourPusherWestPushesRobotWest() {
        setupAndPush(6, 1);
        assertEquals(5, player.getX());
        assertEquals(1, player.getY());
    }

}
