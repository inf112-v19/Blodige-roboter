package no.uib.inf112.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public class TiledMapHandler implements Disposable {

    public static final float DEFAULT_ZOOM_SENSITIVITY = 1f;
    public static final float DEFAULT_MAX_ZOOM = 10f;
    public static final float DEFAULT_MIN_ZOOM = 1f;

    public static final String ZOOM_SENSITIVITY_PATH = "zoomsensitivity";
    public static final String MAX_ZOOM_PATH = "maxzoom";
    public static final String MIN_ZOOM_PATH = "minzoom";

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;

    private int mapWidth;
    private int mapHeight;
    private int tileWidth;
    private int tileHeight;

    private float zoom;

    private float zoomSensitivity;
    private float maxZoom;
    private float minZoom;


    /**
     * TODO make the zoom properties be part of the map file
     *
     * @param map
     *     The relative path from assets folder to the Tiled map file
     *
     * @throws IllegalArgumentException
     *     if max zoom is less than min zoom
     */
    public TiledMapHandler(String map) {
        try {
            tiledMap = new TmxMapLoader().load(map);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Invalid map");
        }

        mapWidth = tiledMap.getProperties().get("width", int.class);
        mapHeight = tiledMap.getProperties().get("height", int.class);
        tileWidth = tiledMap.getProperties().get("tilewidth", int.class);
        tileHeight = tiledMap.getProperties().get("tileheight", int.class);


        zoom = 1f; //Start with no zoom

        zoomSensitivity = tiledMap.getProperties().get(ZOOM_SENSITIVITY_PATH, DEFAULT_ZOOM_SENSITIVITY, float.class);
        maxZoom = tiledMap.getProperties().get(MAX_ZOOM_PATH, DEFAULT_MAX_ZOOM, float.class);
        minZoom = tiledMap.getProperties().get(MIN_ZOOM_PATH, DEFAULT_MIN_ZOOM, float.class);

        if (maxZoom < minZoom) {
            throw new IllegalArgumentException(
                "Max (" + maxZoom + ") zoom cannot be less than min zoom (" + minZoom + ")");
        }

        renderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render(OrthographicCamera camera) {
        camera.setToOrtho(false, zoom * Gdx.graphics.getWidth(), zoom * Gdx.graphics.getHeight());
        renderer.setView(camera);
        renderer.render();
    }

    public void update(float delta) {

    }

    /**
     * Zoom in or out of the map
     *
     * @param direction
     *     The direction to zoom, if not 1 or -1 {@link Math#signum(float)} is used to get the direction
     *
     * @throws IllegalArgumentException
     *     if give zoom is 0
     */
    public void zoom(int direction) {
        if (direction == 0) {
            throw new IllegalArgumentException("Zoom direction cannot be 0");
        }
        float delta = Math.signum(direction) * zoomSensitivity;
        zoom += delta;
        if (zoom > maxZoom) { zoom = maxZoom; }
        else if (zoom < minZoom) { zoom = minZoom; }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        tiledMap.dispose();
    }
}
