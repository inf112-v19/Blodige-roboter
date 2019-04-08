package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.SpawnTile;
import no.uib.inf112.core.player.IPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpawnPhase extends AbstractPhase {

    public SpawnPhase(long totalRunTime) {
        super(totalRunTime);
    }

    @Override
    public void startPhase(@NotNull MapHandler map) {
        List<IPlayer> players = GameGraphics.getRoboRally().getPlayerHandler().getPlayers();

        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                List<Tile> tiles = map.getAllTiles(x, y);

                for (Tile tile : tiles) {
                    if (tile != null && tile.getTileType() == TileType.SPAWN) {
                        for(IPlayer player : players) {
                            SpawnTile spawnTile = (SpawnTile) tile;
                            if(player.getDock() == spawnTile.getSpawnNumber()) {
                                player.teleport(tile.getX(), tile.getY());
                                player.setBackup(tile.getX(), tile.getY());
                            }
                        }
                    }
                }
            }
        }
    }
}