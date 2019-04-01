package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface ColorableTile extends Tile {

    /**
     * @return The current color of the tile
     */
    @NotNull
    Color getColor();

    /**
     * @param color new color of the tile
     */
    void setColor(@NotNull Color color);
}
