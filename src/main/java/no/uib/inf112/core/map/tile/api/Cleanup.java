package no.uib.inf112.core.map.tile.api;

import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface Cleanup<T extends Tile> extends RequirementTile {

    void clean(@NotNull T tile);
}
