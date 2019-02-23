package no.uib.inf112.core.player;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TileType;
import org.jetbrains.annotations.NotNull;

public class Robot implements Entity {

    private Direction direction;
    private int x, y;
    boolean update;

    /**
     * @param x         The x position the player starts at
     * @param y         The y position the player starts at
     * @param direction What direction the player is facing on start
     * @param headless  True if you want player without graphics (e.g. for testing purposes), false otherwise
     * @throws IllegalArgumentException If the given position is out of bounds
     * @throws IllegalArgumentException If direction is {@code null}
     * @throws IllegalArgumentException If there is already an entity at the given {@code (x,y)}. See {@link MapHandler#addEntity(Entity)}
     * @throws IllegalStateException    If no {@link TiledMapTile} can be found
     */
    public Robot(int x, int y, Direction direction, boolean headless) {
        this.x = x;
        this.y = y;

        if (direction == null) {
            throw new IllegalArgumentException("Given direction can not be null");
        }
        this.direction = direction;

        if (!headless)
            GameGraphics.getRoboRally().getCurrentMap().addEntity(this);
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
    public void setDirection(@NotNull Direction direction) {
        //TODO Issue #46 rotate texture of robot ie visually show it
        this.direction = direction;
        update();
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
     */
    public void move(@NotNull Movement movement) {
        switch (movement) {
            case MOVE_1:
                move(direction.getDx(), direction.getDy());
                break;
            case MOVE_2:
                move(2 * direction.getDx(), 2 * direction.getDy());
                break;
            case MOVE_3:
                move(3 * direction.getDx(), 3 * direction.getDy());
                break;
            case BACK_UP:
                move(-1 * direction.getDx(), -1 * direction.getDy());
                break;
            case LEFT_TURN:
                setDirection(direction.turnLeft());
                break;
            case RIGHT_TURN:
                setDirection(direction.turnRight());
                break;
            case U_TURN:
                setDirection(direction.inverse());
                break;
            default:
                throw new IllegalArgumentException("Unknown movement " + movement.name());
        }
    }


    /**
     * Move the robot with given delta to new coordinates
     */
    private void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        if (GameGraphics.getRoboRally().getCurrentMap().isOutsideBoard(x, y)) {
            GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().kill();
            return;
        }
        update();
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
