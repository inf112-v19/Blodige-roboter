package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @param <T> The type
 * @author Elg
 */
public class ActionPhase<T extends Tile> extends AbstractPhase {

    private final TileType tileType;
    private final long delay; //delay till next phase

    /**
     * @param tileType
     * @param delay
     */
    public ActionPhase(@NotNull TileType tileType, int delay) {
        super(delay);
        if (tileType.getImplClass() == null || !(ActionTile.class.isAssignableFrom(tileType.getImplClass()))) {
            throw new IllegalArgumentException("Given tile type '" + tileType + "' cannot run actions");
        } else if (tileType.getAttributes().contains(Attribute.ACTIVE_ONLY_ON_STEP)) {
            throw new IllegalArgumentException("Given tile can not have its own phase, as it is activated when stepped on");
        }

        this.tileType = tileType;
        this.delay = delay;
    }

    @Override
    public void startPhase(@NotNull MapHandler map) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                List<Tile> tiles = map.getAllTiles(x, y);

                for (Tile tile : tiles) {
                    //get a tile of the correct type
                    if (tile != null && tile.getTileType() == tileType) {
                        //noinspection unchecked checked in constructor
                        ActionTile<T> actionTile = (ActionTile<T>) tile;
                        for (Tile otherTile : tiles) {
                            if (otherTile != tile && actionTile.canDoAction(otherTile)) {
                                //noinspection unchecked checked in actionTile.canDoAction
                                actionTile.action((T) otherTile);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Phase{" + "tileType=" + tileType +
                ", delay=" + delay +
                '}';
    }
}
