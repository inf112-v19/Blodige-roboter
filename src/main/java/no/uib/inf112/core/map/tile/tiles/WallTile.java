package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractMultiDirectionalTile;
import no.uib.inf112.core.map.tile.api.CollidableTile;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class WallTile extends AbstractMultiDirectionalTile implements CollidableTile {

    public WallTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public boolean willCollide(@NotNull MovableTile tile, @NotNull Direction dir) {
        //tile wants to move from this tile
        if (tile.getX() == getX() && tile.getY() == getY()) {
            return getDirections().contains(dir);
        } else {
            //the tile try and move onto this tile
            return getDirections().contains(dir.inverse());
        }
    }

    @Override
    public String toString() {
        return "WallTile{dirs=" + getDirections() + "}";
    }
}
