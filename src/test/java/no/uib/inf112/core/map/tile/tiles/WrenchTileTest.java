package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.util.Vector2Int;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WrenchTileTest extends WrenchAndHammerTileTest {

    private Vector2Int pos = new Vector2Int(1, 0); //Position of the tile we are testing

    @Test
    public void wrenchTileShouldBeAbleToSetBackup() {
        testPlayer.teleport(pos.x, pos.y);
        WrenchTile wTile = (WrenchTile) roboRally.getCurrentMap().getTile("board", pos.x, pos.y);

        testPlayer.setBackup(0, 0);
        wTile.action(testPlayer);
        assertEquals(pos, testPlayer.getBackup());
    }
}