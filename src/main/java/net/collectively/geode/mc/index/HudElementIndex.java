package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;

/**
 * Index of HUD layers to easily register to be drawn.
 */
@SuppressWarnings("unused")
public interface HudElementIndex {
    /**
     * Registers a new {@link HudElement} after the {@link VanillaHudElements#HOTBAR} HUD layer.
     *
     * @param identifier The unique {@link Identifier} of that layer.
     * @param element    The layer to register.
     */
    static <T extends HudElement> T registerHotbar(Identifier identifier, T element) {
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, identifier, element);
        return element;
    }

    /**
     * Registers a new {@link HudElement} after the {@link VanillaHudElements#HOTBAR} HUD layer.
     *
     * @param identifier The unique identifier of that layer, belonging to the {@link Geode#HOOKED_MOD_ID} namespace.
     * @param element    The layer to register.
     */
    static <T extends HudElement> T registerHotbar(String identifier, T element) {
        return registerHotbar(Geode.id(identifier), element);
    }
}
