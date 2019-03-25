package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractActionTile;
import no.uib.inf112.core.map.tile.api.BackupTile;
import no.uib.inf112.core.map.tile.api.HealableTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.AbstractPlayer;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Elg
 */
public class WrenchAndHammerTile extends AbstractActionTile {

    public WrenchAndHammerTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public void action(@NotNull Tile tile) {
        HealableTile healableTile = (HealableTile) tile;
        healableTile.heal(AbstractPlayer.MAX_HEALTH);

        BackupTile bTile = (BackupTile) tile;
        bTile.getBackup().x = getX();
        bTile.getBackup().y = getY();
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotUpdatesBackup();
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Arrays.asList(Attribute.BACKUPABLE, Attribute.HEALABLE);
    }
}
