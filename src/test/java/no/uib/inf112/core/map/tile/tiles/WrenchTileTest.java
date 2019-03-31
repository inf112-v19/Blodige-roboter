package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class WrenchTileTest extends TestGraphics {

    private static RoboRally roboRally;
    private AbstractPlayer testPlayer;
    private WrenchTile wTile;
    private Vector2Int pos = new Vector2Int(1, 0); //Position of the tile we are testing

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "hammer_and-or_wrench_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        roboRally.getPlayerHandler().generateOnePlayer();
        testPlayer = roboRally.getPlayerHandler().testPlayer();
        testPlayer.teleport(pos.x, pos.y);
        wTile = (WrenchTile) roboRally.getCurrentMap().getTile("board", pos.x, pos.y);
    }

    @Test
    public void wrenchTileShouldBeAbleToSetBackup() {
        testPlayer.setBackup(0, 0);
        wTile.action(testPlayer);
        assertEquals(pos, testPlayer.getBackup());
    }

}