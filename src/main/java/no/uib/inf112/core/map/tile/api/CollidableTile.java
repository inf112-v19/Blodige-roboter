package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;

/**
 * A tiles that might collide with {@link MovableTile} tiles
 *
 * @author Elg
 */
public interface CollidableTile extends MultiDirectionalTile {

    /**
     * @param tile The tile to check will collide with
     * @param dir  The direction the tile will be moving
     * @return {@code true} if the given tile, {@code tile}, will collide with this tile if it moves in given direction, {@code dir}
     */
    boolean willCollide(Tile tile, Direction dir);

}
