package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Color;
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
import no.uib.inf112.core.util.UVector2Int;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static no.uib.inf112.core.map.tile.TileGraphic.LASER_HORIZONTAL;
import static no.uib.inf112.core.map.tile.TileGraphic.LASER_VERTICAL;

public abstract class GameMap implements MapHandler {

    private TiledMap tiledMap;

    private TiledMapTileLayer entityLayer;
    private TiledMapTileLayer entityLaserLayer;

    //A map of all know entities and their last know location
    private Map<UVector2Int, Entity> entities;
    private Set<Tile> entityLasers;
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
            System.out.println("WARN: Given tiled map does not have a tile layer named '" + FLAG_LAYER_NAME + "'");
        }
        if (collidables == null) {
            System.out.println("WARN: Given tiled map does not have a tile layer named '" + COLLIDABLES_LAYER_NAME + "'");
        }
        if (lasers == null) {
            System.out.println("WARN: Given tiled map does not have a tile layer named '" + LASERS_LAYER_NAME + "'");
        }

        TiledMapTileLayer collidablesLayer = collidables;
        TiledMapTileLayer flagLayer = flags;
        TiledMapTileLayer boardLayer = baseLayer;
        TiledMapTileLayer laserLayer = lasers;

        //create a two new empty layer for all the robots to play on, one for entity and one for their laser trace
        entityLaserLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        tiledMap.getLayers().add(entityLaserLayer);

        entityLayer = new TiledMapTileLayer(mapWidth, mapHeight, tileWidth, tileHeight);
        tiledMap.getLayers().add(entityLayer);


        tiles = new HashMap<>();
        tiles.put(boardLayer, new Tile[mapWidth][mapHeight]);
        tiles.put(laserLayer, new Tile[mapWidth][mapHeight]);
        tiles.put(entityLaserLayer, null);
        tiles.put(entityLayer, null);
        tiles.put(collidablesLayer, new Tile[mapWidth][mapHeight]);
        tiles.put(flagLayer, new Tile[mapWidth][mapHeight]);

        entities = new ConcurrentHashMap<>();
        entityLasers = new HashSet<>();
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
        if (getTile(entityLayer, entity.getX(), entity.getY()) != null) {
            throw new IllegalStateException("Cannot add an entity on top of another entity");
        }
        entities.put(new UVector2Int(entity.getX(), entity.getY()), entity);
        entity.update(true);
    }

    @Override
    public boolean isOutsideBoard(int x, int y) {
        return x < 0 || x >= getMapWidth() || y < 0 | y >= getMapHeight();
    }

    @Override
    public void removeEntity(@Nullable Entity entity) {
        if (entity == null) {
            return;
        }
        UVector2Int pos = new UVector2Int(entity.getX(), entity.getY());
        Entity e = entities.get(pos);

        if (entity.equals(e)) {
            entityLayer.setCell(entity.getX(), entity.getY(), null);
            entities.remove(pos);
        }
    }

    @Override
    public void addEntityLaser(@NotNull Tile laser) {
        for (Tile knownLaser : getLaserEntities()) {

            if (laser.getX() == knownLaser.getX() && laser.getY() == knownLaser.getY()) {
                // Already a laser tile in this layer, se if we need to change it to a cross or just ignore it (same orientation)
                if (laser.getTile() != knownLaser.getTile()) {
                    removeEntityLaser(knownLaser);
                    entityLasers.add(new LaserTile(new Vector2Int(knownLaser.getX(), knownLaser.getY()), TileGraphic.LASER_CROSS, Color.WHITE));
                    entityLaserLayer.setCell(laser.getX(), laser.getY(), new TiledMapTileLayer.Cell().setTile(TileGraphic.LASER_CROSS.getTile()));
                    return;
                } else {
                    return;
                }
            }
        }
        entityLaserLayer.setCell(laser.getX(), laser.getY(), new TiledMapTileLayer.Cell().setTile(laser.getTile()));
        entityLasers.add(laser);
    }

    private boolean removeEntityLaser(Tile entityLaser) {
        Tile tile = getTile(entityLaserLayer, entityLaser.getX(), entityLaser.getY());
        if (tile != null && tile.getTile().getId() == TileGraphic.LASER_CROSS.getId()) {
            //There is two lasers here remove only the one we want and restore tile to the other
            entityLaserLayer.setCell(entityLaser.getX(), entityLaser.getY(), null);
            for (Tile laserTile : entityLasers) {
                if (laserTile.getX() == entityLaser.getX() && laserTile.getY() == entityLaser.getY()) {
                    entityLasers.remove(laserTile);
                    entityLaserLayer.setCell(entityLaser.getX(), entityLaser.getY(), null);
                    Vector2Int pos = new Vector2Int(laserTile.getX(), laserTile.getY());
                    LaserTile newLaserTile = new LaserTile(pos, (laserTile.hasAttribute(Attribute.DIR_WEST)) ? LASER_VERTICAL : LASER_HORIZONTAL);
                    addEntityLaser(newLaserTile);
                    return true;
                }
            }
        }
        entityLaserLayer.setCell(entityLaser.getX(), entityLaser.getY(), null);
        return entityLasers.remove(entityLaser);
    }

    @Override
    public void removeEntityLasers() {
        for (int y = 0; y < getMapHeight(); y++) {
            for (int x = 0; x < getMapWidth(); x++) {
                entityLaserLayer.setCell(x, y, null);
            }
        }
        entityLasers.clear();
    }

    /**
     * @return the set of all the laser traces from the entities currently on the map
     */
    private Set<Tile> getLaserEntities() {
        return entityLasers;
    }

    public Map<UVector2Int, Entity> getEntities() {
        return entities;
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
    @Nullable
    public TiledMapTileLayer getLayer(@NotNull String layer) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(layer);
    }

    @Override
    @Nullable
    public Tile getTile(@NotNull String layerName, int x, int y) {
        TiledMapTileLayer layer;
        if (ENTITY_LAYER_NAME.equals(layerName)) {
            layer = entityLayer;
        } else if (ENITTY_LASER_LAYER_NAME.equals(layerName)) {
            layer = entityLaserLayer;
        } else {
            layer = getLayer(layerName);
        }
        return getTile(layer, x, y);
    }

    //TODO ISSUE #118 test (should return a instance of a Tile that corresponds to the cell on the map, should cache instances, should return correct entity if on entity layer (and not create new instances of entities)
    @Override
    @Nullable
    public Tile getTile(@Nullable TiledMapTileLayer layer, int x, int y) {
        if (isOutsideBoard(x, y) || layer == null) {
            return null;
        }

        if (layer.equals(entityLayer)) {
            return entities.get(new UVector2Int(x, y));
        }

        if (layer.equals(entityLaserLayer)) {
            for (Tile tile : entityLasers) {
                if (tile.getX() == x && tile.getY() == y) {
                    return tile;
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

    //TODO ISSUE #118  test (this should return instance of all non-null tiles on all layers at the given location)
    @Override
    @NotNull
    public List<Tile> getAllTiles(int x, int y) {
        return tiles.keySet().stream().map(layer -> getTile(layer, x, y)).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
