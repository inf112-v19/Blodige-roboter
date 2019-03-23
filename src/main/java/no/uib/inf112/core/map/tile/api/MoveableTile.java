package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface MoveableTile<R> extends Tile<R> {

    /**
     * Move a tile to the relative position
     *
     * @param x relative x
     * @param y relative y
     */
    void move(int x, int y);

    /**
     * Check if this tile can move to a relative posiotpnm
     *
     * @param x relative x
     * @param y relative y
     * @return If the potential new position is valid
     */
    boolean canMove(int x, int y);

    void teleport(int x, int y);

    void kill();
}
