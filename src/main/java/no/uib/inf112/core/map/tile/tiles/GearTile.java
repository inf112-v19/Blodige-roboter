package no.uib.inf112.core.map.tile.tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;
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
public class GearTile extends AbstractRequirementTile implements ActionTile<SingleDirectionalTile>, SingleDirectionalTile {

    private Direction rotation;

    public GearTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        rotation = Direction.getDirectionsFromTile(this).iterator().next();
    }

    @Override
    public void action(@NotNull SingleDirectionalTile tile) {
        tile.rotate(rotation);
    }

    @Override
    public void playActionSound() {
        GameGraphics.getSoundPlayer().playRobotMoving();
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
    public void setDirection(@NotNull Direction direction) {
        if (direction != Direction.WEST && direction != Direction.EAST) {
            throw new IllegalArgumentException("Gears can only spin in the WEST and EAST directions.");
        }
        this.rotation = direction;
    }

    @Override
    public String toString() {
        return "GearTile{" + "rotation=" + rotation + '}';
    }
}
