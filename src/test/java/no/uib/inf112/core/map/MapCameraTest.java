package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import no.uib.inf112.desktop.TestGraphics;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static no.uib.inf112.core.map.MapHandler.*;

public class MapCameraTest extends TestGraphics {

    private MapCamera c;


    @Before
    public void setUp() {
        c = new MapCameraTestImpl();
    }

    @Test
    public void initCameraTest() {
        assertEquals(DEFAULT_MAX_ZOOM, c.getCamera().zoom);
    }

    @Test
    public void deltaIsOne() {
        assertEquals(1f, c.deltaSize());
    }

    @Test
    public void moveCameraChangesXAndYPosition() {
        Vector3 oldPos = c.getCamera().position.cpy();
        c.moveCamera(1, 2);
        assertEquals(oldPos.x + c.getCamera().zoom, c.getCamera().position.x);
        assertEquals(oldPos.y + 2 * c.getCamera().zoom, c.getCamera().position.y);
    }

    @Test
    public void moveCameraTwoTimesTest() {
        Vector3 oldPos = c.getCamera().position.cpy();
        c.moveCamera(-3, -2);
        assertEquals(oldPos.x - 3 * c.getCamera().zoom, c.getCamera().position.x);
        assertEquals(oldPos.y - 2 * c.getCamera().zoom, c.getCamera().position.y);

        //moving back should take us to the start location
        c.moveCamera(3, 2);
        assertEquals(oldPos.x, c.getCamera().position.x);
        assertEquals(oldPos.y, c.getCamera().position.y);
    }

    @Test
    public void zoomZeroDoesNotZoom() {
        c.zoomCamera(0);
        assertEquals(DEFAULT_MAX_ZOOM, c.getCamera().zoom);
    }

    @Test
    public void zoomInOneTimeZoomsInOnce() {
        c.zoomCamera(ZOOM_IN);
        assertEquals(DEFAULT_MAX_ZOOM - DEFAULT_ZOOM_SENSITIVITY, c.getCamera().zoom);
    }

    @Test
    public void zoomNineTimesZoomsCorrect() {
        for (double i = DEFAULT_MAX_ZOOM; i > DEFAULT_MIN_ZOOM; i--) {
            double zoom = c.getCamera().zoom;
            c.zoomCamera(ZOOM_IN);
            assertEquals("i: " + i, zoom - DEFAULT_ZOOM_SENSITIVITY, (double) c.getCamera().zoom);
        }
    }

    @Test
    public void zoomOverMaxResultsInMaxZoom() {
        c.getCamera().zoom = DEFAULT_MAX_ZOOM;
        for (int i = 0; i < 50; i++) {
            c.zoomCamera(ZOOM_OUT);
            assertEquals(DEFAULT_MAX_ZOOM, c.getCamera().zoom);
        }
    }

    @Test
    public void zoomZeroManyTimesDoesNotZoom() {
        for (int i = 0; i < 20; i++) {
            c.zoomCamera(0);
            assertEquals(DEFAULT_MAX_ZOOM, c.getCamera().zoom);
        }
    }

    @Test
    public void zoomInDoesNotZoomOverMinZoom() {
        c.getCamera().zoom = DEFAULT_MIN_ZOOM;
        for (int i = 0; i < 20; i++) {
            c.zoomCamera(ZOOM_IN);
            assertEquals(DEFAULT_MIN_ZOOM, c.getCamera().zoom);
        }
    }


    @Test
    public void zoomingScalesCorrectlyWithTilesShown() {
        for (double i = DEFAULT_MAX_ZOOM; i >= DEFAULT_MIN_ZOOM; i--) {
            assertEquals(new Vector2((float) i, (float) i), c.tilesShown());
            c.zoomCamera(ZOOM_IN);
        }
    }

    @Test
    public void perfectResizingDoesNotAffectTilesShown() {
        Vector2 old = c.tilesShown().cpy();
        c.resize(2, 2);
        assertEquals(old, c.tilesShown());
    }

    private class MapCameraTestImpl extends MapCamera {

        MapCameraTestImpl() {
            super();
        }

        @Override
        public void render(@NotNull Batch batch) {
            //Skeleton code: made for running in test environment nowhere to render too
        }

        @Override
        public void update(float delta) {
            //Skeleton code: made for running in test environment nowhere to render too
        }

        @Override
        public MapProperties getProperties() {
            return new MapProperties();
        }

        @Override
        public int getMapWidth() {
            return 200;
        }

        @Override
        public int getMapHeight() {
            return 200;
        }

        @Override
        public int getTileWidth() {
            return 1;
        }

        @Override
        public int getTileHeight() {
            return 1;
        }
    }
}
