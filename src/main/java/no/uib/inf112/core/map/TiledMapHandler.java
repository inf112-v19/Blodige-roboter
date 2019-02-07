package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


public class TiledMapHandler extends MapCamera {

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;

    private final TiledMapTileLayer boardLayer;
    private final TiledMapTileLayer entityLayer;


    //A map of all know entities and their last know location
    private Map<Entity, Vector2> entities;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;


    /**
     * @param map The relative path from assets folder to the Tiled map file
     * @throws IllegalArgumentException if max zoom is less than min zoom
     */
    public TiledMapHandler(String map) {
        super();

        try {
            TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
            params.textureMagFilter = Texture.TextureFilter.Linear;
            params.textureMinFilter = Texture.TextureFilter.Linear;
            tiledMap = new TmxMapLoader().load(map, params);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Failed to load map at '" + map + "'");
        }

        mapWidth = tiledMap.getProperties().get("width", int.class);
        mapHeight = tiledMap.getProperties().get("height", int.class);
        tileWidth = tiledMap.getProperties().get("tilewidth", int.class);
        tileHeight = tiledMap.getProperties().get("tileheight", int.class);


        TiledMapTileLayer baseLayer = null;
        try {
            baseLayer = (TiledMapTileLayer) tiledMap.getLayers().get(BOARD_LAYER_NAME);
        } catch (ClassCastException ignore) {
        }
        if (baseLayer == null) {
            throw new IllegalStateException("Given tiled map does not have a tile layer named '" + BOARD_LAYER_NAME + "'");
        }
        boardLayer = baseLayer;

        //create a new empty layer for all the robots to play on :)
        entityLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        tiledMap.getLayers().add(entityLayer);

        renderer = new OrthogonalTiledMapRenderer(tiledMap);

        //use a linked hashmap to make sure the iteration is consistent
        entities = new LinkedHashMap<>();

    }


    @Override
    public void render(@NotNull Batch batch) {
        getCamera().update();
        batch.setProjectionMatrix(getCamera().combined);
        renderer.setView(getCamera());
        renderer.render();
    }

    @Override
    public void update(float delta) {
        for (Map.Entry<Entity, Vector2> entry : entities.entrySet()) {

            //make sure the new x and y are always consistent
            int x = entry.getKey().getX();
            int y = entry.getKey().getY();
            Vector2 lastPos = entry.getValue();

            if (lastPos == null) {
                lastPos = new Vector2(x, y);
                entry.setValue(lastPos);
            } else if (x == lastPos.x && y == lastPos.y) {
                //do not update if there is no change
                continue;
            }

            entityLayer.setCell((int) lastPos.x, (int) lastPos.y, null);
            setEntityOnBoard(entry.getKey(), lastPos, x, y);

        }
    }


    @Override
    public MapProperties getProperties() {
        return tiledMap.getProperties();
    }

    @NotNull
    @Override
    public TiledMapTile getBoardLayerTile(int x, int y) {
        return boardLayer.getCell(x, y).getTile();
    }

    @Override
    @Nullable
    public Entity getEntity(int x, int y) {
        Vector2 v = new Vector2(x, y);
        for (Map.Entry<Entity, Vector2> entry : entities.entrySet()) {
            if (v.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @NotNull
    @Override
    public TiledMapTileSets getMapTileSets() {
        return tiledMap.getTileSets();
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        for (Entity knownRobot : getEntities()) {
            if (entity.getX() == knownRobot.getX() && entity.getY() == knownRobot.getY()) {
                throw new IllegalStateException("Cannot add an entity on top of another entity");
            }
        }
        entities.put(entity, null);
    }

    @Override
    public boolean removeEntity(Entity entity) {
        return entities.remove(entity) == null;
    }

    @NotNull
    @Override
    public Set<Entity> getEntities() {
        return entities.keySet();
    }

    @Override
    public boolean isOutsideBoard(int x, int y) {
        return x < 0 || x >= getMapWidth() || y < 0 | y >= getMapHeight();
    }

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public int getMapHeight() {
        return mapHeight;
    }

    @Override
    public void dispose() {
        renderer.dispose();
        tiledMap.dispose();
    }


    /**
     * Draw an entity on the entity layer
     *
     * @param entity The entity to draw
     * @param oldPos The last known position
     * @param x      The new x, provided as a parameter to make this thread safe
     * @param y      The new y, provided as a parameter to make this thread safe
     */
    private void setEntityOnBoard(@NotNull Entity entity, @NotNull Vector2 oldPos, int x, int y) {
        if (entity.getTile() == null) {
            return;
        }
        if (isOutsideBoard(x, y)) {
            throw new IllegalArgumentException(
                    "Given location (" + x + ", " + y + ") is out of bounds");
        }
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(entity.getTile());
        entityLayer.setCell(x, y, cell);


        Direction dir = entity.getDirection();

        float dx = x - oldPos.x;
        float dy = y - oldPos.y;
        if (dx > 0) {
            dir = Direction.EAST;
        } else if (dx < 0) {
            dir = Direction.WEST;
        } else {
            if (dy > 0) {
                dir = Direction.NORTH;
            } else if (dy < 0) {
                dir = Direction.SOUTH;
            }
        }
        entity.setDirection(dir);

        oldPos.x = x;
        oldPos.y = y;
    }
}