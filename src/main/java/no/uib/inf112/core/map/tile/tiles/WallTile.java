package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractMultiDirectionalTile;
import no.uib.inf112.core.map.tile.api.CollidableTile;
import no.uib.inf112.core.map.tile.api.Tile;
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
    public boolean willCollide(Tile tile, Direction dir) {
        //tile wants to move from this tile
        if (tile.getX() == getX() && tile.getY() == getY()) {
            System.out.println(getDirections().contains(dir));
            System.out.println(dir);
            System.out.println(getDirections());
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
