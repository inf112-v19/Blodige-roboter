package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface CollidableTile<R> extends MultiDirectionalTile<R> {


    /**
     * @param tile The tile to check will collide with
     * @return true if the tile moves in the current direction it is facing
     */
    boolean willCollide(MoveableTile tile);

}
