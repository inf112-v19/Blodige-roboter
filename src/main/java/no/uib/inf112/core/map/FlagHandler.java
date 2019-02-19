package no.uib.inf112.core.map;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;

/**
 * @author rikkeaas
 */
public class FlagHandler {

    private class Flag implements ISpecialTile {

        private int x, y;
        private int rank;

        /**
         * Create a new flag at x,y coordinates with a given rank
         *
         * @param x    the x coordinate of flag
         * @param y    the y coordinate of flag
         * @param rank the rank of flag (i.e. the place in the order of flags the robots have to follow from 1 to nb of flags)
         */
        public Flag(int x, int y, int rank) {
            this.x = x;
            this.y = y;
            this.rank = rank;
        }

        public int getX() { return this.x; }
        public int getY() { return this.y; }
        public int getRank() { return this.rank; }

    }

    private ArrayList<ISpecialTile> flags;
    private final ArrayList<int[]> FLAG_POSITIONS;
    private final int FLAG_COUNT;

    public FlagHandler(int flagCount) {
        FLAG_POSITIONS = getDefaultPositions(flagCount);
        FLAG_COUNT = flagCount;
        flags = new ArrayList<>(flagCount);

        for (int i = 0; i < flagCount; i++) {
            flags.add(new Flag(FLAG_POSITIONS.get(i)[0], FLAG_POSITIONS.get(i)[1], i+1));
        }
    }

    public int getFLagCount() { return this.FLAG_COUNT; }

    public ArrayList<ISpecialTile> getFlags() { return this.flags; }

    public boolean coordinateHasFlag(int x, int y) {
        int[] coords = new int[2];
        coords[0] = x; coords[1] = y;
        return FLAG_POSITIONS.contains(coords);
    }

    /**
     * Searches up rank of flag at position x,y. If there is no flag method will return -1
     *
     * @param x the x coordinate of the position that is checked
     * @param y the y coordinate of the position that is checked
     * @return the rank of flag at x,y or -1 if there is no flag
     */
    public int getRankOfFlag(int x, int y) {
        for (ISpecialTile flag : flags) {
            if (flag.getX() == x && flag.getY() == y) {
                return flag.getRank();
            }
        }
        return -1;
    }

    /**
     * Some non-important default positions. Will be (3,1), (3,2), (3,3),...
     *
     * @param nbOfPositions number of positions you want to generate
     * @return the positions
     */
    private ArrayList<int[]> getDefaultPositions(int nbOfPositions) {
        ArrayList<int[]> positions = new ArrayList<>(nbOfPositions);
        for (int i = 0; i < nbOfPositions; i++) {
            int[] temp = new int[2];
            temp[0] = 3; temp[1] = i + 1;
            positions.add(temp);
        }
        return positions;
    }

}
