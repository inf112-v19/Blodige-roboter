package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractRequirementTile;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.SingleDirectionalTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class GearTile extends AbstractRequirementTile implements ActionTile<SingleDirectionalTile> {

    private Direction turnDir;

    public GearTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        turnDir = Direction.getDirectionsFromTile(this).iterator().next();
    }

    @Override
    public void action(@NotNull SingleDirectionalTile tile) {
        Direction dir = tile.getDirection();
        switch (turnDir) {
            case WEST:
                dir = dir.turnLeft();
                break;
            case EAST:
                dir = dir.turnRight();
                break;
            case SOUTH:
                dir = dir.inverse();
                break;
            case NORTH:
                break;
            default:
                throw new IllegalStateException("Unknown direction");
        }
        tile.setDirection(dir);
    }

    @Override
    public void playActionSound() {

    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Collections.singletonList(SingleDirectionalTile.class);
    }

    @Override
    public String toString() {
        return "GearTile{" +
                "turnDir=" + turnDir +
                '}';
    }
}
