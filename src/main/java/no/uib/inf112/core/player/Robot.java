package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.*;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.Direction;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

public abstract class Robot extends AbstractRequirementTile implements Entity {

    private boolean stopMoving = false;
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
    public void move(@NotNull Movement movement, int maxTime) {
        switch (movement) {
            case MOVE_1:
                move(direction.getDx(), direction.getDy(), maxTime);
                break;
            case MOVE_2:
                move(2 * direction.getDx(), 2 * direction.getDy(), maxTime);
                break;
            case MOVE_3:
                move(3 * direction.getDx(), 3 * direction.getDy(), maxTime);
                break;
            case BACK_UP:
                move(-1 * direction.getDx(), -1 * direction.getDy(), maxTime);
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
    @Override
    public void move(int dx, int dy, int maxTime) {
        stopMoving = false;
        if (dx == 0 && dy == 0) {
            return;
        }
        Direction dir = Direction.fromDelta(dx, dy);


        int sdx = dir.getDx();
        int sdy = dir.getDy();

        int max = Math.max(Math.abs(dx), Math.abs(dy));
        int maxTimePerMovement =
                Math.round((maxTime * 1f) / max);

        if (move(dir) && !stopMoving) {
            update();
            Sound.ROBOT_MOVING.play();
            if (dx - sdx != 0 || dy - sdy != 0) {
                GameGraphics.scheduleSync(() -> move(dx - sdx, dy - sdy, maxTime - maxTimePerMovement), maxTimePerMovement);
            }
        }
    }

    private boolean push(MovableTile mTile, Direction dir) {
        if (mTile.move(dir)) {
            ((Entity) mTile).update();
            GameGraphics.getRoboRally().getCurrentMap().update(0);
            move(dir);
            return true;
        }
        return false;
    }

    @Override
    public boolean move(@NotNull Direction dir) {
        int dx = dir.getDx();
        int dy = dir.getDy();

        if (willCollide(this, 0, 0, dir)) {
            return false;
        }

        // Robot walks out of map
        if (GameGraphics.getRoboRally().getCurrentMap().isOutsideBoard(pos.x + dx, pos.y + dy)) {
            kill();
            update();
            Sound.ROBOT_FALLING.play();
            stopMoving();
            return true;
        }

        if (!willCollide(this, dx, dy, dir)) {
            pos.x += dx;
            pos.y += dy;
            update();
            for (Tile tile : GameGraphics.getRoboRally().getCurrentMap().getAllTiles(pos.x, pos.y)) {
                if (tile.hasAttribute(Attribute.ACTIVE_ONLY_ON_STEP)) {
                    ActionTile cTile = (ActionTile) tile;
                    if (cTile.canDoAction(this)) {
                        //noinspection unchecked checked in if
                        cTile.action(this);
                    }
                }
            }
            return true;
        } else {
            MovableTile toBePushed = null;
            for (Tile tile : GameGraphics.getRoboRally().getCurrentMap().getAllTiles(pos.x + dx, pos.y + dy)) {
                if (tile.hasAttribute(Attribute.PUSHABLE)) {
                    if (toBePushed == null) {
                        toBePushed = (MovableTile) tile;
                    } else {
                        throw new IllegalStateException("Expected one pushable on a position, found two");
                    }
                }
            }
            return toBePushed != null && push(toBePushed, dir);
        }
    }


    private boolean willCollide(MovableTile mTile, int dx, int dy, Direction dir) {
        int x = mTile.getX() + dx;
        int y = mTile.getY() + dy;

        for (Tile tile : GameGraphics.getRoboRally().getCurrentMap().getAllTiles(x, y)) {
            if (tile.hasSuperClass(CollidableTile.class) && !equals(tile)) {
                CollidableTile cTile = (CollidableTile) tile;
                if (cTile.willCollide(this, dir)) {
                    return true;
                }
            }
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Robot)) {
            return false;
        }
        Robot robot = (Robot) o;

        if (direction != robot.direction) {
            return false;
        }
        return pos.equals(robot.pos);
    }

    @Override
    public int hashCode() {
        int result = direction.hashCode();
        result = 31 * result + pos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "pos=" + pos +
                ", direction=" + direction +
                ", color=" + color +
                '}';
    }

    @Override
    public void stopMoving() {
        stopMoving = true;
    }
}
