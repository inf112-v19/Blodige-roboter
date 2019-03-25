package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.CollidableTile;
import no.uib.inf112.core.map.tile.api.MoveableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class WallTile extends AbstractMultiDirectionalTile<Void> implements CollidableTile<Void> {

    public WallTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public boolean willCollide(MoveableTile tile, Direction dir) {
        System.out.println("getDirections() = " + getDirections());
        System.out.println("tile.getX() = " + tile.getX());
        System.out.println("tile.getY() = " + tile.getY());
        System.out.println("getX() = " + getX());
        System.out.println("getY() = " + getY());
        //tile wants to move from this tile
        if (tile.getX() == getX() && tile.getY() == getY()) {
            System.out.println(tile + " cannot move in the direction " + dir);
            return getDirections().contains(dir);

            //the tile try and move onto this tile
        } else if (getDirections().contains(dir.inverse())) {
            System.out.println(tile + " cannot step onto this tile from " + dir);
            return true;
        }
        System.out.println(tile + " CAN move in direction " + dir);
        return false;
    }

    @Nullable
    @Override
    public Void action(@NotNull Tile tile) {
        return null;
    }

    @Override
    public void playActionSound() {

    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Collections.singletonList(Attribute.MOVEABLE);
    }

    @Override
    public String toString() {
        return "WallTile{dirs=" + getDirections() + "}";
    }
}
