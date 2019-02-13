package no.uib.inf112.core.player;

public interface Card {


    /**
     * @return The action (movement) imposed by this card
     */
    Movement getAction();

    /**
     * @return The priority of this card
     */
    int getPriority();

}
