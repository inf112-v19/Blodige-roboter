package no.uib.inf112.core.map.tile.api;

import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface ActionTile<T extends Tile> extends RequirementTile {

    /**
     * Do action :) TODO write javadoc
     *
     * @param tile The tile to do the action on. Guaranteed to have all attributes of {@link #requiredAttributes()}
     */
    void action(@NotNull T tile);

    void playActionSound();

}
