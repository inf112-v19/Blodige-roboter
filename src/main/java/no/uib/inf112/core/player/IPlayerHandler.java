package no.uib.inf112.core.player;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.SpawnTile;
import no.uib.inf112.core.util.ComparableTuple;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Daniel
 */
public interface IPlayerHandler {

    /**
     * Add all player cards in a queue
     * Execute cards for each player after priority
     */
    void endTurn();

    /**
     * Add all player to the round queue
     * Do turn for first player
     */
    void startTurn();

    /**
     * @return currently playing players
     */
    List<IPlayer> getPlayers();

    /**
     * @return the number of players playing
     */
    int getPlayerCount();

    /**
     * Move players to given spawning docks
     * Count number of flags in map
     *
     * @param map
     */
    default ComparableTuple<Integer, Stack<SpawnTile>> analyseMap(MapHandler map) {
        Stack<SpawnTile> spawnTiles = new Stack<>();
        int flagCount = 0;
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Tile boardTile = map.getTile(MapHandler.BOARD_LAYER_NAME, x, y);
                Tile flagTile = map.getTile(MapHandler.FLAG_LAYER_NAME, x, y);

                if (boardTile != null && boardTile.getTileType() == TileType.SPAWN) {
                    SpawnTile spawnTile = (SpawnTile) boardTile;
                    spawnTiles.add(spawnTile);
                }

                if (flagTile != null && flagTile.getTileType() == TileType.FLAG) {
                    flagCount++;
                }
            }
        }
        return new ComparableTuple<Integer, Stack<SpawnTile>>(flagCount, spawnTiles);
    }

    /**
     * Checks if game is over
     * Updates game over field variable
     */
    void checkGameOver();

    /**
     * Rank players according to flags and
     * time played
     * @return String list of players ranked in correct order
     */
    String[] rankPlayers();

    /**
     * @return number of flags to catch
     */
    int getFlagCount();

    IPlayer mainPlayer();

    boolean isGameOver();

    Map<IPlayer, Long> getWonPlayers();
}

