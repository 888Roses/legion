package net.collectively.geode.mc.serialization;

import net.collectively.geode.core.types.*;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

public interface WriteReadUtil {
    static void writeDouble3(WriteView view, String name, double3 value) {
        view.put(name, double3.CODEC, value);
    }

    static double3 readDouble3(ReadView view, String name, double3 defaultValue) {
        return view.read(name, double3.CODEC).orElse(defaultValue);
    }

    static void writeDouble2(WriteView view, String name, double2 value) {
        view.put(name, double2.CODEC, value);
    }

    static double2 readDouble2(ReadView view, String name, double2 defaultValue) {
        return view.read(name, double2.CODEC).orElse(defaultValue);
    }
}
