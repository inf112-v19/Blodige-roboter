package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.round.DefaultGameRule;
import no.uib.inf112.core.round.Round;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultGameRuleTest extends TestGraphics {

    private RoboRally roboRally;
    private IPlayer player;
    private static Round defaultGameRule;

    @BeforeClass
    public static void beforeClass() {
    }

    @Before
    public void setUp() {
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "round_test_map.tmx", 1);
        player = roboRally.getPlayerHandler().testPlayer();
        player.teleport(0, 0);
        player.setDirection(Direction.NORTH);
        player.setBackup(0, 0);

        defaultGameRule = DefaultGameRule.generate(0);
    }

    @Test
    public void assertSetupWorks() {
        assertStatus(0, 0, Direction.NORTH, 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void onlyRotateAfterFullRound() {
        defaultGameRule.startRound();
        assertStatus(0, 0, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void movedByConveyorAndRotatedAfterFullRound() {
        player.teleport(1, 0);
        defaultGameRule.startRound();
        assertStatus(1, 1, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void movedByConveyorRotateAndRegisteredFlagAfterFullRound() {
        player.teleport(2, 0);
        defaultGameRule.startRound();
        assertStatus(2, 1, Direction.NORTH.turnRight(), 2, 1, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 1, false);
    }

    @Test
    public void movedByDoubleConveyourRotatedAndRegisteredFlagAfterFullRound() {
        player.teleport(3, 0);
        defaultGameRule.startRound();
        assertStatus(3, 2, Direction.NORTH.turnRight(), 3, 2, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 1, false);
    }

    @Test
    public void movedByDoubleConveyourRotatedAfterFullRound() {
        player.teleport(4, 0);
        roboRally.getCurrentMap().update(0);
        defaultGameRule.startRound();
        assertStatus(4, 10, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void singleConveyorMovesOnlyFiveTimesAfterFullRound() {
        player.teleport(5, 0);
        defaultGameRule.startRound();
        assertStatus(5, 5, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void singleConveyorMoveThenShotByLaserAfterFullRound() {
        player.teleport(6, 0);
        defaultGameRule.startRound();
        assertStatus(6, 1, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH - 5, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void OneDoubleConveyorMovesOnlyOnceAfterFullRound() {
        player.teleport(7, 0);
        defaultGameRule.startRound();
        assertStatus(7, 1, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void twoDoubleConeyorsMoveAndBackupIsUpdatedAfterFullRound() {
        player.teleport(8, 0);
        defaultGameRule.startRound();
        assertStatus(8, 2, Direction.NORTH.turnRight(), 8, 2, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void hammerAndWrenchShouldUpdateBackupAndHeal1AfterFullRound() {
        player.teleport(9, 0);
        player.damage(2);
        defaultGameRule.startRound();
        assertStatus(9, 0, Direction.NORTH.turnRight(), 9, 0, AbstractPlayer.MAX_HEALTH - 1, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void rotationRightGearAndPlayerShoulRotateRightTwiceAfterFullRound() {
        player.teleport(10, 0);
        defaultGameRule.startRound();
        assertStatus(10, 0, Direction.NORTH.inverse(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void rotationLeftGearAndPlayerShouldCancelOutRotationAfterFullRound() {
        player.teleport(11, 0);
        defaultGameRule.startRound();
        assertStatus(11, 0, Direction.NORTH, 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void conveyorTowardsWallShouldDoNothingPlayerStillRotatesAfterFullRound() {
        player.teleport(12, 0);
        defaultGameRule.startRound();
        assertStatus(12, 0, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, false);
    }

    @Test
    public void flagShouldRegisterAndPlayerRotateRightAfterFullRound() {
        player.teleport(13, 0);
        defaultGameRule.startRound();
        assertStatus(13, 0, Direction.NORTH.turnRight(), 13, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 1, false);
    }

    @Test
    public void conveyorShouldPushRobotOfMapAndNoFlagRegistrationAfterFullRound() {
        player.teleport(14, 0);
        defaultGameRule.startRound();
        assertStatus(0, 0, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES - 1, 0, false);
    }

    @Test
    public void doubleConveyorShouldPushRobotOntoWholeRobotShouldBeAtBackupAfterFullRound() {
        player.teleport(15, 0);
        roboRally.getCurrentMap().update(0);
        defaultGameRule.startRound();
        assertStatus(0, 0, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES - 1, 0, false);
    }

    @Test
    public void powerDownShouldFullyHealRobotAndDoNoMovement() {
        player.teleport(0, 0);
        player.damage(AbstractPlayer.MAX_HEALTH - 1);
        player.setWillPowerDown(true);
        assertTrue(player.willPowerDown());
        assertStatus(0, 0, Direction.NORTH, 0, 0, 1, AbstractPlayer.MAX_LIVES, 0, false);
        defaultGameRule.startRound();
        assertStatus(0, 0, Direction.NORTH.turnRight(), 0, 0, AbstractPlayer.MAX_HEALTH, AbstractPlayer.MAX_LIVES, 0, true);
    }


    private void assertStatus(int posX, int posY, Direction direction, int backupX, int backupY, int health, int lives, int flags, boolean poweredDown) {
        assertEquals(posX, player.getX());
        assertEquals(posY, player.getY());
        assertEquals(direction, player.getDirection());
        assertEquals(new Vector2Int(backupX, backupY), player.getBackup());
        assertEquals(health, player.getHealth());
        assertEquals(lives, player.getLives());
        assertEquals(flags, player.getFlags());
        assertEquals(poweredDown, player.isPoweredDown());
    }

    @Test
    public void laserShouldKillRobotAndNotRegisterFlag() {
        //Need different setup for this test (two players)
        roboRally = GameGraphics.createRoboRally(TEST_MAP_FOLDER + File.separatorChar + "round_test_map.tmx", 2);
        IPlayer player0 = roboRally.getPlayerHandler().getPlayers().get(0);
        IPlayer player1 = roboRally.getPlayerHandler().getPlayers().get(1);
        player0.teleport(0, 7);
        player1.teleport(13, 7);
        player0.setDirection(Direction.NORTH);
        player1.setDirection(Direction.NORTH);
        player1.setBackup(0, 0);

        player1.damage(AbstractPlayer.MAX_HEALTH - 1);

        DefaultGameRule.generate(0).startRound();

        assertEquals(0, player1.getX());
        assertEquals(0, player1.getY());
        assertEquals(AbstractPlayer.MAX_HEALTH, player1.getHealth());
        assertEquals(AbstractPlayer.MAX_LIVES - 1, player1.getLives());
        assertEquals(0, player1.getFlags());
    }

}
