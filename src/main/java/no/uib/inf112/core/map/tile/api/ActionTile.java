package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Elg
 */
public interface ActionTile<T extends Tile> extends Tile {

    /**
     * Do action :) TODO write javadoc
     *
     * @param tile The tile to do the action on. Guaranteed to have all attributes of {@link #requiredAttributes()}
     */
    void action(@NotNull T tile);

    void playActionSound();

    /**
     * What attributes needs to be present for the action to be called. If null this tile can do action on all tiles
     */
    @Nullable
    List<Attribute> requiredAttributes();

    /**
     * This checks if the {@link #requiredAttributes} are present/null, given tile is not this tile
     *
     * @param tile The tile to check
     * @return if this tile can do action on the given tile
     */
    boolean canDoAction(@NotNull Tile tile);
}
