package no.uib.inf112.core.player;

import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;


/**
 * An entity in on the board
 *
 * @author Elg
 */
public interface Entity extends HealableTile, MovableTile, ColorableTile, CollidableTile, BackupableTile, DamageableTile {

    /**
     * @return If this entity has changed in some way
     */
    boolean shouldUpdate();

    /**
     * Set the update state to true
     */
    default void update() {
        update(true);
    }

    /**
     * @param update new update state
     */
    void update(boolean update);

    @Override
    default boolean willCollide(Tile tile, Direction dir) {
        //entities cannot be walked on
        return true;
    }

    @NotNull
    @Override
    default Set<Direction> getDirections() {
        return Collections.singleton(getDirection());
    }
}
