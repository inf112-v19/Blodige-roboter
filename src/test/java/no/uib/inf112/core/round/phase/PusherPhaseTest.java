package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PusherPhaseTest extends TestGraphics {


    private static IPlayer player;
    private static RoboRally roboRally;
    private static PusherPhase pusherPhase;

    @BeforeClass
    public static void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "pusher_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        pusherPhase = new PusherPhase(0);
    }

    private void runPhase(int startX, int startY, int phaseNb) {
        player.teleport(startX, startY);
        roboRally.getCurrentMap().update(0);
        pusherPhase.startPhase(roboRally.getCurrentMap(), phaseNb);
    }

    @Test
    public void oneThreeFivePusherPushesInOddNumberPhase() {
        for (int i = 1; i <= 5; i += 2) {
            runPhase(0, 1, i);
            assertEquals(0, player.getX());
            assertEquals(0, player.getY());
        }
    }

    @Test
    public void oneThreeFivePusherDoesNotPushInEvenNumberPhase() {
        for (int i = 2; i <= 4; i += 2) {
            runPhase(0, 1, i);
            assertEquals(0, player.getX());
            assertEquals(1, player.getY());
        }
    }

    @Test
    public void twoFourPusherPushesInEvenNumberPhase() {
        for (int i = 2; i <= 4; i += 2) {
            runPhase(1, 1, i);
            assertEquals(1, player.getX());
            assertEquals(0, player.getY());
        }
    }

    @Test
    public void twoFourPusherDoesNotPushInPhaseThree() {
        for (int i = 1; i <= 5; i += 2) {
            runPhase(1, 1, i);
            assertEquals(1, player.getX());
            assertEquals(1, player.getY());
        }
    }
}