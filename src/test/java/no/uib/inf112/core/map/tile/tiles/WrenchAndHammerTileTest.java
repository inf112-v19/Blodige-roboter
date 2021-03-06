package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class WrenchAndHammerTileTest extends TestGraphics {

    protected static RoboRally roboRally;
    protected IPlayer testPlayer;
    private WrenchAndHammerTile wahTile;
    private Vector2Int pos = new Vector2Int(0, 0); //Position of the tile we are testing

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "hammer_and-or_wrench_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
        testPlayer.teleport(pos.x, pos.y);
        wahTile = (WrenchAndHammerTile) roboRally.getCurrentMap().getTile("board", pos.x, pos.y);
    }


    @Test
    public void wahTileShouldBeAbleToChangeBackup() {
        testPlayer.setBackup(1, 0); //Changing backup to see that wah-tile can set backup
        wahTile.action(testPlayer);
        assertEquals(pos, testPlayer.getBackup());
    }

    @Test
    public void wahTileShouldBeAbleToHeal() {
        int beforeHealth = testPlayer.getHealth();
        testPlayer.damage(1);
        wahTile.clean(testPlayer);
        assertEquals(beforeHealth, testPlayer.getHealth());
    }

    @Test
    public void wahTileShouldOnlyHealOneHP() {
        testPlayer.damage(2);
        int damageHealt = testPlayer.getHealth();
        wahTile.clean(testPlayer);
        assertEquals(damageHealt + 1, testPlayer.getHealth());
    }
}
