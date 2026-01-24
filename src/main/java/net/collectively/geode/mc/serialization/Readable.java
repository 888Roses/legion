package net.collectively.geode.mc.serialization;

import net.minecraft.storage.ReadView;

public interface Readable {
    void read(ReadView view);
}
