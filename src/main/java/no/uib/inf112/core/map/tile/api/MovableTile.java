package no.uib.inf112.core.map.tile.api;

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
}
