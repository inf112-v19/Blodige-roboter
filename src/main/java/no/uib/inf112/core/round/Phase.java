package no.uib.inf112.core.round;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;

import java.util.List;

/**
 * @author Elg
 */
public class Phase {

    private final TileType tileType;
    private final long delay; //delay till next phase

    public Phase(TileType tileType, int delay) {
        this.tileType = tileType;
        this.delay = delay;
    }

    void startPhase(MapHandler map) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                List<Tile> tiles = map.getAllTiles(x, y);

                for (Tile tile : tiles) {

                    //get a tile of the correct type
                    if (tile != null && tile.getTileType() == tileType) {

                        for (Tile otherTile : tiles) {
                            //do action
                            if (otherTile != tile && tile.canDoAction(otherTile)) {
                                tile.action(tile);
                            }
                        }
                    }
                }
            }
        }
    }

    public long getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Phase{" + "tileType=" + tileType +
                ", delay=" + delay +
                '}';
    }
}
