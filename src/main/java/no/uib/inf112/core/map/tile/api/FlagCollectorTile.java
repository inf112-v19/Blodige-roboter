package no.uib.inf112.core.map.tile.api;

/**
 * @author Elg
 */
public interface FlagCollectorTile extends Tile {

    /**
     * @return amount of flags visited
     */
    int getFlags();

    /**
     * @param flagRank
     * @return if player can get given flag or nor
     */
    boolean canGetFlag(int flagRank);

    /**
     * Add flags visited by one
     */
    void registerFlagVisit();
}
