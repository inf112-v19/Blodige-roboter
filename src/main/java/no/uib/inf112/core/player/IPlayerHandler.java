package no.uib.inf112.core.player;

import java.util.List;

/**
 * @author Daniel
 */
public interface IPlayerHandler {
    /**
     * Add all player cards in a queue
     * Execute cards for each player after priority
     */
    void endTurn();

    /**
     * Add All player to the round queue
     * Do turn for first player
     */
    void startTurn();

    /**
     * If no more players, return
     * else do turn for next player in queue
     */
    void nextPlayer();

    /**
     * @return currently playing players
     */
    List<IPlayer> getPlayers();

    /**
     * @return the number of players playing
     */
    int getPlayerCount();

    /**
     * Add all players to game
     * Pick a unique dock for each player
     */
    void generatePlayers();

}


