package no.uib.inf112.core.ui.actors;

import com.badlogic.gdx.graphics.Color;

/**
 * @author Elg
 */
public interface DisabledVisualizer {

    /**
     * @return If this object is disabled
     */
    boolean isDisabled();

    /**
     * Automatically overwritten when extending {@link com.badlogic.gdx.scenes.scene2d.Actor Actor}
     *
     * @return The color of the object
     */
    Color getColor();

    /**
     * Show if this is disabled or not
     */
    default void act() {
        if (isDisabled()) {
            getColor().a = 0.5f;
        } else {
            getColor().a = 1f;
        }
    }

}
