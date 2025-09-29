package net.rose.legion.client;

import net.fabricmc.api.ClientModInitializer;
import net.rose.legion.client.event.PotionTooltipComponentCallback;

public class LegionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PotionTooltipComponentCallback.EVENT.register(new PotionTooltipComponentCallback());
    }
}
