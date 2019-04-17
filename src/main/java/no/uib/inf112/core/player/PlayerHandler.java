package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.SpawnTile;
import no.uib.inf112.core.util.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static no.uib.inf112.core.GameGraphics.HEADLESS;

public class PlayerHandler implements IPlayerHandler {

    private int playerCount;
    private int flagCount;
    private List<IPlayer> players;
    private IPlayer user;

    /**
     * @param playerCount
     * @throws IllegalArgumentException if playerCount is invalid
     */
    public PlayerHandler(int playerCount, MapHandler map) {
        if (playerCount < 2) {
            if (!HEADLESS) {
                throw new IllegalArgumentException("Not enough players");
            }
        } else if (playerCount > 8) {
            throw new IllegalArgumentException("Too many players");
        }
        this.playerCount = playerCount;
        flagCount = 0;
        players = new ArrayList<>(playerCount);
        analyseMap(map);
    }

    @Override
    public void endTurn() {
        GameGraphics.getRoboRally().round();
    }

    @Override
    public void startTurn() {
        GameGraphics.getUiHandler().getPowerButton().resetAlpha();

        Player p = (Player) mainPlayer();
        p.setPoweredDown(p.willPowerDown());
        if (p.isDestroyed()) {
            return;
        }
        if (p.isPoweredDown()) {
            p.heal(Player.MAX_HEALTH);
            p.setWillPowerDown(false);
            p.endDrawCards();
        } else {
            p.beginDrawCards();
        }
    }

    @Override
    public List<IPlayer> getPlayers() {
        return players;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    @Override
    public void analyseMap(MapHandler map) {
        Stack<SpawnTile> spawnTiles = new Stack<>();
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Tile boardTile = map.getTile(MapHandler.BOARD_LAYER_NAME, x, y);
                Tile flagTile = map.getTile(MapHandler.FLAG_LAYER_NAME, x, y);

                if (boardTile != null && boardTile.getTileType() == TileType.SPAWN) {
                    SpawnTile spawnTile = (SpawnTile) boardTile;
                    if (spawnTile.getSpawnNumber() <= playerCount) {
                        spawnTiles.add(spawnTile);
                    }
                }

                if (flagTile != null && flagTile.getTileType() == TileType.FLAG) {
                    flagCount++;
                }
            }
        }
        if (!spawnTiles.empty()) {
            Collections.shuffle(spawnTiles);
            SpawnTile spawnTile = spawnTiles.pop();
            user = new Player(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map);
            user.setDock(spawnTile.getSpawnNumber());
            players.add(user);

            for (int i = 1; i < playerCount; i++) {
                SpawnTile tile = spawnTiles.pop();
                StaticPlayer staticPlayer = new StaticPlayer(tile.getX(), tile.getY(), Direction.NORTH, map);
                staticPlayer.setDock(tile.getSpawnNumber());
                players.add(staticPlayer);
            }
        } else {
            for (int i = 0; i < playerCount; i++) {
                StaticPlayer staticPlayer = new StaticPlayer(i, 0, Direction.NORTH, map);
                staticPlayer.setDock(i);
                players.add(staticPlayer);
            }
        }
    }

    public IPlayer testPlayer() {
        if (!HEADLESS) {
            throw new IllegalStateException("Game is not headless");
        }
        return players.get(0);
    }


    public IPlayer mainPlayer() {
        if (HEADLESS) {
            throw new IllegalStateException("Game is headless");
        }
        return players.get(0);
    }

    @Override
    public int getFlagCount() {
        return flagCount;
    }

    @Override
    public String toString() {
        return "PlayerHandler{" +
                "playerCount= " + playerCount +
                ", players= " + players +
                ", user= " + user +
                "}";
    }
}
