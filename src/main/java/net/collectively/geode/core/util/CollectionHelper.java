package net.collectively.geode.core.util;

@SuppressWarnings("unused")
public interface CollectionHelper {
    static <T> boolean isOutsideOfBounds(T[] array, int index) {
        return index < 0 || index >= array.length;
    }
}
