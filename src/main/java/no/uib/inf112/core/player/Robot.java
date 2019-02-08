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

        if (direction == null) throw new IllegalArgumentException("Given direction can not be null");
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
     * Move the robot with given delta to new coordinates
     * @param deltaX
     * @param deltaY
     */
    public void move(int deltaX, int deltaY) {
        //If sentence is while we are doing it simple as can be to avoid exception with click on the button
        //Fixme #44
        if(deltaX+x < 0) {
            this.x = 5;
            return;
        }
        this.x = x+deltaX;
        this.y = y+deltaY;

    }
}
