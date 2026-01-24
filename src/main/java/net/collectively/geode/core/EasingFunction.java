package net.collectively.geode.core;

import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public interface EasingFunction {
    double get(double x);
}