package no.uib.inf112.core.map;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ConveryorInteractOnUserTest extends TestGraphics {

    private static RoboRally roboRally;
    private static Player player;
    private static Robot testBot;
    private int roboX, roboY;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.getRoboRally();
        roboRally.changeMap(TEST_MAP_FOLDER + File.separatorChar + "player_wall_test_map.tmx");
    }

    @Before
    public void setup() {
        roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        testBot = player.getRobot();
    }

    @Test
    public void singleStepUpConveyorShouldMoveRobotOneUp() {
        testBot.teleport(0, 0);
        roboRally.mapInteractOnUser.scan(roboRally.getCurrentMap().getEntities());

        assertEquals(0, testBot.getX());
        assertEquals(1, testBot.getY());
    }
    
}
