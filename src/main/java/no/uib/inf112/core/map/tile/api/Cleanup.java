package no.uib.inf112.core.map.tile.api;

import org.jetbrains.annotations.NotNull;

/**
 * Post round action! This is things that needs to be clean up (phase 5 in rulebook) after a round
 *
 * @author Elg
 */
public interface Cleanup<T extends Tile> extends RequirementTile {

    /**
     * @param tile The tile to clean on
     */
    void clean(@NotNull T tile);
}
