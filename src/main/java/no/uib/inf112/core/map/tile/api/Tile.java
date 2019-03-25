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

    int getX();

    int getY();

    @NotNull
    TileType getTileType();

    @NotNull
    TiledMapTile getTile();

    boolean hasAttribute(@Nullable Attribute attribute);
}
