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

    @Override
    public boolean equals(Object object) {
        if (object instanceof Vector2Int ) {
            return false;
        }
        Vector2Int vector2Int = (Vector2Int) object;
        return this.x==vector2Int.x && this.y == vector2Int.y;
    }
}

