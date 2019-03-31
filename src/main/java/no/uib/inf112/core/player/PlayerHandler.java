package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.util.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static no.uib.inf112.core.GameGraphics.HEADLESS;
import static no.uib.inf112.core.GameGraphics.getInputMultiplexer;

public class PlayerHandler implements IPlayerHandler {

    private int playerCount;
    private List<IPlayer> players;
    private IPlayer user;
    private MapHandler map;

    /**
     * @param playerCount
     * @throws IllegalArgumentException if playercount is invalid
     */
    public PlayerHandler(int playerCount, MapHandler map) {
        if (playerCount < 2) {
            if (!HEADLESS) {
                throw new IllegalArgumentException("Not enough players");
            }
        } else if (playerCount > 8) {
            throw new IllegalArgumentException("Too many players");
        }
        this.map = map;
        this.playerCount = playerCount;
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
    }

    @Override
    public void generatePlayers() {
        //Keeping this in case we want to generate new players, currently only used for testing
        if (HEADLESS) {
            players = new ArrayList<>(playerCount);
            for (int i = 0; i < playerCount; i++) {
                players.add(new NonPlayer(5 + i, 2, Direction.NORTH, map));
            }

            Stack<Integer> docks = new Stack<>();
            for (int i = 1; i <= playerCount; i++) {
                docks.push(i);
            }
            Collections.shuffle(docks);

            for (IPlayer player : players) {
                player.setDock(docks.pop());
            }
        }

    }

    @Override
    public void generateOnePlayer() {
        players = new ArrayList<>(playerCount);
        players.add(new NonPlayer(0, 0, Direction.NORTH, map));
        players.get(0).setDock(1);
    }

    @Override
    public void endTurn() {
        GameGraphics.getRoboRally().round();
    }

    @Override
    public void startTurn() {

        Player p = mainPlayer();
        if (p.isDestroyed()) {
            return;
        }
        if (p.isPoweredDown()) {
            p.heal();
            p.poweredDown = false;
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

    public NonPlayer testPlayer() {
        if (!HEADLESS) {
            throw new IllegalStateException("Game is not headless");
        }
        return (NonPlayer) players.get(0);
    }


    public Player mainPlayer() {
        if (HEADLESS) {
            throw new IllegalStateException("Game is headless");
        }
        return (Player) players.get(0);
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
