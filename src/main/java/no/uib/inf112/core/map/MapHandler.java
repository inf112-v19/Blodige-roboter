package no.uib.inf112.core.map;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import no.uib.inf112.core.player.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author Elg
 */
public interface MapHandler {

    //constants for
    float DEFAULT_ZOOM_SENSITIVITY = 1f;
    float DEFAULT_MAX_ZOOM = 10f;
    float DEFAULT_MIN_ZOOM = 1f;

    String ZOOM_SENSITIVITY_PATH = "zoomsensitivity";
    String MAX_ZOOM_PATH = "maxzoom";
    String MIN_ZOOM_PATH = "minzoom";


    //The layer name of the board it self, this layer should never be modified
    String BOARD_LAYER_NAME = "board";

    /**
     * Render the map and it's content
     */
    void render(@NotNull Batch batch);

    /**
     * Update the the map and the locations of all entities known to this map
     *
     * @param delta How long since the last frame (see {@link Graphics#getDeltaTime()}
     */
    void update(float delta);


    /**
     * Zoom in or out of the map
     *
     * @param direction The direction to zoom, if not 1 or -1 {@link Math#signum(float)} is used to get the direction
     */
    void zoomCamera(int direction);


    /**
     * Move the camera around the map
     */
    void moveCamera(float dx, float dy);

    /**
     * @return The current position of the board camera
     */
    OrthographicCamera getCamera();

    MapProperties getProperties();

    /**
     * @return The board tile at the given {@code (x, y)} coordinate
     * @throws IllegalArgumentException if the given given {@code (x, y)} coordinate is outside the map
     */
    @NotNull
    TileType getBoardLayerTile(int x, int y);

    /**
     * @return The entity at the given {@code (x, y)} coordinate or {@code null} if there is nothing there
     */
    @Nullable
    Entity getEntity(int x, int y);

    /**
     * @return The set of all tiles in this map
     */
    @NotNull
    TiledMapTileSets getMapTileSets();

    /**
     * Add a robot to the list of robots to render
     *
     * @param entity The entity to add
     * @throws IllegalArgumentException If the the x or y is out of bounds
     * @throws IllegalStateException    If the added entity has the name coordinates as another robot on the map
     */
    void addEntity(@NotNull Entity entity);

    /**
     * Remove a robot from the map
     *
     * @param entity The robot to remove
     * @return If the robot was successfully removed
     */
    boolean removeEntity(@Nullable Entity entity);

    /**
     * @return A read-only set of known robots in the order they were added
     */
    @NotNull
    Set<Entity> getEntities();

    /**
     * @param x The x coordinate to test
     * @param y The y coordinate to test
     * @return If the given {@code x} and {@code y} is outside this map
     */
    boolean isOutsideBoard(int x, int y);

    /**
     * @return How many tiles there are in the maps width
     */
    int getMapWidth();

    /**
     * @return How many tiles there are in the maps height
     */
    int getMapHeight();

    /**
     * The application window has been resized
     */
    void resize();
}
