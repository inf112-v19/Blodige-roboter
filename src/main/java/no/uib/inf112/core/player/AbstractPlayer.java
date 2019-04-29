package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;


/**
 * @author Elg
 */
public abstract class AbstractPlayer extends Robot implements IPlayer {

    private Vector2Int backup;

    private int dock;
    private int lives;
    private int health;

    protected int flags;
    protected String name;

    private boolean poweredDown;
    private boolean willPowerDown;


    /**
     * @param x         Start x position
     * @param y         Start y position
     * @param direction Start direction
     * @param map       Current map
     */
    public AbstractPlayer(int x, int y, @NotNull Direction direction, @NotNull MapHandler map, @NotNull ComparableTuple<String, Color> color) {
        super(new Vector2Int(x, y), direction, color);
        if (map.isOutsideBoard(x, y)) {
            throw new IllegalArgumentException("Cant set backup outside of the map");
        }
        backup = new Vector2Int(x, y);

        name = color.key;
        flags = 0;
        lives = MAX_LIVES;
        health = MAX_HEALTH;
        poweredDown = false;
        willPowerDown = false;
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
            GameGraphics.getRoboRally().getCurrentMap().removeEntity(this);
            return;
        }
        health = MAX_HEALTH;
        teleport(backup.x, backup.y);
    }

    @Override
    public void destroy() {
        lives = 0;
        GameGraphics.getRoboRally().getCurrentMap().removeEntity(this);
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
    public int getFlags() {
        return flags;
    }

    @Override
    public boolean canGetFlag(int flagNr) {
        return (flags == flagNr - 1);  // Player has to get the flags in order (1 -> 2 -> ...)
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPoweredDown() {
        return poweredDown;
    }

    @Override
    public boolean willPowerDown() {
        return willPowerDown;
    }

    public void setPoweredDown(boolean poweredDown) {
        if (poweredDown) {
            Sound.ROBOT_SHUTDOWN.play();
        }
        this.poweredDown = poweredDown;
    }

    public void setWillPowerDown(boolean willPowerDown) {
        this.willPowerDown = willPowerDown;
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

        if (map.isOutsideBoard(x, y)) {
            throw new IllegalArgumentException("Cant set backup outside of the map");
        } else {
            backup.x = x;
            backup.y = y;
        }
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
    public void clean(@NotNull Tile tile) {
        if (willPowerDown) {
            poweredDown = true;
            heal();
        }
    }

    @Override
    public int compareTo(@NotNull IPlayer other) {
        return getDock() - other.getDock();
    }
}
