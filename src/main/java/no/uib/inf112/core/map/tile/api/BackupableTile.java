package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

/**
 * A tile that has a backup
 *
 * @author Elg
 */
public interface BackupableTile extends Tile {

    /**
     * Use this to set the backup (as {@link Vector2Int} is mutable)
     *
     * @return The current backup
     */
    @NotNull
    Vector2Int getBackup();

    default Vector2Int getValidBackupSpawnpoint(MapHandler map) {
        Vector2Int backup = getBackup().clone();
        Tile bent = map.getTile(MapHandler.ENTITY_LAYER_NAME, backup.x, backup.y);
        if (bent == null || this.equals(bent)) {
            return backup; //we're already standing on our backup
        }

        for (int x = Math.max(backup.x - 1, 0); x < Math.min(backup.x + 2, map.getMapWidth()); x++) {
            for (int y = Math.max(backup.y - 1, 0); y < Math.min(backup.y + 2, map.getMapWidth()); y++) {
                Tile ent = map.getTile(MapHandler.ENTITY_LAYER_NAME, x, y);
                Tile bTile = map.getTile(MapHandler.BOARD_LAYER_NAME, x, y);
                if (ent == null && (bTile == null || bTile.getTileType() != TileType.VOID)) {
                    backup.x = x;
                    backup.y = y;
                    return backup;
                }
            }
        }
        //failed to find spawn...
        throw new IllegalStateException("Failed to find a valid backup spawn point");
    }

}
