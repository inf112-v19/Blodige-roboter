package no.uib.inf112.core.util;

/**
 * Immutable 2D integer vector
 *
 * @author Elg
 */
public class UVector2Int {

    public final int x;
    public final int y;

    public UVector2Int(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UVector2Int that = (UVector2Int) o;

        if (x != that.x) {
            return false;
        }
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
