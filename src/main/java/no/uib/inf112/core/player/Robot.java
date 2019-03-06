package no.uib.inf112.core.player;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TileType;
import no.uib.inf112.core.map.cards.Movement;
import org.jetbrains.annotations.NotNull;

public class Robot implements Entity {

    private Direction direction;
    private int x, y;
    boolean update;

    /**
     * @param x         The x position the player starts at
     * @param y         The y position the player starts at
     * @param direction What direction the player is facing on start
     * @throws IllegalArgumentException If the given position is out of bounds
     * @throws IllegalArgumentException If direction is {@code null}
     * @throws IllegalArgumentException If there is already an entity at the given {@code (x,y)}. See {@link MapHandler#addEntity(Entity)}
     * @throws IllegalStateException    If no {@link TiledMapTile} can be found
     */
    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;

        if (direction == null) {
            throw new IllegalArgumentException("Given direction can not be null");
        }
        this.direction = direction;
    }

    @Override
    public TileType getTileType() {
        switch (direction) {
            case NORTH:
                return TileType.ROBOT_TILE_NORTH;
            case EAST:
                return TileType.ROBOT_TILE_EAST;
            case WEST:
                return TileType.ROBOT_TILE_WEST;
            case SOUTH:
                return TileType.ROBOT_TILE_SOUTH;
            default:
                throw new IllegalStateException("No robot tile for direction " + direction);
        }
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean setDirection(@NotNull Direction direction) {
        //TODO Issue #46 rotate texture of robot ie visually show it
        this.direction = direction;
        update();
        return true;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    /**
     * Move the robot by the given movement card
     *
     * @param movement how to move
     * @return false if the robot moved out of the map
     */
    public boolean move(@NotNull Movement movement) {
        switch (movement) {
            case MOVE_1:
                return move(direction.getDx(), direction.getDy());
            case MOVE_2:
                return move(2 * direction.getDx(), 2 * direction.getDy());
            case MOVE_3:
                return move(3 * direction.getDx(), 3 * direction.getDy());
            case BACK_UP:
                return move(-1 * direction.getDx(), -1 * direction.getDy());
            case LEFT_TURN:
                return setDirection(direction.turnLeft());
            case RIGHT_TURN:
                return setDirection(direction.turnRight());
            case U_TURN:
                return setDirection(direction.inverse());
            default:
                throw new IllegalArgumentException("Unknown movement " + movement.name());
        }
    }


    /**
     * Move the robot with given delta to new coordinates
     *
     * @return false if the robot moved out of the map
     */
    private boolean move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        if (GameGraphics.getRoboRally().getCurrentMap().isOutsideBoard(x, y)) {
            return false;
        }
        update();
        return true;
    }

    public void teleport(int x, int y) {
        if (GameGraphics.getRoboRally().getCurrentMap().isOutsideBoard(x, y)) {
            throw new IllegalArgumentException("Cannot teleport outside the map bounds. Tried to teleport to (" + x + ", " + y + ")");
        }
        this.x = x;
        this.y = y;
        update();
    }

    @Override
    public boolean shouldUpdate() {
        return update;
    }

    @Override
    public void update(boolean update) {
        this.update = update;
    }
}
