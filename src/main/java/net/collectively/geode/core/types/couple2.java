package net.collectively.geode.core.types;

public record couple2<T>(T a, T b) {
    public T get(boolean binary) {
        return binary ? a : b;
    }
}
