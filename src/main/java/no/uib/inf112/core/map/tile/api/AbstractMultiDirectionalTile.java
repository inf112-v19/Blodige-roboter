package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author Elg
 */
public abstract class AbstractMultiDirectionalTile extends AbstractTile implements MultiDirectionalTile {

    private Set<Direction> dirs;

    public AbstractMultiDirectionalTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);

        dirs = Direction.getDirectionsFromTile(this);
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
        return "AbstractMultiDirectionalTile{" + "dirs=" + dirs + "}";
    }
}
