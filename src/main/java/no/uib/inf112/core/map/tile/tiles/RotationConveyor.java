package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.ConditionalRotateEffectTile;
import no.uib.inf112.core.map.tile.api.MovableTile;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import static no.uib.inf112.core.map.tile.Attribute.*;

/**
 * A conveyor which conditionally rotates a tile standing on it
 *
 * @author kristian
 */
public class RotationConveyor extends ConveyorTile implements ConditionalRotateEffectTile {

    public RotationConveyor(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);
    }

    @Override
    public boolean rotate(@NotNull MovableTile tile, Vector2Int prevPos) {
        if (hasAttribute(Attribute.LEFT)) {
            return tile.setDirection(tile.getDirection().turnLeft());
        } else if (hasAttribute(Attribute.RIGHT)) {
            return tile.setDirection(tile.getDirection().turnRight());
        } else {
            int dx = tile.getX() - prevPos.x;
            int dy = tile.getY() - prevPos.y;

            Direction dir = Direction.fromDelta(dx, dy).inverse();
            if (dir == null) {
                return false;
            }
            switch (dir) {
                case NORTH:
                    if (hasAttribute(NORTH_WEST)) {
                        return tile.setDirection(tile.getDirection().turnLeft());
                    } else if (hasAttribute(NORTH_EAST)) {
                        return tile.setDirection(tile.getDirection().turnRight());
                    }
                    break;
                case EAST:
                    if (hasAttribute(Attribute.EAST_SOUTH)) {
                        return tile.setDirection(tile.getDirection().turnLeft());
                    } else if (hasAttribute(Attribute.EAST_NORTH)) {
                        return tile.setDirection(tile.getDirection().turnRight());
                    }
                    break;
                case SOUTH:
                    if (hasAttribute(SOUTH_WEST)) {
                        return tile.setDirection(tile.getDirection().turnLeft());
                    } else if (hasAttribute(Attribute.SOUTH_EAST)) {
                        return tile.setDirection(tile.getDirection().turnRight());
                    }
                    break;
                case WEST:
                    if (hasAttribute(WEST_NORTH)) {
                        return tile.setDirection(tile.getDirection().turnLeft());
                    } else if (hasAttribute(WEST_SOUTH)) {
                        return tile.setDirection(tile.getDirection().turnRight());
                    }
                    break;
            }
            return false;
        }
    }
}
