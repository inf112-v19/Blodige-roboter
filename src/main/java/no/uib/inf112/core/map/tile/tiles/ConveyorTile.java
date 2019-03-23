package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.MultiDirectionalTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Elg
 */
public class ConveyorTile extends AbstractTile<Vector2Int> implements MultiDirectionalTile<Vector2Int> {

    private Set<Direction> dirs;

    public ConveyorTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);

        Set<Direction> tempdirs = new HashSet<>();

        if (hasAttribute(Attribute.DIR_NORTH)) {
            tempdirs.add(Direction.NORTH);
        }
        if (hasAttribute(Attribute.DIR_EAST)) {
            tempdirs.add(Direction.EAST);
        }
        if (hasAttribute(Attribute.DIR_SOUTH)) {
            tempdirs.add(Direction.SOUTH);
        }
        if (hasAttribute(Attribute.DIR_WEST)) {
            tempdirs.add(Direction.WEST);
        }

        dirs = Collections.unmodifiableSet(tempdirs);

    }

    @Override
    public Vector2Int action(@NotNull Tile tile) {
        //TODO
        return null;
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotMoving();
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Collections.singletonList(Attribute.MOVEABLE);
    }

    @NotNull
    @Override
    public Set<Direction> getDirections() {
        return dirs;
    }

    @Override
    public String toString() {
        return "ConveyorTile{" + "dirs=" + dirs + "}";
    }
}
