package no.uib.inf112.core.map.tile.api;

/**
 * A tile that has health and can be healed
 *
 * @author Elg
 */
public interface HealableTile extends Tile {

    /**
     * Heal a tile by the given amount.
     *
     * @param amount How much to heal the player
     */
    void heal(int amount);

    /**
     * Heal this tile by the maximum amount possible.
     * <p>
     * This method is equivalent to {@code heal(Integer.MAX_VALUE)}
     */
    default void heal() {
        heal(Integer.MAX_VALUE);
    }
}
