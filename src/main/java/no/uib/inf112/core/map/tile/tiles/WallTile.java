package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractMultiDirectionalTile;
import no.uib.inf112.core.map.tile.api.CollidableTile;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;

/**
 * @author Elg
 */
public class WallTile extends AbstractMultiDirectionalTile implements CollidableTile {

    public WallTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public boolean willCollide(MovableTile tile, Direction dir) {
        //tile wants to move from this tile
        if (tile.getX() == getX() && tile.getY() == getY()) {
            return getDirections().contains(dir);

            //the tile try and move onto this tile
        } else {
            return getDirections().contains(dir.inverse());
        }
    }

    @Override
    public String toString() {
        return "WallTile{dirs=" + getDirections() + "}";
    }
}
