package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Cleanup;
import no.uib.inf112.core.map.tile.api.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Elg
 */
public class CleanupPhase implements Phase {

    private final TileType tileType;

    /**
     * @param tileType The tile to clean with
     */
    public CleanupPhase(@NotNull TileType tileType) {
        if (tileType.getImplClass() == null || !(Cleanup.class.isAssignableFrom(tileType.getImplClass()))) {
            throw new IllegalArgumentException("Given tile type '" + tileType + "' cannot run actions");
        } else if (tileType.getAttributes().contains(Attribute.ACTIVE_ONLY_ON_STEP)) {
            throw new IllegalArgumentException("Given tile can not have its own phase, as it is activated when stepped on");
        }

        this.tileType = tileType;
    }

    @Override
    public void startPhase(@NotNull MapHandler map, int phaseNr) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                List<Tile> tiles = map.getAllTiles(x, y);

                for (Tile tile : tiles) {
                    //get a tile of the correct type
                    if (tile != null && tile.hasSuperClass(Cleanup.class)) {
                        Cleanup cleanTile = (Cleanup) tile;
                        for (Tile otherTile : tiles) {
                            if (!otherTile.equals(tile) && cleanTile.canDoAction(otherTile)) {
                                //noinspection unchecked checked in actionTile.canDoAction
                                cleanTile.clean(otherTile);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public long getRunTime() {
        return 0; //clean in no time!
    }

    @Override
    public String toString() {
        return "Phase{" + "tileType=" + tileType + '}';
    }
}
