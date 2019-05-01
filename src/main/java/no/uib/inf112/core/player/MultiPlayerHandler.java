package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.tiles.SpawnTile;
import no.uib.inf112.core.multiplayer.IClient;
import no.uib.inf112.core.multiplayer.dtos.NewGameDto;
import no.uib.inf112.core.multiplayer.dtos.PlayerDto;
import no.uib.inf112.core.multiplayer.dtos.StartRoundDto;
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
    private Player user;
    private boolean gameOver;
    private long startTime;
    private IClient client;
    private StartRoundDto startRoundDto;

    public MultiPlayerHandler(@NotNull NewGameDto newGameDto, @NotNull MapHandler map, IClient client) {
        if (newGameDto.players.size() < 2 || newGameDto.players.size() > 8) {
            throw new IllegalArgumentException("Number of players not allowed: " + newGameDto.players.size());
        }
        playerCount = newGameDto.players.size();
        flagCount = 0;
        players = new ArrayList<>(playerCount);
        gameOver = false;
        startTime = System.currentTimeMillis();
        wonPlayers = new TreeMap<>();
        this.client = client;
        addPlayers(map, newGameDto);
    }


    @Override
    public void endTurn() {
        client.sendSelectedCards(user.isPoweredDown(), user.getCardList());
        GameScreen.getUiHandler().hideDrawnCards();
    }

    /**
     * Starts the round by setting up drawncards for the given mainplayer based on cards received in the startRound object
     *
     * @param startRoundDto object containing data about the round.
     */
    public void startRound(@NotNull StartRoundDto startRoundDto) {
        user.getCards().setDrawnCards(startRoundDto.drawnCards);
    }

    /**
     * Runs a given round sets correct selected cards and runs the roborally round.
     *
     * @param startRoundDto object containing data about the round.
     */
    public void runRound(StartRoundDto startRoundDto) {
        this.startRoundDto = startRoundDto;
        for (IPlayer player : players) {
            if (!mainPlayer().equals(player)) {
                OnlinePlayer onlinePlayer = (OnlinePlayer) player;
                for (PlayerDto playerDto : startRoundDto.players) {
                    if (playerDto.id == onlinePlayer.getId()) {
                        onlinePlayer.setPoweredDown(playerDto.isPoweredDown);
                        onlinePlayer.setCards(playerDto.cards);
                    }
                }
            } else {
                for (PlayerDto playerDto : startRoundDto.players) {
                    if (playerDto.id == mainPlayer().getId() && !mainPlayer().isPoweredDown() && playerDto.cards != null) {
                        ((Player) mainPlayer()).getCards().setSelectedCards(playerDto.cards);
                    }
                }
            }
        }
        GameGraphics.getRoboRally().round();

    }

    @Override
    public void startTurn() {
        if (gameOver) {
            return;
        }
        user.getCards().clearSelectedCards();
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
            if (startRoundDto != null) {
                user.getCards().setDrawnCards(startRoundDto.drawnCards);
            }
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
     * @param map the map
     */
    private void addPlayers(@NotNull MapHandler map, @NotNull NewGameDto newGameDto) {
        ComparableTuple<Integer, Stack<SpawnTile>> result = analyseMap(map);
        flagCount = result.key;
        Stack<SpawnTile> spawnTiles = result.value;
        if (!spawnTiles.isEmpty()) {
            for (PlayerDto player : newGameDto.players) {
                SpawnTile spawnTile = spawnTiles.pop();
                if (player.id == newGameDto.userId) {
                    user = new Player(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map, new ComparableTuple<>(GameGraphics.mainPlayerName + " (you)", player.color), player.id);
                    user.setDock(spawnTile.getSpawnNumber());
                    players.add(user);
                } else {
                    IPlayer onlinePlayer = new OnlinePlayer(spawnTile.getX(), spawnTile.getY(), Direction.NORTH, map, new ComparableTuple<>(player.name, player.color), player.id);
                    onlinePlayer.setDock(spawnTile.getSpawnNumber());
                    players.add(onlinePlayer);
                }
            }
        } else {
            for (int i = 0; i < playerCount; i++) {
                NonPlayer nonPlayer = new NonPlayer(i, 0, Direction.NORTH, map, new ComparableTuple<>("blue", Color.BLUE));
                nonPlayer.setDock(i);
                players.add(nonPlayer);
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
