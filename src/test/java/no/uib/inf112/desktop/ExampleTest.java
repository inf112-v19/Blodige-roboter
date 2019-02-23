package no.uib.inf112.desktop;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Movement;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Vector2Int;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ExampleTest extends TestGraphics{
    private RoboRally roboRally;

    @Before
    public void setUp() {
        Main.HEADLESS = true;
        roboRally = new RoboRally();
        GameGraphics.SetRoboRally(roboRally);
        roboRally.getPlayerHandler().generatePlayers(Main.HEADLESS);
    }

    @Test
    public void MoveShouldMovePlayer() {
        Robot robot = roboRally.getPlayerHandler().mainPlayer().getRobot();
        Vector2Int pos = new Vector2Int(((Robot) robot).getX(), robot.getY());

        robot.move(Movement.MOVE_1);
        assertNotEquals(pos, new Vector2Int(robot.getX(), robot.getY()));
    }
}
