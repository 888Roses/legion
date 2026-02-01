package dev.rosenoire.legion.client;

import dev.rosenoire.legion.client.tooltip.*;
import net.collectively.geode.GeodeClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import dev.rosenoire.legion.client.event.ArmorTooltipComponentCallback;
import dev.rosenoire.legion.client.event.PotionTooltipComponentCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Legion implements ClientModInitializer {
    public static final String MOD_ID = "legion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GeodeClient geode = GeodeClient.create(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing mod Legion!");
        LOGGER.info("Thanks for using my mod <3 - Rosenoire");

        ArmorTooltipComponent.registerPreviewHandler(new ArmorTrimTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new HarnessTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new HorseArmorTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new NautilusArmorTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new WolfArmorTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new HumanoidPreviewHandler());

        TooltipComponentCallback.EVENT.register(new PotionTooltipComponentCallback());
        TooltipComponentCallback.EVENT.register(new ArmorTooltipComponentCallback());
    }
}
