package no.uib.inf112.core.util;

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

}
