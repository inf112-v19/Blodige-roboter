package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.ui.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * A tile that will do something to some other tile. If {@link no.uib.inf112.core.map.tile.Attribute#ACTIVE_ONLY_ON_STEP} is set for this tile the {@link #action(Tile)} will be run in all phases
 *
 * @author Elg
 * @see no.uib.inf112.core.round.phase.Phase
 * @see no.uib.inf112.core.map.tile.Attribute#ACTIVE_ONLY_ON_STEP
 */
public interface ActionTile<T extends Tile> extends RequirementTile {

    /**
     * Before calling this method it is expected that {@link #canDoAction(Tile)} (where {@code Tile} is the same as here) returns {@code true}
     *
     * @param tile The tile to do the action on.
     */
    boolean action(@NotNull T tile);

    /**
     * The sound played when the action is executed
     *
     * @return the Sound enum to be the action sound for this tile
     */
    @NotNull
    Sound getActionSound();

}
