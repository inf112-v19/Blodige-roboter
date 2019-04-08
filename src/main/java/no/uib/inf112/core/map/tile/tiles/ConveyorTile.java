package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Elg
 */
public class ConveyorTile extends AbstractRequirementTile implements ActionTile<MovableTile>, SingleDirectionalTile {

    private Direction dir;

    public ConveyorTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        Set<Direction> tempDirs = Direction.getDirectionsFromTile(this);
        if (tempDirs.size() != 1) {
            throw new IllegalStateException("Given tileTypes '" + tg + "' does not have exactly one direction, but " + tempDirs.size());
        }
        dir = tempDirs.iterator().next();
    }

    @Override
    public boolean action(@NotNull MovableTile tile) {
        tile.move(dir.getDx(), dir.getDy(), 0);
        return true;
    }

    @Override
    public Sound getActionSound() {
        return Sound.CONVEYOR;
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return dir;
    }

    @Override
    public void setDirection(@NotNull Direction direction) {
        //The directions of a CONVEYOR cannot be changed
    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Collections.singletonList(MovableTile.class);
    }

    @Override
    public String toString() {
        return "ConveyorTile{dir=" + getDirection() + " express= " + hasAttribute(Attribute.HIGH_PRIORITY) + "}";
    }
}
