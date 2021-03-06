package no.uib.inf112.core.map;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Elg
 */
public interface MapHandler {

    float DEFAULT_ZOOM_SENSITIVITY = 1f;
    float DEFAULT_MAX_ZOOM = 10f;
    float DEFAULT_MIN_ZOOM = 2f;

    int ZOOM_IN = -1;
    int ZOOM_OUT = 1;


    String ZOOM_SENSITIVITY_PATH = "zoomsensitivity";
    String MAX_ZOOM_PATH = "maxzoom";
    String MIN_ZOOM_PATH = "minzoom";


    //The layer name of the board it self, this layer should never be modified
    String BOARD_LAYER_NAME = "board";
    String ENTITY_LAYER_NAME = "entities";
    String ENITTY_LASER_LAYER_NAME = "entitylasers";
    String LASERS_LAYER_NAME = "lasers";
    String COLLIDABLES_LAYER_NAME = "collidables";
    String FLAG_LAYER_NAME = "flags";

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
     * @return The camera that render the board
     */
    OrthographicCamera getCamera();

    /**
     * @return Properties of the current map
     */
    MapProperties getProperties();

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
    void removeEntity(@Nullable Entity entity);

    /**
     * Adds an laserTile to the laserEntities layer, if there is one there currently it creates a cross tile if they have different orientation, otherwise it ignores it.
     *
     * @param laser laserTile to add on the map
     */
    void addEntityLaser(@NotNull Tile laser);

    /**
     * @param x The x coordinate to test
     * @param y The y coordinate to test
     * @return If the given {@code x} and {@code y} is outside this map
     */
    boolean isOutsideBoard(int x, int y);

    /**
     * @return How many tiles there are horizontally in the map
     */
    int getMapWidth();

    /**
     * @return How many tiles there are vertically in the map
     */
    int getMapHeight();

    /**
     * @return The width, in pixes, of each tile
     */
    int getTileWidth();

    /**
     * @return The height, in pixes, of each tile
     */
    int getTileHeight();

    /**
     * The application window has been resized
     */
    void resize(int width, int height);

    /**
     * @param layer The name of the layer
     * @return The corresponding layer to this tile
     */
    @Nullable
    TiledMapTileLayer getLayer(@NotNull String layer);

    /**
     * @param layerName The name of the layer
     * @param x
     * @param y
     * @return The tile at the given location and tile
     */
    @Nullable
    Tile getTile(@NotNull String layerName, int x, int y);

    /**
     * @return The tile at the given location and tile
     */
    @Nullable
    Tile getTile(@NotNull TiledMapTileLayer layer, int x, int y);

    /**
     * @return A list of all tiles on a given location
     */
    @NotNull
    List<Tile> getAllTiles(int x, int y);

    void removeEntityLasers();
}
