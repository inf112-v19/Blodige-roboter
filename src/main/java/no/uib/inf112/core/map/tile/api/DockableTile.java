package no.uib.inf112.core.map.tile.api;


public interface DockableTile extends Tile {

    /**
     *
     * @return which docking number the tile contains
     */
    int getSpawnNumber();
}
