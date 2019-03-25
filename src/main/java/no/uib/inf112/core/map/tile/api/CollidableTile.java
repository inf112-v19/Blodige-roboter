package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;

/**
 * @author Elg
 */
public interface CollidableTile extends MultiDirectionalTile {


    /**
     * @param tile The tile to check will collide with
     * @param dir  The direction the tile will be moving
     * @return true if the tile moves in the current direction it is facing
     */
    boolean willCollide(MoveableTile tile, Direction dir);

}
