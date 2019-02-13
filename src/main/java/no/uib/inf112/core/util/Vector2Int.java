package no.uib.inf112.core.util;

/**
 * @author Elg
 */
public class Vector2Int {

    public int x;
    public int y;

    public Vector2Int(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
