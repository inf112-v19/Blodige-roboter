package no.uib.inf112.core.player;

import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.util.Vector2Int;

public interface IPlayer extends Comparable<IPlayer> {
    /**
     * damage the player by the given amount and handles death if health is less than or equal to 0
     *
     * @param damageAmount How much to damage the player
     * @throws IllegalArgumentException If the damage amount is not positive
     */
    void damage(int damageAmount);

    /**
     * Kill the player, decreasing their lives and depending on Main.headless permanently remove from map if there are
     * no lives left
     */
    void kill();

    /**
     * Heal the player by the given amount up to {@link #MAX_HEALTH}
     *
     * @param healAmount How much to heal
     * @throws IllegalArgumentException If the heal amount is not positive
     */
    void heal(int healAmount);

    /**
     * @return If the player is dead. A player is dead if their lives are 0 or less
     */
    boolean isDestroyed();

    /**
     * @param id
     * @return the next player card in queue
     */
    PlayerCard getNextCard(int id);

    /**
     * Move robot with given card
     *
     * @param card
     */
    void moveRobot(Card card);

    /**
     * @return lives
     */
    int getLives();

    /**
     * @return health
     */
    int getHealth();

    /**
     * @return if powered down or not
     */
    boolean isPoweredDown();

    /**
     * @return amount of damage tokens
     */
    int getDamageTokens();

    /**
     * @return backup position
     */
    Vector2Int getBackup();

    /**
     * Sets the backup for the player
     *
     * @param x
     * @param y
     */
    void setBackup(int x, int y);

    /**
     * @return players robot
     */
    Robot getRobot();

    /**
     * @return assigned dock
     */
    int getDock();

    /**
     * @param dock
     */
    void setDock(int dock);
}
