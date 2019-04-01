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
     * Add all player to the round queue
     * Do turn for first player
     */
    void startTurn();

    /**
     * @return currently playing players
     */
    List<IPlayer> getPlayers();

    /**
     * @return the number of players playing
     */
    int getPlayerCount();
}


