package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Elg
 */
public interface Tile<R> {

    int getX();

    int getY();

    @NotNull
    TileType getTileType();

    TiledMapTile getTile();


    boolean hasAttribute(@Nullable Attribute attribute);


    /**
     * Do action :) TODO write javadoc
     *
     * @param tile The tile to do the action on. Guaranteed to have all attributes of {@link #requiredAttributes()}
     */
    @Nullable
    R action(@NotNull Tile tile);


    void playActionSound();

    /**
     * What attributes needs to be present for the action to be called
     */
    @Nullable
    List<Attribute> requiredAttributes();

    boolean canDoAction(@NotNull Tile tile);
}
