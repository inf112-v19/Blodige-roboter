package no.uib.inf112.core.player;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.MapHandler;

public class Robot implements Entity {

    private final TiledMapTile tile;
    private int x, y;
    private MapHandler handler;

    /**
     * @param handler
     *     from the map
     */
    public Robot(int x, int y, MapHandler handler) {
        this.x = x;
        this.y = y;

        this.handler = handler;
        tile = handler.getMapTileSets().getTile(1);
        if (tile == null) { throw new IllegalStateException("Failed to find robot tile"); }
        handler.addEntity(this);
    }

    @Override
    public TiledMapTile getTile() {
        return tile;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
