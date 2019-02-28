package no.uib.inf112.core.player;

import java.util.List;

/**
 * @author Daniel
 */
public interface IPlayerHandler {
    /**
     * Add all player cards in a queue
     * Execute movements for each player after priority
     */
    void endTurn();

    /**
     * Add All player to the round queue
     */
    void startTurn();

    /**
     *
     */
    void nextPlayer();

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
     * @param headless true if you want to generate players without graphics, false otherwise
     */
    void generatePlayers(boolean headless);

    /**
     * Get deck which is currently in the game
     *
     * @return deck
     */
    Deck getDeck();
}
