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
    private static MapHandler map;

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 2);
        map = roboRally.getCurrentMap();
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
        MapHandler map1 = map;
        List<IPlayer> players = roboRally.getPlayerHandler().getPlayers();
        for (int i = 0; i < 3; i++) {
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

}
