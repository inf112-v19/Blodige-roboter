package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.GameGraphics;
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

    public ConveyorTile(Vector2Int pos, TileGraphic tg) {
        super(pos, tg);
        Set<Direction> tempDirs = Direction.getDirectionsFromTile(this);
        if (tempDirs.size() != 1) {
            System.out.println("WARN: ConveyorTile " + tg + " does not have one direction, but " + tempDirs.size());
            return;
        }
        dir = tempDirs.iterator().next();
    }

    @Override
    public void action(@NotNull MovableTile tile) {
        System.out.println("moving " + tile);
        if (dir == null) {
            System.out.println("Dir is null");
            return; //TODO remove this when all conveyors has gotten a direction
        }
        tile.move(dir.getDx(), dir.getDy(), 0);
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

    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Collections.singletonList(MovableTile.class);
    }

    @Override
    public String toString() {
        return "ConveyorTile{" + "dir=" + getDirection() + "}";
    }
}
