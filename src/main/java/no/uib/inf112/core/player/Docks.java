package no.uib.inf112.core.player;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;

import java.util.List;

public class Docks {


    public Docks(MapHandler map) {
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                List<Tile> tiles = map.getAllTiles(x, y);

                for (Tile tile : tiles) {
                    if (tile != null && tile.getTileType() == TileType.SPAWN) {

                    }
                }
            }
        }
    }
}