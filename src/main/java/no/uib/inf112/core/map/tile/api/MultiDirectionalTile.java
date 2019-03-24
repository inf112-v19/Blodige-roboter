package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Elg
 */
public interface MultiDirectionalTile<R> extends Tile<R> {

    @NotNull
    Set<Direction> getDirections();
}
