package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterBackupTest extends TestGraphics {


    private MapHandler map = GameGraphics.getRoboRally().getCurrentMap();

    //TODO #77 this should be tested way more.
    @Test
    public void settingBackupShouldUpdateBackup() {
        Player testPlayer = new NonPlayer(1, 1, Direction.NORTH, map);
        Vector2Int newBackup = new Vector2Int(0, 0);
        testPlayer.setBackup(newBackup.x, newBackup.y);
        assertEquals(newBackup, testPlayer.getBackup());
    }

}
