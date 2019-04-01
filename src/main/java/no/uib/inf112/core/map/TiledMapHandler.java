package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.tiled.CustomOrthogonalTiledMapRenderer;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.UVector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class TiledMapHandler extends MapCamera implements Disposable {

    private OrthogonalTiledMapRenderer renderer;

    /**
     * @param map The relative path from assets folder to the Tiled map file
     * @throws IllegalArgumentException if max zoom is less than min zoom
     */
    public TiledMapHandler(String map) {
        super(map);
        if (!GameGraphics.HEADLESS) {
            renderer = new CustomOrthogonalTiledMapRenderer(getTiledMap());
        }
    }


    @Override
    public void render(@NotNull Batch batch) {
        getCamera().update();
        batch.setProjectionMatrix(getCamera().combined);
        renderer.setView(getCamera());
        renderer.render();
    }

    @Override
    public void update(float delta) {
        Map<UVector2Int, Entity> entities = getEntities();

        for (Map.Entry<UVector2Int, Entity> entry : entities.entrySet()) {
            Entity entity = entry.getValue();
            UVector2Int oldPos = entry.getKey();
            if (entity.shouldUpdate()) {

                int x = entity.getX();
                int y = entity.getY();

                entity.update(false);
                getEntityLayer().setCell(oldPos.x, oldPos.y, null);

                if (isOutsideBoard(x, y)) {
                    throw new IllegalArgumentException("Given location (" + x + ", " + y + ") is out of bounds for " + entity.toString());
                }
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(entity.getTile());
                getEntityLayer().setCell(x, y, cell);

                entities.remove(oldPos, entity);
                entities.put(new UVector2Int(x, y), entity);
            }
        }
    }


    @Override
    public void dispose() {
        renderer.dispose();
        getTiledMap().dispose();
    }
}
