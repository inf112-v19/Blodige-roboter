package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract tile that handles the basics of a tile. In general all tiles should extend this class, as they have to
 * have the same constructor signature as this class.
 *
 * @author Elg
 */
public abstract class AbstractTile implements Tile {

    private final Vector2Int pos;
    private final TileGraphic tg;
    private final TileType tt;

    public AbstractTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {

        this.pos = pos;
        this.tg = tg;
        this.tt = tg.getTileType();
    }

    @Override
    public int getX() {
        return pos.x;
    }

    @Override
    public int getY() {
        return pos.y;
    }

    @NotNull
    @Override
    public TileType getTileType() {
        return tt;
    }

    @NotNull
    @Override
    public TiledMapTile getTile() {
        return tg.getTile();
    }

    @Override
    public boolean hasAttribute(@Nullable Attribute attribute) {
        return tg.getAttributes().contains(attribute);
    }

    @Override
    public boolean hasSuperClass(@NotNull Class<? extends Tile> superClass) {
        return superClass.isAssignableFrom(getClass());
    }
}
