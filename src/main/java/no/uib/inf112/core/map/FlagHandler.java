package no.uib.inf112.core.map;

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

    private ArrayList<Flag> flags;
    private final int[][] FLAG_POSITIONS;

    public FlagHandler(int nbOfFlags) {
        FLAG_POSITIONS = getDefaultPositions(nbOfFlags);
        flags = new ArrayList<>(nbOfFlags);

        for (int i = 0; i < nbOfFlags; i++) {
            flags.add(new Flag(FLAG_POSITIONS[i][0], FLAG_POSITIONS[i][1], i+1));
        }

    }

    private int[][] getDefaultPositions(int nbOfPositions) {
        int[][] positions = new int[nbOfPositions][2];
        for (int i = 0; i < nbOfPositions; i++) {
            positions[i][0] = 3;
            positions[i][1] = i + 1;
        }
        return positions;
    }

}
