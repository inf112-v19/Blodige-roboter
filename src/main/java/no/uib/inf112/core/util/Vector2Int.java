package no.uib.inf112.core.util;

/**
 * Mutable 2D integer vector
 *
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
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Vector2Int)) {
            return false;
        }
        Vector2Int vector2Int = (Vector2Int) object;
        return x == vector2Int.x && y == vector2Int.y;
    }
}

