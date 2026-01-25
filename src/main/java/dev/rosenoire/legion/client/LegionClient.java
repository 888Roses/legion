package dev.rosenoire.legion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import dev.rosenoire.legion.client.event.ArmorTooltipComponentCallback;
import dev.rosenoire.legion.client.event.PotionTooltipComponentCallback;

public class LegionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooltipComponentCallback.EVENT.register(new PotionTooltipComponentCallback());
        TooltipComponentCallback.EVENT.register(new ArmorTooltipComponentCallback());
    }
}
