package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractActionTile;
import no.uib.inf112.core.map.tile.api.BackupTile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class WrenchTile extends AbstractActionTile<BackupTile> {

    public WrenchTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public void action(@NotNull BackupTile tile) {
        tile.getBackup().x = getX();
        tile.getBackup().y = getY();
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotUpdatesBackup();
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Collections.singletonList(Attribute.BACKUPABLE);
    }
}
