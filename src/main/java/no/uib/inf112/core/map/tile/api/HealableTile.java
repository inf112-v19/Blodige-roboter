package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface HealableTile extends Tile {

    void heal(int amount);

    default void heal() {
        heal(Integer.MAX_VALUE);
    }
}
