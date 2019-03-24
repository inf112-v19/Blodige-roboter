package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.MultiDirectionalTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Elg
 */
public abstract class AbstractMultiDirectionalTile<R> extends AbstractTile<R> implements MultiDirectionalTile<R> {

    private Set<Direction> dirs;

    public AbstractMultiDirectionalTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);

        Set<Direction> tempDirs = new HashSet<>();

        if (hasAttribute(Attribute.DIR_NORTH)) {
            tempDirs.add(Direction.NORTH);
        }
        if (hasAttribute(Attribute.DIR_EAST)) {
            tempDirs.add(Direction.EAST);
        }
        if (hasAttribute(Attribute.DIR_SOUTH)) {
            tempDirs.add(Direction.SOUTH);
        }
        if (hasAttribute(Attribute.DIR_WEST)) {
            tempDirs.add(Direction.WEST);
        }
        dirs = Collections.unmodifiableSet(tempDirs);

        if (dirs.isEmpty()) {
            System.out.println("WARN: Multi-directional TileGraphic " + tg + " has no direction specified");
        }
    }

    @NotNull
    @Override
    public Set<Direction> getDirections() {
        return dirs;
    }

    @Override
    public String toString() {
        return "AbstractMultiDirectionalTile{" +
                "dirs=" + dirs +
                "}";
    }
}
