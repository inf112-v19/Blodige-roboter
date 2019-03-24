package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public abstract class AbstractTile<R> implements Tile<R> {

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

    //TODO test (this should return true only if the tiletype of this instance has the given attribute)
    @Override
    public boolean hasAttribute(@Nullable Attribute attribute) {
        return tg.getAttributes().contains(attribute);
    }

    //TODO test (this should look at all the required atts and check that hasAttributes return true on all of them)
    @Override
    public boolean canDoAction(@NotNull Tile tile) {
        return tile != this && (requiredAttributes() == null || requiredAttributes().stream().allMatch(this::hasAttribute));
    }
}
