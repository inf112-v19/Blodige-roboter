package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface BackupTile extends Tile {

    /**
     * Use this to set the backup (as {@link Vector2Int} is mutable)
     *
     * @return The current backup vector
     */
    @NotNull
    Vector2Int getBackup();
}
