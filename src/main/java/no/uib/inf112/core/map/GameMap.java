package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;
import no.uib.inf112.desktop.Main;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class GameMap implements MapHandler{

    private TiledMap tiledMap;

    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer entityLayer;


    //A map of all know entities and their last know location
    private Map<Entity, Vector2Int> entities;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;

    //The layer name of the board it self, this layer should never be modified
    String BOARD_LAYER_NAME = "board";

    public GameMap(String map){
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

        //use a linked hashmap to make sure the iteration is consistent
        entities = new LinkedHashMap<>();

    }

    public GameMap() {
        if (!Main.HEADLESS) {
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

    @NotNull
    @Override
    public TiledMapTileSets getMapTileSets() {
        return tiledMap.getTileSets();
    }

    @Override
    public void addEntity(@NotNull Entity entity) {
        for (Entity knownRobot : getEntities().keySet()) {
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
    public Map<Entity, Vector2Int> getEntities() {
        return entities;
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

    protected  TiledMapTileLayer getEntityLayer(){
        return entityLayer;
    }

    protected  TiledMap getTiledMap(){
        return tiledMap;
    }
}
