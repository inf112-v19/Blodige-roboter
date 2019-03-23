package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface HealableTile<R> extends Tile<R> {

    void heal(int amount);

}
