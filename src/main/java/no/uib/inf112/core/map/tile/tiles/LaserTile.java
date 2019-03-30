package no.uib.inf112.core.map.tile.tiles;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractMultiDirectionalTile;
import no.uib.inf112.core.map.tile.api.ColorableTile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

public class LaserTile extends AbstractMultiDirectionalTile implements ColorableTile {
    private Color color;

    public LaserTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);
        color = Color.DARK_GRAY;
    }

    @NotNull
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull Color color) {
        this.color = color;
    }
}
