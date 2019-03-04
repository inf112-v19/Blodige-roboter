package no.uib.inf112.core.player;

import java.util.List;

/**
 * @author Daniel
 */
public interface IPlayerHandler {
    /**
     * @return currently playing players
     */
    List<Player> getPlayers();

    /**
     * @return the number of players playing
     */
    int getPlayerCount();

    /**
     * Add all players to game
     * Pick a unique dock for each player
     *
     */
    void generatePlayers();

    /**
     * Do next turn in game
     */
    void doTurn();
}


