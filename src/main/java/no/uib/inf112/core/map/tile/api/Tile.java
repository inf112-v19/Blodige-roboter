package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public interface Tile {

    /**
     * @return The x-coordinate of this tile on the map, this should always be within the map
     */
    int getX();

    /**
     * @return The y-coordinate of this tile on the map, this should always be within the map
     */
    int getY();

    /**
     * @return The tile type of this tile
     */
    @NotNull
    TileType getTileType();

    /**
     * @return The Tiled tile to draw
     */
    @NotNull
    TiledMapTile getTile();

    /**
     * @param attribute The attribute to check
     * @return If this tile has the given attribute
     */
    boolean hasAttribute(@Nullable Attribute attribute);

    /**
     * @param superClass The class to check
     * @return if the given class a super class of this class
     */
    boolean hasSuperClass(@NotNull Class<? extends Tile> superClass);
}
