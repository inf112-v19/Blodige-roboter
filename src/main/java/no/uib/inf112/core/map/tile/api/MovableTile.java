package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * A tile that can be moved
 *
 * @author Elg
 */
public interface MovableTile extends SingleDirectionalTile {

    /**
     * Move a tile to the relative position given by the parameters
     *
     * @param dx Relative x-coordinate to this
     * @param dy Relative y-coordinate to this
     */
    void move(int dx, int dy, int maxTime);

    /**
     * The tile does a step in a given direction. The Absolute value sum for change in position for both axis will be one.
     *
     * @param dir direction to move in
     * @return true if tile was able to move
     */
    boolean move(Direction dir);

    /**
     * Set the coordinates of this class to the given parameters
     *
     * @param x The new x-coordinate
     * @param y The new y-coordinate
     */
    void teleport(int x, int y);

    /**
     * Set the coordinates of this class to the given parameters
     *
     * @param v The location to teleport to
     */
    default void teleport(@NotNull Vector2Int v) {
        teleport(v.x, v.y);
    }

    /**
     * If it can move it can die!
     */
    void kill();

    /**
     * This tile should not finish its current movement
     */
    void stopMoving();
}
