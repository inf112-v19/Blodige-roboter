package no.uib.inf112.core.player;

import no.uib.inf112.core.util.Vector2Int;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegisterBackupTest {

    //TODO #77 this should be tested way more.
    @Test
    public void settingBackupShouldUpdateBackup() {
        Player testPlayer = new Player(1, 1, Direction.NORTH);
        Vector2Int newBackup = new Vector2Int(0, 0);
        testPlayer.setBackup(newBackup.x, newBackup.y);
        assertEquals(newBackup, testPlayer.getBackup());
    }

}