package no.uib.inf112.core.map.tile.api;

import com.badlogic.gdx.graphics.Color;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface ColorTile<R> extends Tile<R> {

    @NotNull
    Color getColor();

    void setColor(@NotNull Color color);
}
