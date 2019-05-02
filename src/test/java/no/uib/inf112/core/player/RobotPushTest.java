package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RobotPushTest extends TestGraphics {

    private static RoboRally roboRally;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 8);
    }

    @Test
    public void pushingEightRobotsSixTimesShouldMoveEightRobotsSixTimes() {
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        IPlayer player = players.get(0);
        player.setDirection(Direction.EAST);
        player.move(Movement.MOVE_3);
        player.move(Movement.MOVE_3);

        for (int i = 0; i < players.size(); i++) {
            assertEquals(new Vector2Int(i + 6, 0), new Vector2Int(players.get(i).getX(), players.get(i).getY()));
        }
    }

    @Test
    public void pushingEightRobotsAndMovingThemThreeTimesAndPushingThemAgain() {
        MapHandler map = GameGraphics.getRoboRally().getCurrentMap();
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        for (int i = 0; i < 4; i++) {
            IPlayer player = players.get(0);
            player.setDirection(Direction.EAST);
            player.move(Movement.MOVE_3);
            for (int j = 0; j < players.size(); j++) {
                //System.out.println(i);
                assertEquals(new Vector2Int(j + (i * 3) + 3, 0 + i), new Vector2Int(players.get(j).getX(), players.get(j).getY()));
            }
            for (IPlayer playerElem : players) {
                playerElem.setDirection(Direction.NORTH);
                playerElem.move(Movement.MOVE_1);
            }
            map.update(0);
        }
    }

    @Test
    public void pushingRobotsOutOfMapShouldTeleportRobotsToBackup() {
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        IPlayer player = players.get(0);
        player.setDirection(Direction.EAST);
        for (int i = 0; i < 5; i++) {
            player.move(Movement.MOVE_3);
        }

        for (int i = 0; i < players.size(); i++) {
            if (i <= 4) {
                assertEquals(new Vector2Int(i + 15, 0), new Vector2Int(players.get(i).getX(), players.get(i).getY()));
            } else {
                assertEquals(new Vector2Int(i, 0), new Vector2Int(players.get(i).getX(), players.get(i).getY()));
            }
        }
    }

    @Test
    public void pushingRobotAgainstAWallShouldMakeNothingHappen() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_wall_test_map.tmx", 2);
        IPlayer player0 = roboRally.getPlayerHandler().getPlayers().get(0);
        IPlayer player1 = roboRally.getPlayerHandler().getPlayers().get(1);
        player0.teleport(0, 0);
        player0.setDirection(Direction.NORTH);
        player0.update();
        player1.teleport(0, 1);
        player1.update();

        roboRally.getCurrentMap().update(0);

        player0.move(Movement.MOVE_3);

        assertEquals(0, player0.getX());
        assertEquals(0, player0.getY());

        assertEquals(0, player1.getX());
        assertEquals(1, player1.getY());
    }

}
