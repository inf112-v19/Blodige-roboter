package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.DockableTile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

public class SpawnTile extends AbstractTile implements DockableTile {

    private final int spawnNr;
    private final TileGraphic tg;

    public SpawnTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        this.tg = tg;
        spawnNr = Integer.parseInt(tg.name().replace("SPAWN", ""));
    }

    @Override
    public int getSpawnNumber() {
        return spawnNr;
    }

    @Override
    public String toString() {
        return "SpawnTile{" + tg.name() + "}";
    }
}
