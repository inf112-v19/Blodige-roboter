package no.uib.inf112.core.testUtils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import no.uib.inf112.core.map.TiledMapHandler;
import org.jetbrains.annotations.NotNull;

public class HeadlessMapHandler extends TiledMapHandler {

    public HeadlessMapHandler(String map) {
        super(map);
    }

    @Override
    public void render(@NotNull Batch batch) {
        //Do nothing
    }

    @Override
    public void zoomCamera(int direction) {
        //Do nothing
    }

    @Override
    public void moveCamera(float dx, float dy) {
        //Do nothing
    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public void resize(int width, int height) {
        //Do nothing
    }

}
