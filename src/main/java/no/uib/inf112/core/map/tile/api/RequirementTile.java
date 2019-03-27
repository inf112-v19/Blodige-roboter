package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Elg
 */
public interface RequirementTile extends Tile {

    /**
     * What attributes needs to be present for the action to be called. If null this tile can do action on all tiles
     */
    @Nullable
    List<Attribute> requiredAttributes();

    /**
     * If null this tile can do action on any other tiles
     *
     * @return
     */
    @Nullable
    List<Class<? extends Tile>> requiredSuperClasses();

    /**
     * This checks if the {@link #requiredAttributes} are present/null, given tile is not this tile
     *
     * @param tile The tile to check
     * @return if this tile can do action on the given tile
     */
    boolean canDoAction(@NotNull Tile tile);
}
