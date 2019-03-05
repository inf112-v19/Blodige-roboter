package no.uib.inf112.core.testUtils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import no.uib.inf112.core.map.GameMap;
import org.jetbrains.annotations.NotNull;

public class HeadlessMapHandler extends GameMap {
    public HeadlessMapHandler(String map) {
        super(map);
    }

    @Override
    public void render(@NotNull Batch batch) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void zoomCamera(int direction) {

    }

    @Override
    public void moveCamera(float dx, float dy) {

    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public void resize() {

    }
}
