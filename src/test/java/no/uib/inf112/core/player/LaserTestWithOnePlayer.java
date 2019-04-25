package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.round.phase.LaserPhase;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;

public class LaserTestWithOnePlayer extends TestGraphics {

    private static IPlayer testPlayer;
    private static MapHandler map;


    @Before
    public void setUp() {
        RoboRally roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "laser_test_map.tmx", 1);
        map = roboRally.getCurrentMap();
        testPlayer = roboRally.getPlayerHandler().mainPlayer();
        testPlayer.teleport(0, 0);
        testPlayer.setDirection(Direction.NORTH);
        map.update(0);
    }


    @Test
    public void standingOnLaserShouldDecreaseHealthByTwo() {
        int healthBefore = testPlayer.getHealth();
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(Movement.MOVE_1);
        map.update(0);
        new LaserPhase(0).startPhase(map);
        assertEquals(healthBefore - 2, testPlayer.getHealth());
    }

    @Test
    public void walkingOnTreeLasersShouldDecreaseHealthBySix() {
        int healthBefore = testPlayer.getHealth();
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(Movement.MOVE_1);
        testPlayer.setDirection(Direction.NORTH);
        map.update(0);
        new LaserPhase(0).startPhase(map);

        testPlayer.move(Movement.MOVE_1);
        map.update(0);
        new LaserPhase(0).startPhase(map);

        testPlayer.move(Movement.MOVE_1);
        map.update(0);
        new LaserPhase(0).startPhase(map);

        assertEquals(healthBefore - 6, testPlayer.getHealth());
    }

    @Test
    public void standingOnLaserForFivePhasesShouldDecreaseLivesByOne() {
        int livesBefore = testPlayer.getLives();
        testPlayer.teleport(1, 2);
        map.update(0);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);
        new LaserPhase(0).startPhase(map);

        assertEquals(livesBefore - 1, testPlayer.getLives());
    }

    @Test
    public void standingOnDoubleLaserShouldDecreaseHealthByFour() {
        int healthBefore = testPlayer.getHealth();
        testPlayer.teleport(3, 3);
        map.update(0);
        new LaserPhase(0).startPhase(map);
        assertEquals(healthBefore - 4, testPlayer.getHealth());
    }
}
