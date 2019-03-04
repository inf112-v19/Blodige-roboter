package no.uib.inf112.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public abstract class MapCamera implements MapHandler {

    private float zoomSensitivity;
    private float maxZoom;
    private float minZoom;

    private OrthographicCamera camera;


    private float defaultHeight;
    private float defaultWidth;
    private float currHeight;
    private float currWidth;

    public MapCamera() {

        defaultWidth = Gdx.graphics.getWidth();
        defaultHeight = Gdx.graphics.getHeight();

        Gdx.app.postRunnable(() -> {
            camera = new OrthographicCamera();

            zoomSensitivity = getProperties().get(ZOOM_SENSITIVITY_PATH, DEFAULT_ZOOM_SENSITIVITY, float.class);
            maxZoom = getProperties().get(MAX_ZOOM_PATH, DEFAULT_MAX_ZOOM, float.class);
            minZoom = getProperties().get(MIN_ZOOM_PATH, DEFAULT_MIN_ZOOM, float.class);

            if (maxZoom < minZoom) {
                throw new IllegalArgumentException(
                        "Max (" + maxZoom + ") zoom cannot be less than min zoom (" + minZoom + ")");
            }

            resize();

        });
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void zoomCamera(int direction) {
        if (direction == 0) {
            return;
        }
        float delta = Math.signum(direction) * getZoomSensitivity();
        camera.zoom += delta;
        ensureZoomBounds();
    }

    private void ensureZoomBounds() {
        if (camera.zoom > getMaxZoom()) {
            camera.zoom = getMaxZoom();
        } else if (camera.zoom < getMinZoom()) {
            camera.zoom = getMinZoom();
        }
    }

    /**
     * @return How many tiles are shown in the x any y direction
     */
    private Vector2 tilesShown() {
        return new Vector2((currWidth * camera.zoom) / getTileWidth(), (currHeight * camera.zoom) / getTileHeight());
    }


    /*
     * Calculate how much larger the current screen is than the default size
     */
    private float deltaSize() {
        return ((currWidth / defaultWidth) + (currHeight / defaultHeight)) / 2;
    }

    @Override
    public void moveCamera(float dx, float dy) {

        camera.position.x += dx * camera.zoom;
        camera.position.y += dy * camera.zoom;

        ensurePositionBounds();
    }

    /**
     * Make sure at least half the board is inside the camera
     */
    private void ensurePositionBounds() {
        Vector2 visibleTiles = tilesShown();

        float minX = -visibleTiles.x / 2;
        float maxX = (visibleTiles.x / 2) + (getMapWidth() * getTileWidth());


        float minY = -visibleTiles.y / 2;
        float maxY = (visibleTiles.y / 2) + (getMapHeight() * getTileHeight());

        if (camera.position.x < minX) {
            camera.position.x = minX;
        } else if (camera.position.x > maxX) {
            camera.position.x = maxX;
        }

        if (camera.position.y < minY) {
            camera.position.y = minY;
        } else if (camera.position.y > maxY) {
            camera.position.y = maxY;
        }
    }


    @Override
    public void resize() {
        if (camera == null) {
            return;
        }

        currHeight = Gdx.graphics.getHeight();
        currWidth = Gdx.graphics.getWidth();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //center the camera and zoom
        camera.zoom = getMaxZoom();
        camera.position.x = getMapWidth() * getTileWidth() / 2f;
        camera.position.y = getMapHeight() * getTileHeight() / 2f;

        ensureZoomBounds();
        ensurePositionBounds();
    }


    public float getZoomSensitivity() {
        return zoomSensitivity / deltaSize();
    }

    public float getMaxZoom() {
        return maxZoom / deltaSize();
    }

    public float getMinZoom() {
        return minZoom / deltaSize();
    }
}
