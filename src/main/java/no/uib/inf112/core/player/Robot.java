package no.uib.inf112.core.player;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.map.MapHandler;
import org.jetbrains.annotations.NotNull;

public class Robot implements Entity {

    private final TiledMapTile tile;
    private Direction direction;
    private int x, y;

    /**
     * @param x
     *     The x position the player starts at
     * @param y
     *     The y position the player starts at
     * @param direction
     *     What direction the player is facing on start
     *
     * @throws IllegalArgumentException
     *     If the given position is out of bounds
     * @throws IllegalArgumentException
     *     If direction is {@code null}
     * @throws IllegalArgumentException
     *     If there is already an entity at the given {@code (x,y)}. See {@link MapHandler#addEntity(Entity)}
     * @throws IllegalStateException
     *     If no {@link TiledMapTile} can be found
     */
    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;

        if (direction == null) { throw new IllegalArgumentException("Given direction can not be null"); }
        this.direction = direction;

        //TODO #Issue 32: make this standardized (as parameters? as constants?)
        tile = RoboRally.getCurrentMap().getMapTileSets().getTileSet("player_tileset").getTile(106);
        if (tile == null) {
            throw new IllegalStateException("Failed to find robot tile");
        }
        RoboRally.getCurrentMap().addEntity(this);
    }

    @Override
    public TiledMapTile getTile() {
        return tile;
    }

    @NotNull
    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void setDirection(@NotNull Direction direction) {
        //TODO rotate texture of robot ie visually show it
        this.direction = direction;
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
     * @param movement
     *     how to move
     */
    public void move(Movement movement) {
        //TODO move according to card
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
                setDirection(direction.inverse());
                break;
            case LEFT_TURN:
                setDirection(direction.left());
                move(direction.getDx(), direction.getDy());
                break;
            case RIGHT_TURN:
                setDirection(direction.right());
                move(direction.getDx(), direction.getDy());
                break;
            default:
                throw new IllegalArgumentException("Unknown movement " + movement.name());
        }
    }

    /**
     * Move the robot with given delta to new coordinates
     */
    private void move(int deltaX, int deltaY) {
        this.x = x + deltaX;
        this.y = y + deltaY;
    }

    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
