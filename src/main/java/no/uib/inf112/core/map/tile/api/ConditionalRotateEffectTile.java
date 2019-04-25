package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * A tile that can rotate another tile
 *
 * @author Kristian
 */
public interface ConditionalRotateEffectTile extends Tile {

    /**
     * Rotates the given MovableTile
     *
     * @param tile    tile to rotate
     * @param prevPos receives a vector of the previous position so that the implementation can take this into consideration
     * @return true if the tile is able to rotate the provided tile (also true if the tile is set to the already set direction)
     */
    boolean rotate(@NotNull MovableTile tile, Vector2Int prevPos);
}
