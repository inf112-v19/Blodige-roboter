package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * A tile that has multiple directions, like a wall
 *
 * @author Elg
 */
public interface MultiDirectionalTile extends Tile {

    /**
     * @return All directions this tile has
     */
    @NotNull
    Set<Direction> getDirections();
}
