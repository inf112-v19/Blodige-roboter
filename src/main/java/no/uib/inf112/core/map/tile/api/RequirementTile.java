package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A tile that has some requirements for other methods to be run.
 *
 * @author Elg
 * @see no.uib.inf112.core.map.tile.api.ActionTile
 * @see no.uib.inf112.core.map.tile.api.Cleanup
 */
public interface RequirementTile extends Tile {

    /**
     * What attributes needs to be present for the action to be called.
     *
     * @return The list of attributes required to be present for this class to do something, if
     * {@code null} this tile can do action on any other tiles
     */
    @Nullable
    List<Attribute> requiredAttributes();

    /**
     * @return A list of classes required to be super classes, if {@code null} this tile can do action on any other tiles
     */
    @Nullable
    List<Class<? extends Tile>> requiredSuperClasses();

    /**
     * This checks if the {@link #requiredAttributes} are present/null, given tile is not this tile
     *
     * @param tile The tile to check
     * @return If this tile can do action on the given tile
     */
    boolean canDoAction(@NotNull Tile tile);
}
