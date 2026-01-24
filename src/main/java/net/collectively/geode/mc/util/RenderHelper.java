package net.collectively.geode.mc.util;

import net.collectively.geode.core.types.double3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.world.World;
import org.joml.Quaternionf;

/**
 * Collection of client rendering utils.
 */
public interface RenderHelper {
    /**
     * Gets the tick delta of the application for that render tick.
     *
     * @see RenderTickCounter#getTickProgress(boolean)
     */
    @Environment(EnvType.CLIENT)
    static float getTickDelta() {
        MinecraftClient client = MinecraftClient.getInstance();
        RenderTickCounter counter = client.getRenderTickCounter();
        return counter.getTickProgress(true);
    }

    /**
     * Gets the tick delta if the world is client sided. Otherwise, returns 0.
     *
     * @param world The world in which we want to get the tick delta. Mandatory to know whether it is client side or server side.
     * @return The tick delta or 0 if server side.
     * @see #getTickDelta()
     */
    static float getTickDeltaOr0(World world) {
        if (world.isClient()) {
            return getTickDelta();
        }

        return 0;
    }

    static Quaternionf rotation(double3 radians) {
        return new Quaternionf().rotateXYZ(
                (float) radians.x(),
                (float) radians.y(),
                (float) radians.z()
        );
    }
}
