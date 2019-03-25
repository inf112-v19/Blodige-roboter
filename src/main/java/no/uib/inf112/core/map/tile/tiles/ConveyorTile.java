package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractMultiDirectionalActionTile;
import no.uib.inf112.core.map.tile.api.MoveableTile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class ConveyorTile extends AbstractMultiDirectionalActionTile<MoveableTile> {


    public ConveyorTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public void action(@NotNull MoveableTile tile) {
        //TODO
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotMoving();
    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return Collections.singletonList(Attribute.MOVEABLE);
    }

    @Override
    public String toString() {
        return "ConveyorTile{" + "dirs=" + getDirections() + "}";
    }
}
