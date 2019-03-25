package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractActionTile;
import no.uib.inf112.core.map.tile.api.MoveableTile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class DeathTile extends AbstractActionTile<MoveableTile> {

    public DeathTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public void action(@NotNull MoveableTile tile) {
        tile.kill();
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotFalling();
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Collections.singletonList(Attribute.MOVEABLE);
    }
}
