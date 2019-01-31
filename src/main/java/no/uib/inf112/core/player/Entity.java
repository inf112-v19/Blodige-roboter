package no.uib.inf112.core.player;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
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
    TiledMapTile getTile();

}
