package no.uib.inf112.core.util;

import org.jetbrains.annotations.NotNull;

/**
 * A tuple that is comparable on its key
 *
 * @author Elg
 */
public class ComparableTuple<K extends Comparable<K>, V> implements Comparable<ComparableTuple<K, V>> {

    @NotNull
    public K key;
    @NotNull
    public V value;

    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public ComparableTuple(@NotNull K key, @NotNull V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComparableTuple<?, ?> that = (ComparableTuple<?, ?>) o;

        if (!key.equals(that.key)) {
            return false;
        }
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public int compareTo(@NotNull ComparableTuple<K, V> other) {
        return key.compareTo(other.key);
    }
}
