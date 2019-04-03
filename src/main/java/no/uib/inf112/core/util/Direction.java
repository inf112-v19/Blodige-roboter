package no.uib.inf112.core.util;

import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.api.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Elg
 */
@SuppressWarnings("Duplicates")
public enum Direction {

    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    ;


    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    /**
     * The input will be run through {@link Math#signum(float)}, so any integer as long as one of them is 0 will work
     *
     * @param dx A delta in the x direction
     * @param dy A delta in the x direction
     * @return Direction from delta values
     * @throws IllegalArgumentException if the given arguments does not match any direction
     */
    public static Direction fromDelta(int dx, int dy) {
        dx = (int) Math.signum(dx);
        dy = (int) Math.signum(dy);
        if (dx == NORTH.dx && dy == NORTH.dy) {
            return NORTH;
        } else if (dx == EAST.dx && dy == EAST.dy) {
            return EAST;
        } else if (dx == WEST.dx && dy == WEST.dy) {
            return WEST;
        } else if (dx == SOUTH.dx && dy == SOUTH.dy) {
            return SOUTH;
        }
        throw new IllegalArgumentException("Unknown direction (" + dx + ", " + dy + ")");
    }

    /**
     * @return The opposite direction of this one
     */
    public Direction inverse() {
        switch (this) {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            case SOUTH:
                return NORTH;
            default:
                throw new IllegalStateException("Unknown direction " + name());
        }
    }

    /**
     * @return The relative direction to the right
     */
    public Direction turnLeft() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            default:
                throw new IllegalStateException("Unknown direction " + name());
        }
    }

    /**
     * @return The relative direction to the left
     */
    public Direction turnRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case WEST:
                return NORTH;
            case SOUTH:
                return WEST;
            default:
                throw new IllegalStateException("Unknown direction " + name());
        }
    }

    /**
     * @param tile The tile to get the directions of
     * @return An unmodifiable set of all direction this tile has
     */
    @NotNull
    public static Set<Direction> getDirectionsFromTile(@NotNull Tile tile) {
        Set<Direction> tempDirs = new HashSet<>();

        if (tile.hasAttribute(Attribute.DIR_NORTH)) {
            tempDirs.add(Direction.NORTH);
        }
        if (tile.hasAttribute(Attribute.DIR_EAST)) {
            tempDirs.add(Direction.EAST);
        }
        if (tile.hasAttribute(Attribute.DIR_SOUTH)) {
            tempDirs.add(Direction.SOUTH);
        }
        if (tile.hasAttribute(Attribute.DIR_WEST)) {
            tempDirs.add(Direction.WEST);
        }
        return Collections.unmodifiableSet(tempDirs);
    }

}
