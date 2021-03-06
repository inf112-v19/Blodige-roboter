package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class DeathTileTest extends TestGraphics {

    private IPlayer player;

    @Before
    public void setUp() {
        RoboRally roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_hole_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().mainPlayer();
        player.teleport(0, 0);
        player.setDirection(Direction.NORTH);
        player.setBackup(0, 0);
    }


    @Test
    public void movingOntoHoleShouldTeleportRobotToBackup() {
        player.move(Movement.MOVE_1);
        Vector2Int backup = player.getBackup();
        assertEquals(backup.x, player.getX());
        assertEquals(backup.y, player.getY());
    }

    @Test
    public void movingOntoHoleShouldReduceLifeByOne() {
        int livesBefore = player.getLives();
        player.move(Movement.MOVE_1);
        assertEquals(livesBefore - 1, player.getLives());
    }

    @Test
    public void movingOverAHoleShouldTeleportRobotToBackup() {
        //Hole is one step from robot, default tile is two tiles from robot
        int nLives = player.getLives();
        player.move(Movement.MOVE_2);
        Vector2Int backup = player.getBackup();
        assertEquals(backup.x, player.getX());
        assertEquals(backup.y, player.getY());
        assertEquals("Player probably continued to move after death", nLives - 1, player.getLives());
    }
}
