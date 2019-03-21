package no.uib.inf112.core.map.MapAction;

import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;

public interface MapAction {

    /**
     * Does the action on the entity parent and plays the corresponding sound
     */
    void doAction();

    /**
     * @return Returns resulting movement on the entity
     */
    Vector2Int getResultOfMovement();

    /**
     * @return the parent that triggered this Action
     */
    Entity getParent();
}
