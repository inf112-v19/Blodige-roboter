package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.UVector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractStaticTilePhase extends AbstractPhase {

    private final TileType type;
    private Map<UVector2Int, Tile> tiles;

    public AbstractStaticTilePhase(long totalRunTime, TileType tileType) {
        super(totalRunTime);
        type = tileType;
    }

    private void calculateTiles(MapHandler map) {
        tiles = new HashMap<>();
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                for (Tile tile : map.getAllTiles(x, y)) {
                    if (tile.getTileType() == type) {
                        tiles.put(new UVector2Int(x, y), tile);
                    }
                }
            }
        }
    }

    @NotNull
    public Map<UVector2Int, Tile> getTiles(MapHandler map) {
        if (tiles == null) {
            calculateTiles(map);
        }
        return tiles;
    }
}
