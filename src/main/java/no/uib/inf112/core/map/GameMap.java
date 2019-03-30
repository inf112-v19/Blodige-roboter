package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tile.Attribute;
import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.map.tile.tiles.LaserTile;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import static no.uib.inf112.core.map.tile.TileGraphic.LASER_HORIZONTAL;
import static no.uib.inf112.core.map.tile.TileGraphic.LASER_VERTICAL;

public abstract class GameMap implements MapHandler {

    private TiledMap tiledMap;

    private TiledMapTileLayer entityLayer;
    private TiledMapTileLayer entityLaserLayer;

    //A map of all know entities and their last know location
    Map<Entity, Vector2Int> entities;
    Map<LaserTile, Vector2Int> entityLasers;
    private Map<TiledMapTileLayer, Tile[][]> tiles = new HashMap<>();

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
        TiledMapTileLayer lasers = null;
        TiledMapTileLayer collidables = null;
        TiledMapTileLayer flags = null;
        try {
            baseLayer = (TiledMapTileLayer) tiledMap.getLayers().get(BOARD_LAYER_NAME);
            lasers = (TiledMapTileLayer) tiledMap.getLayers().get(LASERS_LAYER_NAME);
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
        if (lasers == null) {
            throw new IllegalStateException("Given tiled map does not have a tile layer named '" + LASERS_LAYER_NAME + "'");
        }

        TiledMapTileLayer collidablesLayer = collidables;
        TiledMapTileLayer flagLayer = flags;
        TiledMapTileLayer boardLayer = baseLayer;
        TiledMapTileLayer laserLayer = lasers;

        //create a two new empty layer for all the robots to play on, one for enity and one for their laser trace
        entityLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        tiledMap.getLayers().add(entityLayer);
        entityLaserLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        tiledMap.getLayers().add(entityLaserLayer);

        tiles = new HashMap<>();
        tiles.put(boardLayer, new Tile[mapWidth][mapHeight]);
        tiles.put(laserLayer, new Tile[mapWidth][mapHeight]);
        tiles.put(entityLaserLayer, null);
        tiles.put(entityLayer, null);
        tiles.put(collidablesLayer, new Tile[mapWidth][mapHeight]);
        tiles.put(flagLayer, new Tile[mapWidth][mapHeight]);


        //use a linked hashmap to make sure the iteration is consistent
        entities = new LinkedHashMap<>();
        entityLasers = new LinkedHashMap<>();
    }

    /**
     * Used to create a skeleton maphandler for testing. Should be used as little as possible
     */
    GameMap() {
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
    public boolean isOutsideBoard(int x, int y) {
        return x < 0 || x >= getMapWidth() || y < 0 | y >= getMapHeight();
    }

    //FIXME this should be tested
    @Override
    public boolean removeEntity(Entity entity) {
        entityLayer.setCell(entity.getX(), entity.getY(), null);
        return entities.remove(entity) == null;
    }

    @Override
    public void addEntityLaser(LaserTile laser) {
        for (LaserTile knownLaser : getLaserEntities()) {
            if (laser.getX() == knownLaser.getX() && laser.getY() == knownLaser.getY()) {
                removeEntityLaser(knownLaser);
                entityLasers.put(new LaserTile(new Vector2Int(knownLaser.getX(), knownLaser.getY()), TileGraphic.LASER_CROSS), null);
            }
        }
        entityLasers.put(laser, null);
    }

    @Override
    public boolean removeEntityLaser(LaserTile entityLaser) {
        if (getTile(LASERS_LAYER_NAME, entityLaser.getX(), entityLaser.getY()).getTile().getId() == TileGraphic.LASER_CROSS.getId()) {
            entityLaserLayer.setCell(entityLaser.getX(), entityLaser.getY(), null);
            Iterator<LaserTile> iterator = entityLasers.keySet().iterator();
            while (iterator.hasNext()) {
                LaserTile laserTile = iterator.next();
                if (laserTile.getX() == entityLaser.getX() && laserTile.getY() == entityLaser.getY()) {
                    iterator.remove();
                    entityLasers.remove(laserTile);
                    entityLaserLayer.setCell(entityLaser.getX(), entityLaser.getY(), null);
                    Vector2Int pos = new Vector2Int(laserTile.getX(), laserTile.getY());
                    LaserTile newLaserTile = new LaserTile(pos, (laserTile.hasAttribute(Attribute.DIR_WEST)) ? LASER_VERTICAL : LASER_HORIZONTAL);
                    addEntityLaser(newLaserTile);
                    break;
                }
            }
        }
        entityLaserLayer.setCell(entityLaser.getX(), entityLaser.getY(), null);
        return entityLasers.remove(entityLaser) == null;
    }

    /**
     * TODO JAVADOC
     *
     * @return
     */
    private Set<LaserTile> getLaserEntities() {
        return entityLasers.keySet();
    }

    @NotNull
    @Override
    public Set<Entity> getEntities() {
        return entities.keySet();
    }

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public int getMapHeight() {
        return mapHeight;
    }

    TiledMapTileLayer getEntityLayer() {
        return entityLayer;
    }

    TiledMap getTiledMap() {
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
    @Nullable
    public TiledMapTileLayer getLayer(@NotNull String layer) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layer);
    }

    @Override
    @Nullable
    public Tile getTile(@NotNull String layerName, int x, int y) {
        TiledMapTileLayer layer = getLayer(layerName);
        if (layerName.equals(ENTITY_LAYER_NAME)) {
            layer = entityLayer;
        } else if (layerName.equals(ENITTY_LASER_LAYER_NAME)) {
            layer = entityLaserLayer;
        }
        return layer != null ? getTile(layer, x, y) : null;
    }

    //TODO test (should return a instance of a Tile that corresponds to the cell on the map, should cache instances, should return correct entity if on entity layer (and not create new instances of entities)
    @Override
    @Nullable
    public Tile getTile(@NotNull TiledMapTileLayer layer, int x, int y) {
        if (isOutsideBoard(x, y)) {
            return null;
        }

        if (layer.equals(entityLayer)) {
            Vector2Int vec = new Vector2Int(x, y);
            for (Map.Entry<Entity, Vector2Int> entry : entities.entrySet()) {
                if (vec.equals(entry.getValue())) {
                    return entry.getKey();
                }
            }
            return null;
        }

        Tile[][] tiles = this.tiles.get(layer);
        if (tiles == null) {
            return null;
        }

        if (tiles[x][y] == null) {
            TiledMapTileLayer.Cell cell = layer.getCell(x, y);
            if (cell == null) {
                return null;
            }
            TileGraphic tg = TileGraphic.fromTiledId(cell.getTile().getId());
            if (tg == null) {
                return null;
            }
            tiles[x][y] = tg.createInstance(x, y);
        }
        return tiles[x][y];
    }

    //TODO test (this should return instance of all non-null tiles on all layers at the given location)
    @Override
    @NotNull
    public List<Tile> getAllTiles(int x, int y) {
        return tiles.keySet().stream().map(layer -> getTile(layer, x, y)).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
