package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
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
        colors = addColors();
        addPlayers(map);
    }

    /**
     * @return
     */
    public static Stack<ComparableTuple<String, Color>> addColors() {
        Stack<ComparableTuple<String, Color>> colors = new Stack<>();
        colors.push(new ComparableTuple<>("Coral", Color.CORAL));
        colors.push(new ComparableTuple<>("Green", Color.GREEN));
        colors.push(new ComparableTuple<>("Purple", Color.PURPLE));
        colors.push(new ComparableTuple<>("Yellow", Color.YELLOW));
        colors.push(new ComparableTuple<>("Orange", Color.ORANGE));
        colors.push(new ComparableTuple<>("Cyan", Color.CYAN));
        colors.push(new ComparableTuple<>("Red", Color.RED));
        colors.push(new ComparableTuple<>("Blue", Color.BLUE));
        return colors;
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
        GameScreen.getUiHandler().getPowerButton().resetButton();

        Player p = (Player) mainPlayer();
        p.setPoweredDown(p.willPowerDown());
        if (p.isDestroyed()) {
            return;
        }
        if (p.isPoweredDown()) {
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

    /**
     * Move players to given spawning docks
     * Count number of flags in map
     *
     * @param map
     */
    private void addPlayers(MapHandler map) {
        ComparableTuple<Integer, Stack<SpawnTile>> result = analyseMap(map);
        flagCount = result.key;
        Stack<SpawnTile> spawnTiles = result.value;
        if (!HEADLESS) {
            if (!spawnTiles.empty()) {
                Collections.shuffle(spawnTiles);
                SpawnTile spawnTile = spawnTiles.pop();
                user = new Player(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map, new ComparableTuple<>(GameGraphics.mainPlayerName, Color.MAGENTA), 0);
                user.setDock(spawnTile.getSpawnNumber());
                players.add(user);
                while (!spawnTiles.isEmpty() && players.size() < playerCount) {
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
        } else {
            for (int i = 0; i < playerCount; i++) {
                if (!spawnTiles.isEmpty()) {
                    SpawnTile tile = spawnTiles.pop();
                    StaticPlayer staticPlayer = new StaticPlayer(tile.getX(), tile.getY(), Direction.NORTH, map, colors.pop());
                    staticPlayer.setDock(tile.getSpawnNumber());
                    players.add(staticPlayer);
                } else {
                    StaticPlayer staticPlayer = new StaticPlayer(i, 0, Direction.NORTH, map, colors.pop());
                    staticPlayer.setDock(i);
                    players.add(staticPlayer);
                }
            }
        }
    }

    @Override
    public void checkGameOver() {
        removePlayers();

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
    public Map<IPlayer, Long> getWonPlayers() {
        return wonPlayers;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void setGameOver(boolean state) {
        gameOver = state;
    }

    @Override
    public IPlayer mainPlayer() {
        if (!players.isEmpty()) {
            IPlayer player = players.get(0);
            if (player instanceof Player || HEADLESS) {
                return players.get(0);
            }
            gameOver = true;
        }
        return new Player(0, 0, Direction.NORTH, GameGraphics.getRoboRally().getCurrentMap(), new ComparableTuple<>("Dead", Color.BLACK), 0);
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
