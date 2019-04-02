package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RobotTest extends TestGraphics {

    private int roboX;
    private int roboY;
    private IPlayer player;

    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;
    private static RoboRally roboRally;

    @BeforeClass
    public static void beforeClass() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "player_test_map.tmx", 1);
    }

    @Before
    public void setUp() {
        //roboRally.getPlayerHandler().generateOnePlayer();
        player = roboRally.getPlayerHandler().testPlayer();
        player.teleport(HEIGHT / 2, WIDTH / 2);
        player.setDirection(Direction.NORTH);

        roboX = player.getX();
        roboY = player.getY();
    }

    @Test
    public void movingOneFacingNorthShouldNotChangeX() {
        player.move(Movement.MOVE_1);
        assertEquals(roboX, player.getX());
    }

    @Test
    public void movingOneFacingNorthShouldIncrementY() {
        player.move(Movement.MOVE_1);
        assertEquals(roboY + 1, player.getY());
    }

    @Test
    public void movingThreeFacingNorthShouldIncreaseYWithThree() {
        player.move(Movement.MOVE_3);
        assertEquals(roboY + 3, player.getY());
    }

    @Test
    public void movingTwoFacingEastShouldNotChangeY() {
        player.setDirection(Direction.EAST);
        player.move(Movement.MOVE_2);
        assertEquals(roboY, player.getY());
    }

    @Test
    public void movingTwoFacingEastShouldIncreaseXWithTwo() {
        player.setDirection(Direction.EAST);
        player.move(Movement.MOVE_2);
        assertEquals(roboX + 2, player.getX());
    }

    @Test
    public void backingUpWhileFacingNorthShouldDecrementY() {
        player.move(Movement.BACK_UP);
        assertEquals(roboY - 1, player.getY());
    }

    @Test
    public void backingUpShouldNotAffectDirectionOfRobot() {
        Direction facing = player.getDirection();
        player.move(Movement.BACK_UP);
        assertEquals(facing, player.getDirection());
    }

    @Test
    public void movingForwardShouldNotAffectDirectionOfRobot() {
        Direction facing = player.getDirection();
        player.move(Movement.MOVE_2);
        player.move(Movement.MOVE_1);
        player.move(Movement.MOVE_3);
        assertEquals(facing, player.getDirection());
    }

    @Test
    public void turningLeftWhileFacingNorthShouldResultInWest() {
        player.setDirection(Direction.NORTH); //Just in case setup is changed
        player.move(Movement.LEFT_TURN);
        assertEquals(Direction.WEST, player.getDirection());
    }

    @Test
    public void turningLeftShouldNotChangeXOrY() {
        player.move(Movement.LEFT_TURN);
        assertEquals(roboX, player.getX());
        assertEquals(roboY, player.getY());
    }

    @Test
    public void turningRightTwiceShouldHaveTheSameResultAsAUTurn() {
        Robot player2 = new RobotImpl(new Vector2Int(2, 2), player.getDirection(), Color.BLUE);
        player.move(Movement.RIGHT_TURN);
        player.move(Movement.RIGHT_TURN);
        player.move(Movement.U_TURN);
        assertEquals(player.getDirection(), player2.getDirection());
    }

    @Test
    public void movingRobotInASquareShouldResultInRobotBeingBackAtStartingPosition() {
        Direction facing = player.getDirection();
        for (int i = 0; i < 100; i++) {
            if (i % 4 == 0) {
                assertEquals(roboX, player.getX());
                assertEquals(roboY, player.getY());
                assertEquals(facing, player.getDirection());
            } else {
                assertFalse(roboX == player.getX() && roboY == player.getY());
                assertNotEquals(facing, player.getDirection());
            }
            player.move(Movement.MOVE_2);
            player.move(Movement.LEFT_TURN);
        }
    }

//    @Test
//    public void getTileType() {
//        for (Direction dir : Direction.values()) {
//            player.setDirection(dir);
//            assertNotNull(player.getTileType());
//
//            assertEquals(TileType.ROBOT, player.getTileType());
//
//            String[] name = player.get().name().split("_");
//            assertEquals(dir.name(), name[name.length - 1]);
//        }
//    }

    @Test
    public void movingOutOfBoundTeleportToBackup() {
        player.setBackup(player.getX(), player.getY());
        player.teleport(0, 0);
        player.setDirection(Direction.SOUTH);
        player.move(Movement.MOVE_1);

        assertEquals(roboX, player.getX());
        assertEquals(roboY, player.getY());
    }

    @Test
    public void movingOutOfBoundReduceLifeByOne() {

        player.teleport(0, 0);
        player.setDirection(Direction.SOUTH);
        player.move(Movement.MOVE_1);

        assertTrue(player.shouldUpdate());

        assertEquals(AbstractPlayer.MAX_HEALTH, player.getHealth());
        assertEquals(AbstractPlayer.MAX_LIVES - 1, player.getLives());

    }

    private class RobotImpl extends Robot {

        public RobotImpl(Vector2Int pos, Direction direction, Color color) {
            super(pos, direction, color);
        }

        @Override
        public void heal(int amount) {
            player.heal(amount);
        }

        @Override
        public void kill() {
            player.kill();
        }

        @NotNull
        @Override
        public Vector2Int getBackup() {
            return new Vector2Int(0, 0);
        }

        @Override
        public void damage(int damageAmount) {
            player.damage(damageAmount);
        }
    }

}
