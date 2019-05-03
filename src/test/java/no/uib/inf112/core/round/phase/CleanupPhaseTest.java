package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class CleanupPhaseTest extends TestGraphics {

    private static RoboRally roboRally;
    private static IPlayer player;

    @BeforeClass
    public static void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "round_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().mainPlayer();
    }

    @Test
    public void wrenchAndHammerTileShouldCleanByIncreasingHealthOfPlayer() {
        assertEquals(AbstractPlayer.MAX_HEALTH, player.getHealth());
        player.damage(1);
        assertEquals(AbstractPlayer.MAX_HEALTH - 1, player.getHealth());
        player.teleport(9, 0);
        player.update();
        roboRally.getCurrentMap().update(0);

        new CleanupPhase(TileType.HAMMER_AND_WRENCH).startPhase(roboRally.getCurrentMap());
        assertEquals(AbstractPlayer.MAX_HEALTH, player.getHealth());
    }

    @Test
    public void poweredDownRobotShouldFullyHealInCleanupPhase() {
        assertEquals(AbstractPlayer.MAX_HEALTH, player.getHealth());
        player.damage(AbstractPlayer.MAX_HEALTH - 1);
        assertEquals(1, player.getHealth());
        System.out.println(roboRally.getCurrentMap().getAllTiles(0, 0));
        player.setWillPowerDown(true);

        new CleanupPhase(TileType.ROBOT).startPhase(roboRally.getCurrentMap());
        assertEquals(AbstractPlayer.MAX_HEALTH, player.getHealth());
    }

}
