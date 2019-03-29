package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.*;
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
    @Nullable
    private Direction rotation;

    public ConveyorTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        Set<Direction> tempDirs = Direction.getDirectionsFromTile(this);
        if (tempDirs.size() != 1) {
            throw new IllegalStateException("Given tileTypes '" + tg + "' does not have exactly one direction, but " + tempDirs.size());
        }
        dir = tempDirs.iterator().next();

        if (tg.name().contains("ROTATE")) {
            String[] name = tg.name().split("_");
            rotation = Direction.valueOf(name[name.length - 1]);
        }
    }

    @Override
    public void action(@NotNull MovableTile tile) {
        tile.move(dir.getDx(), dir.getDy(), 0);
        if (rotation != null) {
            tile.rotate(rotation);
        }
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotMoving();
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return dir;
    }

    @Override
    public void setDirection(@NotNull Direction direction) {
        //The directions of a conveyor cannot be changed
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
