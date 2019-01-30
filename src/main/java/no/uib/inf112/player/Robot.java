package no.uib.inf112.player;

import no.uib.inf112.core.map.TiledMapHandler;

public class Robot {
    private int x, y;
    private TiledMapHandler handler;

    /**
     * @param x
     * @param y
     * @param handler from the map
     */
    public Robot(int x, int y, TiledMapHandler handler) {
        this.x = x;
        this.y = y;

        this.handler = handler;
        handler.setCell(x, y, 1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int newX, int newY) {
        //Todo, implement this
    }
}
