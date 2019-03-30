package no.uib.inf112.core.map.tile.api;

public interface DamagableTile extends Tile {
    void damage(int damageAmount);

    default void damage() {
        damage(1);
    }
}
