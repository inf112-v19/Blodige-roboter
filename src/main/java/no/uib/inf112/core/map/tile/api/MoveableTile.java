package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface MoveableTile<R> extends SingleDirectionalTile<R> {

    /**
     * Move a tile to the relative position
     *
     * @param dx relative x
     * @param dy relative y
     */
    void move(int dx, int dy);

    void teleport(int x, int y);

    void kill();
}
