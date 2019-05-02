package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PusherPhaseTest extends TestGraphics {


    private IPlayer player;
    private RoboRally roboRally;
    private PusherPhase pusherPhase;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "pusher_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        pusherPhase = new PusherPhase(0);
    }

    @Test
    public void oneThreeFivePusherPushesInPhaseOne() {
        player.teleport(0, 1);
        pusherPhase.startPhase(roboRally.getCurrentMap(), 1);
        roboRally.getCurrentMap().update(0);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }
}