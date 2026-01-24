package net.collectively.geode.mc._internal;

import net.collectively.geode.mc._internal.index.GeodeMinecraftPayloadRegistry;

public interface GeodeMinecraft {
    static void initialize() {
        GeodeMinecraftPayloadRegistry.registerAll();
    }
}
