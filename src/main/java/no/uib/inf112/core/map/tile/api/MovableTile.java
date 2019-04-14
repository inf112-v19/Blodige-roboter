package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;

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
     * The tile does a move in a given direction
     *
     * @param dir direction to move in
     * @return true if tile was able to move
     */
    boolean moveOneStep(Direction dir);

    /**
     * Set the coordinates of this class to the given parameters
     *
     * @param x The new x-coordinate
     * @param y The new y-coordinate
     */
    void teleport(int x, int y);

    /**
     * If it can move it can die!
     */
    void kill();

    /**
     * This tile should not finish its current movement
     */
    void stopMoving();
}
