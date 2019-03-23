package no.uib.inf112.core.player;

import no.uib.inf112.core.map.tile.api.ColorTile;
import no.uib.inf112.core.map.tile.api.HealableTile;
import no.uib.inf112.core.map.tile.api.MoveableTile;
import no.uib.inf112.core.map.tile.api.SingleDirectionalTile;


/**
 * An entity in on the board
 *
 * @author Elg
 */
public interface Entity<R> extends HealableTile<R>, MoveableTile<R>, SingleDirectionalTile<R>, ColorTile<R> {

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

}
