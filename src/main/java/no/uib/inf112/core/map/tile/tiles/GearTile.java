package no.uib.inf112.core.map.tile.tiles;

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
public class GearTile extends AbstractRequirementTile implements ActionTile<SingleDirectionalTile> {

    private Direction rotation;

    public GearTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
        rotation = Direction.getDirectionsFromTile(this).iterator().next();
    }

    @Override
    public boolean action(@NotNull SingleDirectionalTile tile) {
        Direction orgRotation = tile.getDirection();
        tile.rotate(rotation);
        return orgRotation != tile.getDirection();
    }

    @Override
    public Sound getActionSound() {

        return Sound.conveyor;
    }

    @Nullable
    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Collections.singletonList(SingleDirectionalTile.class);
    }

    @Override
    public String toString() {
        return "GearTile{" +
                "rotation=" + rotation +
                '}';
    }
}
