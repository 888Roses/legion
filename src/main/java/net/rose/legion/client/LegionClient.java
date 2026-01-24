package net.rose.legion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.rose.legion.client.event.ArmorTooltipComponentCallback;
import net.rose.legion.client.event.PotionTooltipComponentCallback;

public class LegionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TooltipComponentCallback.EVENT.register(new PotionTooltipComponentCallback());
        TooltipComponentCallback.EVENT.register(new ArmorTooltipComponentCallback());
    }
}
