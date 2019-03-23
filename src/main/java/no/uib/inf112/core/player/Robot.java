package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Robot extends AbstractTile<Vector2Int> implements Entity<Vector2Int> {

    private Direction direction;
    private boolean update;
    private Color color;
    private Vector2Int pos;

    /**
     * @param pos       The position the player starts at
     * @param direction What direction the player is facing on start
     * @throws IllegalArgumentException If the given position is out of bounds
     * @throws IllegalArgumentException If direction is {@code null}
     * @throws IllegalArgumentException If there is already an entity at the given {@code (x,y)}. See {@link MapHandler#addEntity(Entity)}
     * @throws IllegalStateException    If no {@link TiledMapTile} can be found
     */
    public Robot(Vector2Int pos, Direction direction, Color color) {
        super(pos, TileGraphic.ROBOT_TILE_NORTH);
        this.color = color;
        this.pos = pos;

        if (direction == null) {
            throw new IllegalArgumentException("Given direction can not be null");
        }
        this.direction = direction;
    }

    @NotNull
    @Override
    public TiledMapTile getTile() {
        switch (direction) {
            case NORTH:
                return TileGraphic.ROBOT_TILE_NORTH.getTile();
            case EAST:
                return TileGraphic.ROBOT_TILE_EAST.getTile();
            case WEST:
                return TileGraphic.ROBOT_TILE_WEST.getTile();
            case SOUTH:
                return TileGraphic.ROBOT_TILE_SOUTH.getTile();
            default:
                throw new IllegalStateException("No robot tile for direction " + direction);
        }
    }

    @Nullable
    @Override
    public Vector2Int action(@NotNull Tile tile) {
        //return the potential position when next player movement card is used now
        return null; //// TODO: 23.03.2019 all this
    }

    @Override
    public void playActionSound() {

    }

    @Nullable
    @Override
    public List<Attribute> requiredAttributes() {
        return null;
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(@NotNull Direction direction) {
        this.direction = direction;
        update();
    }

    /**
     * Move the robot by the given movement card
     *
     * @param movement how to move
     * @return false if the robot moved out of the map
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
     *
     * @return false if the robot moved out of the map
     */
    @Override
    public void move(int deltaX, int deltaY) {
        if (GameGraphics.getRoboRally().getCurrentMap().isOutsideBoard(pos.x + deltaX, pos.y + deltaY)) {
            kill();
            update();
            return;
        }
        pos.x += deltaX;
        pos.y += deltaY;
        GameGraphics.getSoundPlayer().playRobotMoving();
        update();
    }

    @Override
    public boolean canMove(int x, int y) {
        return false;
    }

    @Override
    public void teleport(int x, int y) {
        if (GameGraphics.getRoboRally().getCurrentMap().isOutsideBoard(x, y)) {
            throw new IllegalArgumentException("Cannot teleport outside the map bounds. Tried to teleport to (" + x + ", " + y + ")");
        }
        pos.x = x;
        pos.y = y;
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

    @NotNull
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(@NotNull Color color) {
        this.color = color;
    }
}
