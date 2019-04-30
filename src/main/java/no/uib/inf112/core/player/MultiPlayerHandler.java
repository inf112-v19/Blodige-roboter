package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.tiles.SpawnTile;
import no.uib.inf112.core.multiplayer.Client;
import no.uib.inf112.core.multiplayer.jsonClasses.NewGameDto;
import no.uib.inf112.core.multiplayer.jsonClasses.PlayerDto;
import no.uib.inf112.core.multiplayer.jsonClasses.StartRoundDto;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MultiPlayerHandler implements IPlayerHandler {

    private int playerCount;
    private int flagCount;
    private List<IPlayer> players;
    private Map<IPlayer, Long> wonPlayers;
    private Stack<ComparableTuple<String, Color>> colors;
    private Player user;
    private boolean gameOver;
    private long startTime;
    private Client client;

    public MultiPlayerHandler(NewGameDto newGameDto, MapHandler map, Client client) {
        // Check enough players
        playerCount = newGameDto.players.size();
        flagCount = 0;
        players = new ArrayList<>(playerCount);
        gameOver = false;
        startTime = System.currentTimeMillis();
        wonPlayers = new TreeMap<>();
        this.client = client;
        //colors = new Stack<>();
        //addColors();
        addPlayers(map, newGameDto);
    }


    @Override
    public void endTurn() {
        StartRoundDto startRoundDto = client.setSelectedCards(user.getCardList(), user.id);
        for (IPlayer player : players) {
            if (!mainPlayer().equals(player)) {
                OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                Iterator<PlayerDto> iterator = startRoundDto.players.iterator();
                while (iterator.hasNext()) {
                    PlayerDto playerDto = iterator.next();
                    if (playerDto.id == onlinePlayer.getId()) {
                        onlinePlayer.setCards(playerDto.cards);
                    }
                }
            }
        }
        GameGraphics.getRoboRally().round();
        user.getCards().setDrawnCards(startRoundDto.drawnCards);
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
            p.setWillPowerDown(false);
            p.endDrawCards();
        } else {
            GameScreen.getUiHandler().showDrawnCards();
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
    private void addPlayers(MapHandler map, NewGameDto newGameDto) {
        ComparableTuple<Integer, Stack<SpawnTile>> result = analyseMap(map);
        flagCount = result.key;
        Stack<SpawnTile> spawnTiles = result.value;
        for (PlayerDto player : newGameDto.players) {
            SpawnTile spawnTile = spawnTiles.pop();
            if (player.id == newGameDto.userId) {
                user = new Player(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map, new ComparableTuple<>(GameGraphics.mainPlayerName, Color.MAGENTA), player.id);
                user.setDock(spawnTile.getSpawnNumber());
                players.add(user);
            } else {
                //Todo set colors
                IPlayer onlinePlayer = new OnlinePlayer(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map, new ComparableTuple<>(GameGraphics.mainPlayerName, Color.BLUE), player.id);
                onlinePlayer.setDock(spawnTile.getSpawnNumber());
                players.add(onlinePlayer);
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
            gameOver = true;
            return;
        }

        for (IPlayer player : players) {
            if (!player.isDestroyed()) {
                return;
            }
        }
        gameOver = false;
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
                } else {
                    return wonPlayers.get(p1).compareTo(wonPlayers.get(p2));
                }
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

    @Override
    public int getFlagCount() {
        return flagCount;
    }

    @Override
    @NotNull
    public IPlayer mainPlayer() {
        return user;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public Map<IPlayer, Long> getWonPlayers() {
        return wonPlayers;
    }
}
