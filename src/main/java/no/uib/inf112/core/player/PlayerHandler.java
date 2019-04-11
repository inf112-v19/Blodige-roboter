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
        this.flagCount = 0;
        players = new ArrayList<>(playerCount);

        user = new Player(0, 0, Direction.NORTH, map);
        players.add(user);

        for (int i = 1; i < playerCount; i++) {
            players.add(new StaticPlayer(i, 0, Direction.NORTH, map));
        }

        Stack<Integer> docks = new Stack<>();
        for (int i = 1; i <= playerCount; i++) {
            docks.push(i);
        }
        Collections.shuffle(docks);

        for (IPlayer player : players) {
            player.setDock(docks.pop());
        }
        GameGraphics.scheduleSync(() -> analyseMap(map), 0);
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
        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                Tile boardTile = map.getTile(MapHandler.BOARD_LAYER_NAME, x, y);
                Tile flagTile = map.getTile(MapHandler.FLAG_LAYER_NAME, x, y);

                if (boardTile != null && boardTile.getTileType() == TileType.SPAWN) {
                    for (IPlayer player : players) {
                        SpawnTile spawnTile = (SpawnTile) boardTile;
                        if (player.getDock() == spawnTile.getSpawnNumber()) {
                            player.teleport(boardTile.getX(), boardTile.getY());
                            player.setBackup(boardTile.getX(), boardTile.getY());
                        }
                    }
                }

                if(flagTile != null && flagTile.getTileType() == TileType.FLAG) {
                    flagCount++;
                }
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
