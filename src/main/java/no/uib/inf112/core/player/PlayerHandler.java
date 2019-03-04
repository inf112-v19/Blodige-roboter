package no.uib.inf112.core.player;

import no.uib.inf112.desktop.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class PlayerHandler implements IPlayerHandler {

    private int playerCount;
    private ArrayList<Player> players;

    /**
     * @param playerCount
     * @throws IllegalArgumentException if playercount is invalid
     */
    public PlayerHandler(int playerCount) {
        if (playerCount < 2) {
            throw new IllegalArgumentException("Not enough players");
        } else if (playerCount > 8) {
            throw new IllegalArgumentException("Too many players");
        }
        this.playerCount = playerCount;
        players = new ArrayList<>(playerCount);

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(5 + i, 2, Direction.NORTH));
        }

        Stack<Integer> docks = new Stack<>();
        for (int i = 1; i <= playerCount; i++) {
            docks.push(i);
        }
        Collections.shuffle(docks);

        for (Player player : players) {
            player.setDock(docks.pop());
        }

    }

    @Override
    public void generatePlayers() {
        //Keeping this in case we want to generate new players, currently only used for testing
        if (Main.HEADLESS) {
            players = new ArrayList<>(playerCount);

            for (int i = 0; i < playerCount; i++) {
                players.add(new Player(5 + i, 2, Direction.NORTH));
            }

            Stack<Integer> docks = new Stack<>();
            for (int i = 1; i <= playerCount; i++) {
                docks.push(i);
            }
            Collections.shuffle(docks);

            for (Player player : players) {
                player.setDock(docks.pop());
            }
        }

    }

    @Override
    public void doTurn() {
        //TODO Issue #44 check if dead
        //TODO Issue #44 check if player is out side of map

        for (Player player : players) {
            if (player != mainPlayer()) {
                continue;
            }
            if (player.isPoweredDown()) {
                //TODO Issue #24 check if is powered down (then heal)
                continue;
            } else {
                player.beginDrawCards();
            }
        }

    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * Temporary mainplayer
     *
     * @return player
     */
    public Player mainPlayer() {
        return players.get(0);
    }
}
