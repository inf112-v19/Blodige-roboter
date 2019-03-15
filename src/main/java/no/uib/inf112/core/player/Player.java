package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public abstract class Player implements IPlayer {

    public static final int MAX_LIVES = 3;
    public static final int MAX_HEALTH = 10;
    public static final int MAX_PLAYER_CARDS = 5;
    public static final int MAX_DRAW_CARDS = MAX_HEALTH - 1;

    private Robot robot;

    private Vector2Int backup;

    protected int dock;

    protected int flags;
    protected int lives;
    protected boolean poweredDown;
    protected int health;


    /**
     * @param x         Start x position
     * @param y         Start y position
     * @param direction Start direction
     * @throws IllegalArgumentException See {@link Robot#Robot(int, int, Direction)}
     * @throws IllegalStateException    See {@link Robot#Robot(int, int, Direction)}
     */
    public Player(int x, int y, @NotNull Direction direction) {
        backup = new Vector2Int(x, y);

        flags = 0;
        lives = MAX_LIVES;
        health = MAX_HEALTH;
        poweredDown = false;
        robot = new Robot(x, y, direction);
    }

    @Override
    public void damage(int damageAmount) {
        if (damageAmount <= 0) {
            throw new IllegalArgumentException("Cannot do non-positive damage");
        }
        health -= damageAmount;
        if (health <= 0) {
            kill();
        }
    }

    @Override
    public void kill() {
        lives--;
        if (lives == 0) {
            GameGraphics.getRoboRally().getCurrentMap().removeEntity(robot);
            return;
        }
        health = MAX_HEALTH;
        robot.teleport(backup.x, backup.y);
    }

    @Override
    public void heal(int healAmount) {
        if (healAmount <= 0) {
            throw new IllegalArgumentException("Cannot do non-positive damage");
        }
        health = Math.min(MAX_HEALTH, health + healAmount);
    }


    @Override
    public boolean isDestroyed() {
        return lives <= 0;
    }


    @Override
    public void moveRobot(Movement cardAction) {
        if (!getRobot().move(cardAction)) {
            kill();
        }
    }

    @Override
    public int getFlags() {
        return flags;
    }

    @Override
    public boolean canGetFlag(int flagRank) {
        return (flags == flagRank - 1);  // Player has to get the flags in order (1 -> 2 -> ...)
    }

    @Override
    public void registerFlagVisit() {
        flags++;
    }

    @Override
    public int getLives() {
        return lives;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean isPoweredDown() {
        return poweredDown;
    }

    @Override
    public int getDamageTokens() {
        return MAX_HEALTH - health;
    }

    @Override
    public Vector2Int getBackup() {
        return backup;
    }

    @Override
    public void setBackup(int x, int y) {
        MapHandler map = GameGraphics.getRoboRally().getCurrentMap();

        if (!map.isOutsideBoard(x, y)) {
            backup.x = x;
            backup.y = y;
        } else {
            new IllegalArgumentException();
        }
    }

    @Override
    public Robot getRobot() {
        return robot;
    }

    @Override
    public int getDock() {
        return dock;
    }

    @Override
    public void setDock(int dock) {
        this.dock = dock;
    }


    @Override
    public int compareTo(@NotNull IPlayer iPlayer) {
        return Integer.compare(getDock(), iPlayer.getDock());
    }
}
