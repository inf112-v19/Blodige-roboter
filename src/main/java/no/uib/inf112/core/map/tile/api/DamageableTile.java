package no.uib.inf112.core.map.tile.api;

/**
 * A tile that can be damaged based on a integer amount
 *
 * @author Elg
 */
public interface DamageableTile extends Tile {

    /**
     * Damage the given tile
     *
     * @param damageAmount amount to damage the tile with
     */
    void damage(int damageAmount);
}
