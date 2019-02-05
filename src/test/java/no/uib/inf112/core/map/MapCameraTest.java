package no.uib.inf112.core.map;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.utils.Clipboard;
import no.uib.inf112.core.player.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class MapCameraTest {
    private MapCamera c;

    @BeforeClass
    public static void initiateApp() {

        Gdx.app = new Application() {
            @Override
            public ApplicationListener getApplicationListener() {
                return null;
            }

            @Override
            public Graphics getGraphics() {
                return null;
            }

            @Override
            public Audio getAudio() {
                return null;
            }

            @Override
            public Input getInput() {
                return null;
            }

            @Override
            public Files getFiles() {
                return null;
            }

            @Override
            public Net getNet() {
                return null;
            }

            @Override
            public void log(String s, String s1) {

            }

            @Override
            public void log(String s, String s1, Throwable throwable) {

            }

            @Override
            public void error(String s, String s1) {

            }

            @Override
            public void error(String s, String s1, Throwable throwable) {

            }

            @Override
            public void debug(String s, String s1) {

            }

            @Override
            public void debug(String s, String s1, Throwable throwable) {

            }

            @Override
            public void setLogLevel(int i) {

            }

            @Override
            public int getLogLevel() {
                return 0;
            }

            @Override
            public void setApplicationLogger(ApplicationLogger applicationLogger) {

            }

            @Override
            public ApplicationLogger getApplicationLogger() {
                return null;
            }

            @Override
            public ApplicationType getType() {
                return null;
            }

            @Override
            public int getVersion() {
                return 0;
            }

            @Override
            public long getJavaHeap() {
                return 0;
            }

            @Override
            public long getNativeHeap() {
                return 0;
            }

            @Override
            public Preferences getPreferences(String s) {
                return null;
            }

            @Override
            public Clipboard getClipboard() {
                return null;
            }

            @Override
            public void postRunnable(Runnable runnable) {
                runnable.run();
            }

            @Override
            public void exit() {

            }

            @Override
            public void addLifecycleListener(LifecycleListener lifecycleListener) {

            }

            @Override
            public void removeLifecycleListener(LifecycleListener lifecycleListener) {

            }
        };
    }


    @Before
    public void init() {
        c = new MapCamera() {

            @Override
            public void render(@NotNull Batch batch) {

            }

            @Override
            public void update(float delta) {

            }

            @Override
            public MapProperties getProperties() {
                return new MapProperties();
            }

            @NotNull
            @Override
            public TiledMapTile getBoardLayerTile(int x, int y) {
                return null;
            }

            @Nullable
            @Override
            public Entity getEntity(int x, int y) {
                return null;
            }

            @NotNull
            @Override
            public TiledMapTileSets getMapTileSets() {
                return null;
            }

            @Override
            public void addEntity(@NotNull Entity entity) {

            }

            @Override
            public boolean removeEntity(@Nullable Entity entity) {
                return false;
            }

            @NotNull
            @Override
            public Set<Entity> getEntities() {
                return null;
            }

            @Override
            public boolean isOutsideBoard(int x, int y) {
                return false;
            }

            @Override
            public int getMapWidth() {
                return 0;
            }

            @Override
            public int getMapHeight() {
                return 0;
            }

            @Override
            public void dispose() {

            }

            @Override
            public void resize() {

            }


        };
    }

    @Test
    public void initCameraTest() {
        assertEquals(c.DEFAULT_MIN_ZOOM, c.getCamera().zoom);

    }

    @Test
    public void moveCameraChangesXandYposition() {
        c.moveCamera(1000, 1000);
        assertEquals(1000f, c.getCamera().position.x);
        assertEquals(1000f, c.getCamera().position.y);
    }

    @Test
    public void moveCameraTwoTimesTest() {
        c.moveCamera(-1000, -1000);
        assertEquals(-1000f, c.getCamera().position.x);
        assertEquals(-1000f, c.getCamera().position.y);

        c.moveCamera(1000, 1000);
        assertEquals(0f, c.getCamera().position.x);
        assertEquals(0f, c.getCamera().position.y);
    }

    @Test
    public void zoomZeroDoesNotZoom() {
        c.zoomCamera(0);
        assertEquals(c.DEFAULT_MIN_ZOOM, c.getCamera().zoom);
    }

    @Test
    public void zoomOneTimeZoomsOnce() {
        c.zoomCamera(1);
        assertEquals(c.DEFAULT_MIN_ZOOM + c.DEFAULT_ZOOM_SENSITIVITY, c.getCamera().zoom);
    }

    @Test
    public void zoomNineTimesZoomsCorrect() {
        for (int i = 1; i <= 9; i++) {
            float zoom = c.getCamera().zoom;
            c.zoomCamera(54);
            assertEquals(zoom + c.DEFAULT_ZOOM_SENSITIVITY, c.getCamera().zoom);
        }
    }

    @Test
    public void zoomOverMaxResultsInMaxZoom() {
        for (int i = 0; i < 50; i++) {
            c.zoomCamera(30);
        }
        assertEquals(c.DEFAULT_MAX_ZOOM, c.getCamera().zoom);
    }

    @Test
    public void zoomZeroManyTimesDoesNotZoom() {
        for (int i = 0; i < 20; i++) {
            c.zoomCamera(0);
            assertEquals(c.DEFAULT_MIN_ZOOM, c.getCamera().zoom);
        }
    }

    @Test
    public void zoomInDoesNotZoomOverMinzoom() {
        for (int i = 0; i < 20; i++) {
            c.zoomCamera(-50);
            assertEquals(c.DEFAULT_MIN_ZOOM, c.getCamera().zoom);
        }
    }
}
