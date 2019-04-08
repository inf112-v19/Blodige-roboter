package no.uib.inf112.core.testutils;

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
        //Skeleton code: made for running in test environment nowhere to render too
    }

    @Override
    public void zoomCamera(int direction) {
        //Skeleton code: no camera to move
    }

    @Override
    public void moveCamera(float dx, float dy) {
        //Skeleton code: no camera to move
    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public void resize(int width, int height) {
        //Skeleton code: made for running in test environment nowhere to render too
    }

}
