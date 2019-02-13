package no.uib.inf112.core.player;

import java.util.ArrayList;

/**
 * @author Daniel
 */
public interface IPlayerHandler {
    /**
     * Get currently playing players
     * @return
     */
    ArrayList<Player> getPlayers();

    /**
     * Get the number of players playing
     * @return
     */
    int getPlayerCount();

    /**
     * Add all players to game
     * Pick a unique dock for each player
     */
    void generatePlayers();

    /**
     * Do next turn in game
     *
     */
    void doTurn();

    /**
     * Get deck which is currently in the game
     *
     * @return deck
     */
    Deck getDeck();
}
