package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.round.phase.LaserPhase;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class LaserTest extends TestGraphics {

    private static IPlayer testPlayer;
    private static RoboRally roboRally;
    private static MapHandler map;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "laser_test_map.tmx", 1);
        map = roboRally.getCurrentMap();
    }


    @Before
    public void setUp() {
        testPlayer = roboRally.getPlayerHandler().getPlayers().get(0);
        testPlayer.teleport(0, 0);
        testPlayer.setDirection(Direction.NORTH);
    }


    @Test
    public void standingOnLaserShouldDecreaseHealthByTwo() {
        int healthBefore = testPlayer.getHealth();
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(Movement.MOVE_1);
        new LaserPhase(0).startPhase(map);
        assertEquals(healthBefore - 2, testPlayer.getHealth());
    }

    @Test
    public void walkingOnTreeLasersShouldDecreaseHealthBySix() {
        int healthBefore = testPlayer.getHealth();
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(Movement.MOVE_1);
        testPlayer.setDirection(Direction.NORTH);
        new LaserPhase(0).startPhase(map);
        testPlayer.move(Movement.MOVE_1);
        new LaserPhase(0).startPhase(map);
        testPlayer.move(Movement.MOVE_1);
        new LaserPhase(0).startPhase(map);
        assertEquals(healthBefore - 6, testPlayer.getHealth());
    }

    @Test
    public void standingOnLaserForFivePhasesShouldDecreaseLivesByOne() {
        int livesBefore = testPlayer.getLives();
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(Movement.MOVE_1);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);

        assertEquals(livesBefore-1, testPlayer.getLives());
    }

    @Test
    public void standingOnDoubleLaserShouldDecreaseHealthByFour() {
        int healthBefore = testPlayer.getHealth();
        testPlayer.move(Movement.MOVE_3);
        testPlayer.move(Movement.RIGHT_TURN);
        testPlayer.move(Movement.MOVE_3);
        new LaserPhase(0).startPhase(map);
        assertEquals(healthBefore-4, testPlayer.getHealth());
    }

    @Test
    public void playerLaser() {
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        IPlayer first = players.get(0);
        IPlayer secound = players.get(1);
    }
}
