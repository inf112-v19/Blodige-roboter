package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;
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
        renderer = new OrthogonalTiledMapRenderer(getTiledMap());
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
        for (Map.Entry<Entity, Vector2Int> entry : super.entities.entrySet()) {

            //make sure the new x and y are always consistent
            int x = entry.getKey().getX();
            int y = entry.getKey().getY();
            Vector2Int lastPos = entry.getValue();

            if (lastPos == null) {
                lastPos = new Vector2Int(x, y);
                entry.setValue(lastPos);
            } else if (!entry.getKey().shouldUpdate()) {
                //do not update if there is no change
                continue;
            }

            getEntityLayer().setCell((int) lastPos.x, (int) lastPos.y, null);
            entry.getKey().update(false);
            setEntityOnBoard(entry.getKey(), lastPos, x, y);

        }
    }


    @Override
    public void dispose() {
        renderer.dispose();
        getTiledMap().dispose();
    }


    /**
     * Draw an entity on the entity layer
     *
     * @param entity The entity to draw
     * @param oldPos The last known position
     * @param x      The new x, provided as a parameter to make this thread safe
     * @param y      The new y, provided as a parameter to make this thread safe
     */
    private void setEntityOnBoard(@NotNull Entity entity, @NotNull Vector2Int oldPos, int x, int y) {
        if (entity.getTileType() == null) {
            return;
        }
        if (isOutsideBoard(x, y)) {
            throw new IllegalArgumentException("Given location (" + x + ", " + y + ") is out of bounds");
        }
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(entity.getTileType().getTile());
        getEntityLayer().setCell(x, y, cell);

        oldPos.x = x;
        oldPos.y = y;
    }
}