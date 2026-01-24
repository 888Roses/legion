package net.collectively.geode.mc.serialization;

import net.minecraft.storage.WriteView;

public interface Writable {
    void write(WriteView view);
}
