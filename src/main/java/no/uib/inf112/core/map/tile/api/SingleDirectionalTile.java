package no.uib.inf112.core.map.tile.api;

import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * A tile that has a direction, but it is exactly one direction (not the boy band).
 *
 * @author Elg
 */
public interface SingleDirectionalTile extends Tile {

    /**
     * @return The current direction of this tile
     */
    @NotNull
    Direction getDirection();

    /**
     * @param direction The new direction of the tile
     */
    void setDirection(@NotNull Direction direction);

    default void rotate(@NotNull Direction direction) {
        Direction dir = direction;
        switch (direction) {
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
                throw new IllegalStateException("Unknown direction " + direction);
        }
        setDirection(dir);
    }

}
