package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.SpawnTile;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;

import java.util.*;

import static no.uib.inf112.core.GameGraphics.HEADLESS;

public class PlayerHandler implements IPlayerHandler {

    private int playerCount;
    private int flagCount;
    private List<IPlayer> players;
    private Map<IPlayer, Long> wonPlayers;
    private Stack<ComparableTuple<String, Color>> colors;
    private IPlayer user;
    private boolean gameOver;
    private long startTime;

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
        gameOver = false;
        startTime = System.currentTimeMillis();
        wonPlayers = new TreeMap<>();
        colors = new Stack<>();
        analyseMap(map);
    }

    private void addColors() {
        colors.push(new ComparableTuple<>("Coral", Color.CORAL));
        colors.push(new ComparableTuple<>("Green", Color.GREEN));
        colors.push(new ComparableTuple<>("Purple", Color.PURPLE));
        colors.push(new ComparableTuple<>("Yellow", Color.YELLOW));
        colors.push(new ComparableTuple<>("Orange", Color.ORANGE));
        colors.push(new ComparableTuple<>("Cyan", Color.CYAN));
        colors.push(new ComparableTuple<>("Red", Color.RED));
        colors.push(new ComparableTuple<>("Blue", Color.BLUE));
    }

    @Override
    public void endTurn() {
        GameGraphics.getRoboRally().round();
    }

    @Override
    public void startTurn() {
        if (gameOver) {
            return;
        }
        GameScreen.getUiHandler().getPowerButton().resetAlpha();

        Player p = (Player) mainPlayer();
        p.setPoweredDown(p.willPowerDown());
        if (p.isDestroyed()) {
            return;
        }
        if (p.isPoweredDown()) {
            p.heal(IPlayer.MAX_HEALTH);
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
        addColors();
        Collections.shuffle(colors);
        if (!spawnTiles.empty()) {
            Collections.shuffle(spawnTiles);
            SpawnTile spawnTile = spawnTiles.pop();
            user = new Player(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map, new ComparableTuple<>(GameGraphics.mainPlayerName, Color.MAGENTA));
            user.setDock(spawnTile.getSpawnNumber());
            players.add(user);

            for (int i = 1; i < playerCount; i++) {
                SpawnTile tile = spawnTiles.pop();
                NonPlayer nonPlayer = new NonPlayer(tile.getX(), tile.getY(), Direction.NORTH, map, colors.pop());
                nonPlayer.setDock(tile.getSpawnNumber());
                players.add(nonPlayer);
            }
        } else {
            for (int i = 0; i < playerCount; i++) {
                NonPlayer nonPlayer = new NonPlayer(i, 0, Direction.NORTH, map, colors.pop());
                nonPlayer.setDock(i);
                players.add(nonPlayer);
            }
        }
    }

    @Override
    public void checkGameOver() {
        players.removeIf(player -> {
            if (player.getFlags() == flagCount || player.isDestroyed()) {
                wonPlayers.put(player, System.currentTimeMillis());
                return true;
            }
            return false;
        });
        if (players.size() == 1) {
            wonPlayers.put(players.get(0), Math.abs(System.currentTimeMillis() - startTime));
            players.remove(0);
            gameOver = true;
            return;
        }

        for (IPlayer player : players) {
            if (!player.isDestroyed()) {
                return;
            }
        }
        gameOver = true;
    }

    @Override
    public String[] rankPlayers() {
        players.forEach(player -> wonPlayers.put(player, System.currentTimeMillis()));
        List<IPlayer> playerStackWon = new ArrayList<>(wonPlayers.keySet());
        playerStackWon.sort((p1, p2) -> {
            if (p1.getFlags() == p2.getFlags()) {
                if (p1.isDestroyed() && !p2.isDestroyed()) {
                    return 1;
                } else if (p2.isDestroyed() && !p1.isDestroyed()) {
                    return -1;
                } else if (p1.isDestroyed() && p2.isDestroyed()) {
                    return wonPlayers.get(p2).compareTo(wonPlayers.get(p1));
                } else
                    return wonPlayers.get(p1).compareTo(wonPlayers.get(p2));
            } else {
                return Integer.compare(p2.getFlags(), p1.getFlags());
            }
        });

        String[] playersInRankingOrder = new String[playerCount];
        int i = 0;
        for (IPlayer player : playerStackWon) {
            playersInRankingOrder[i++] = i + ". " + player.getName() + ": " + player.getFlags() + " flags";
        }
        return playersInRankingOrder;
    }

    public Map<IPlayer, Long> getWonPlayers() {
        return wonPlayers;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public IPlayer mainPlayer() {
        if (!players.isEmpty()) {
            IPlayer player = players.get(0);
            if (player instanceof Player || HEADLESS) return players.get(0);
        }
        gameOver = true;
        return new Player(0, 0, Direction.NORTH, GameGraphics.getRoboRally().getCurrentMap(), new ComparableTuple<>("Dead", Color.BLACK));
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
