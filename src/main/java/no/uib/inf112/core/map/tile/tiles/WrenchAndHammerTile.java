package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.BackupableTile;
import no.uib.inf112.core.map.tile.api.Cleanup;
import no.uib.inf112.core.map.tile.api.HealableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Elg
 */
public class WrenchAndHammerTile extends WrenchTile implements Cleanup<HealableTile> {

    public WrenchAndHammerTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public void clean(@NotNull HealableTile tile) {
        tile.heal(1);
    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Arrays.asList(BackupableTile.class, HealableTile.class);
    }
}
