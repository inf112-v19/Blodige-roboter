package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.DockableTile;
import no.uib.inf112.core.map.tile.api.SingleDirectionalTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

public class SpawnTile extends AbstractTile implements SingleDirectionalTile, DockableTile {
    private TileGraphic tg;

    public SpawnTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        this.tg = tg;
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection(@NotNull Direction direction) {

    }

    @Override
    public int getSpawnNumber() {
        return Integer.parseInt(tg.toString().replace("SPAWN", ""));
    }

    @Override
    public String toString() {
        return "SpawnTile{" + tg.toString() + "}";
    }
}
