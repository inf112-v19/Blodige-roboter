package no.uib.inf112.core.map.tile.tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractRequirementTile;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.SingleDirectionalTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class GearTile extends AbstractRequirementTile implements ActionTile<SingleDirectionalTile>, SingleDirectionalTile {

    private Direction rotation;

    public GearTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        rotation = Direction.getDirectionsFromTile(this).iterator().next();
    }

    @Override
    public boolean action(@NotNull SingleDirectionalTile tile) {
        Direction orgRotation = tile.getDirection();
        Direction newDirection;
        switch (rotation) {
            case NORTH:
                newDirection = orgRotation;
                break;
            case EAST:
                newDirection = orgRotation.turnRight();
                break;
            case SOUTH:
                newDirection = orgRotation.inverse();
                break;
            case WEST:
                newDirection = orgRotation.turnLeft();
                break;
            default:
                throw new IllegalArgumentException("Unknown direction");
        }
        tile.setDirection(newDirection);
        return orgRotation != tile.getDirection();
    }

    @NotNull
    @Override
    public Sound getActionSound() {

        return Sound.CONVEYOR;
    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Collections.singletonList(SingleDirectionalTile.class);
    }

    @NotNull
    @Override
    public TiledMapTile getTile() {
        //This will sadly not update the graphic, so this could be fixed in the future
        switch (rotation) {
            case WEST:
                return TileGraphic.ROTATE_COUNTERCLOCKWISE.getTile();
            case EAST:
                return TileGraphic.ROTATE_CLOCKWISE.getTile();
            default:
                throw new IllegalStateException("Cannot display gear that rotates in the direction " + rotation);
        }
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return rotation;
    }

    @Override
    public boolean setDirection(@NotNull Direction direction) {
        if (direction != Direction.WEST && direction != Direction.EAST) {
            throw new IllegalArgumentException("Gears can only spin in the WEST and EAST directions.");
        }
        this.rotation = direction;
        return true;
    }

    @Override
    public String toString() {
        return "GearTile{" + "rotation=" + rotation + '}';
    }
}
