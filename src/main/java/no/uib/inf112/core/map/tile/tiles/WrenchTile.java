package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.BackupTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class WrenchTile extends AbstractTile<Void> {
    public WrenchTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public Void action(@NotNull Tile tile) {
        BackupTile bTile = (BackupTile) tile;
        bTile.getBackup().x = getX();
        bTile.getBackup().y = getY();
        return null;
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
