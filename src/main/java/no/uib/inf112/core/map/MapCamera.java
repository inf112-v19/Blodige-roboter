package no.uib.inf112.core.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

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
            resize();

            zoomSensitivity = getProperties().get(ZOOM_SENSITIVITY_PATH, DEFAULT_ZOOM_SENSITIVITY, float.class);
            maxZoom = getProperties().get(MAX_ZOOM_PATH, DEFAULT_MAX_ZOOM, float.class);
            minZoom = getProperties().get(MIN_ZOOM_PATH, DEFAULT_MIN_ZOOM, float.class);

            if (maxZoom < minZoom) {
                throw new IllegalArgumentException(
                        "Max (" + maxZoom + ") zoom cannot be less than min zoom (" + minZoom + ")");
            }

            camera.zoom = (minZoom + maxZoom) / 2;

            ensureZoomBounds();
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

    private float delta() {
        return currHeight / defaultHeight;
    }

    @Override
    public void moveCamera(float dx, float dy) {
        camera.position.x += dx * camera.zoom;
        camera.position.y += dy * camera.zoom;
        //TODO Issue 34: make sure the camera is within a reasonable distance of the board edges
    }

    @Override
    public void resize() {
        if (camera == null) {
            return;
        }

        currHeight = Gdx.graphics.getHeight();
        currWidth = Gdx.graphics.getWidth();

        ensureZoomBounds();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    public float getZoomSensitivity() {
        return zoomSensitivity / delta();
    }

    public float getMaxZoom() {
        return maxZoom / delta();
    }

    public float getMinZoom() {
        return minZoom / delta();
    }
}
