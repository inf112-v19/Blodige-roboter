package no.uib.inf112.core.player;

import java.util.ArrayList;

public class PlayerHandler implements IPlayerHandler {

    private final int playerCount;

    private ArrayList<Player> players;

    /**
     *
     * @param playerCount
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
            players.add(new Player(5 + i, 2, Direction.NORTH, false));
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

    public void doTurn() {
        //TODO Issue #44 check if dead
        //TODO Issue #24 check if is powered down (then heal)
        for(Player player : players)
        for (Card card : player.getCards()) {
            player.getRobot().move(card.getAction());
            //TODO Issue #44 check if player is out side of map
        }
    }
}
