package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class PlayerPhaseTest extends TestGraphics {

    private static RoboRally roboRally;
    private static IPlayer player1;
    private static IPlayer player0;

    @BeforeClass
    public static void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 2);
        player0 = roboRally.getPlayerHandler().mainPlayer();
        player1 = roboRally.getPlayerHandler().getPlayers().get(1);
    }

    @Test
    public void runningPhaseShouldRotateBothPlayers5Times() {
        Vector2Int player0Pos = new Vector2Int(player0.getX(), player0.getY());
        Vector2Int player1Pos = new Vector2Int(player1.getX(), player1.getY());
        Direction player1Dir = player1.getDirection();
        Direction player0Dir = player0.getDirection();

        PlayerPhase playerPhase = new PlayerPhase(0);
        playerPhase.startPhase(roboRally.getCurrentMap());

        assertEquals(player0Dir.turnRight(), player0.getDirection());
        assertEquals(player0Pos, new Vector2Int(player0.getX(), player0.getY()));
        assertEquals(player1Dir.turnRight(), player1.getDirection());
        assertEquals(player1Pos, new Vector2Int(player1.getX(), player1.getY()));
    }

}
