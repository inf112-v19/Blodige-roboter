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
     * TODO write this
     *
     * @param tile
     * @param prevPos
     * @return
     */
    boolean rotate(@NotNull MovableTile tile, Vector2Int prevPos);
}
