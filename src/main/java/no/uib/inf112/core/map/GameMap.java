package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class GameMap implements MapHandler {

    private TiledMap tiledMap;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer entityLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer collidablesLayer;

    //A map of all know entities and their last know location
    Map<Entity, Vector2Int> entities;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;

    public GameMap(String map) {
        try {
            TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
            params.textureMagFilter = Texture.TextureFilter.Linear;
            params.textureMinFilter = Texture.TextureFilter.Linear;
            tiledMap = new TmxMapLoader().load(map, params);
        } catch (final Exception e) {
            System.err.println("Failed to load map at '" + map + "'");
            throw e;
        }

        mapWidth = tiledMap.getProperties().get("width", int.class);
        mapHeight = tiledMap.getProperties().get("height", int.class);
        tileWidth = tiledMap.getProperties().get("tilewidth", int.class);
        tileHeight = tiledMap.getProperties().get("tileheight", int.class);

        TiledMapTileLayer baseLayer = null;
        TiledMapTileLayer collidables = null;
        TiledMapTileLayer flags = null;
        try {
            baseLayer = (TiledMapTileLayer) tiledMap.getLayers().get(BOARD_LAYER_NAME);
            collidables = (TiledMapTileLayer) tiledMap.getLayers().get(COLLIDABLES_LAYER_NAME);
            flags = (TiledMapTileLayer) tiledMap.getLayers().get(FLAG_LAYER_NAME);
        } catch (ClassCastException ignore) {
        }
        if (baseLayer == null) {
            throw new IllegalStateException("Given tiled map does not have a tile layer named '" + BOARD_LAYER_NAME + "'");
        }
        if (flags == null) {
            throw new IllegalStateException("Given tiled map does not have a tile layer named '" + FLAG_LAYER_NAME + "'");
        }
        if (collidables == null) {
            throw new IllegalStateException("Given tiled map does not have a tile layer named '" + COLLIDABLES_LAYER_NAME + "'");
        }

        collidablesLayer = collidables;
        flagLayer = flags;
        boardLayer = baseLayer;

        //create a new empty layer for all the robots to play on :)
        entityLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        tiledMap.getLayers().add(entityLayer);

        //use a linked hashmap to make sure the iteration is consistent
        entities = new LinkedHashMap<>();

    }

    /**
     * Used to create a skeleton maphandler for testing. Should be used as little as possible
     */
    public GameMap() {
        if (!GameGraphics.HEADLESS) {
            throw new IllegalStateException("Headless must be true when using this constructor");
        }
    }

    @Override
    public MapProperties getProperties() {
        return tiledMap.getProperties();
    }

    @NotNull
    @Override
    public TileType getBoardLayerTile(int x, int y) {
        int tileId = boardLayer.getCell(x, y).getTile().getId();
        return TileType.fromTiledId(tileId);
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
    @Nullable
    public Entity getEntity(int x, int y) {
        Vector2 v = new Vector2(x, y);
        for (Map.Entry<Entity, Vector2Int> entry : entities.entrySet()) {
            if (v.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
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

    protected TiledMapTileLayer getEntityLayer() {
        return entityLayer;
    }

    protected TiledMap getTiledMap() {
        return tiledMap;
    }


    @Override
    public int getTileHeight() {
        return tileHeight;
    }

    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    @Override
    public TileType getFlagLayerTile(int x, int y) throws NullPointerException {
        int tileId = flagLayer.getCell(x, y).getTile().getId();
        return TileType.fromTiledId(tileId);
    }

    @Override
    public TileType getCollidablesLayerTile(int x, int y) {
        int tileId = collidablesLayer.getCell(x, y).getTile().getId();
        return TileType.fromTiledId(tileId);
    }
}
