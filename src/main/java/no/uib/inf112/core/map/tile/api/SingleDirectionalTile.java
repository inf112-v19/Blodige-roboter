package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.player.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface SingleDirectionalTile<R> extends Tile<R> {

    @NotNull
    Direction getDirection();

    void setDirection(@NotNull Direction direction);

}
