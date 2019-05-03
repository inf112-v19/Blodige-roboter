package no.uib.inf112.core.multiplayer;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.cards.Card;

import java.util.List;

public interface IClient {

    /**
     * @return an array of all connected players
     */
    String[] getPlayerNames();

    /**
     * Send that party mode is on to the server
     */
    void setPartyModeOn();

    /**
     * Send given display name to the server
     *
     * @param name name to set
     */
    void setName(String name);

    /**
     * Send a message to the server that the game is started
     *
     * @param game game that have been started
     */
    void startGame(GameGraphics game);

    /**
     * send the users selected moves(either cards or powereddown) to the server
     *
     * @param poweredDown true if player is to power down this turn
     * @param cards       selected cards
     */
    void sendSelectedCards(boolean poweredDown, List<Card> cards);

    /**
     * Sets this client as responsible host on the server
     */
    void setHost();

    /**
     * Close the connections to the server
     */
    void closeConnection();
}
