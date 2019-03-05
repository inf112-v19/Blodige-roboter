package no.uib.inf112.core.player;

import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.util.Vector2Int;

public interface IPlayer extends Comparable<IPlayer> {
    /**
     * @return If the player is dead. A player is dead if their lives are 0 or less
     */
    boolean isDestroyed();

    void moveRobot(Card card);

    int getLives();

    int getHealth();

    boolean isPoweredDown();

    int getDamageTokens();

    Vector2Int getBackup();

    void setBackup(int x, int y);

    Robot getRobot();

    int getDock();

    void setDock(int dock);
}
