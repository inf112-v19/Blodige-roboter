package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface MoveableTile extends SingleDirectionalTile {

    /**
     * Move a tile to the relative position
     *
     * @param dx relative x
     * @param dy relative y
     */
    void move(int dx, int dy, int maxTime);

    void teleport(int x, int y);

    void kill();
}
