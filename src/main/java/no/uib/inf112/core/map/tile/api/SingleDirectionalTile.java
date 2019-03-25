package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface SingleDirectionalTile extends Tile {

    @NotNull
    Direction getDirection();

    void setDirection(@NotNull Direction direction);

}
