package no.uib.inf112.core.player;

import no.uib.inf112.core.map.TileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An entity in on the board
 *
 * @author Elg
 */
public interface Entity {

    /**
     * @return The X-coordinate of the entity
     */
    int getX();

    /**
     * @return The Y-coordinate of the entity
     */
    int getY();

    /**
     * @return the tile of this entity, if {@code null} do not render this entity
     */
    @Nullable
    TileType getTileType();

    /**
     * @return the direction the entity is facing
     */
    @NotNull
    Direction getDirection();

    /**
     * Sets the direction the entity is facing
     */
    void setDirection(@NotNull Direction direction);

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
