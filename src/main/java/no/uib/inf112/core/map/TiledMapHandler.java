package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import no.uib.inf112.core.map.tiled.CustomOrthogonalTiledMapRenderer;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;


public class TiledMapHandler extends MapCamera implements Disposable {

    private OrthogonalTiledMapRenderer renderer;

    /**
     * @param map The relative path from assets folder to the Tiled map file
     * @throws IllegalArgumentException if max zoom is less than min zoom
     */
    public TiledMapHandler(String map) {
        super(map);
        renderer = new CustomOrthogonalTiledMapRenderer(getTiledMap());
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
        for (int i = 0; i < entities.size(); i++) {
            //make sure the new x and y are always consistent
            Entity entity = entities.get(i);
            int x = entity.getX();
            int y = entity.getY();
            Vector2Int lastPos = prevPosOfEntity.get(i);

            if (lastPos == null) {
                lastPos = new Vector2Int(x, y);
                prevPosOfEntity.set(i, lastPos);
            } else if (!entity.shouldUpdate()) {
                //do not update if there is no change
                continue;
            }

            getEntityLayer().setCell(lastPos.x, lastPos.y, null);
            entity.update(false);
            setEntityOnBoard(entity, x, y);
            prevPosOfEntity.set(i, new Vector2Int(x, y));

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
     * @param x      The new x, provided as a parameter to make this thread safe
     * @param y      The new y, provided as a parameter to make this thread safe
     */
    private void setEntityOnBoard(@NotNull Entity entity, int x, int y) {
        if (isOutsideBoard(x, y)) {
            throw new IllegalArgumentException("Given location (" + x + ", " + y + ") is out of bounds for " + entity.toString());
        }
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell().setTile(entity.getTile());
        getEntityLayer().setCell(x, y, cell);
    }
}
