package dev.rosenoire.legion.common;

import net.collectively.geode.Geode;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipComponent;
import dev.rosenoire.legion.common.tooltip.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Legion implements ModInitializer {
    public static final String MOD_ID = "legion";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing mod Legion!");
        LOGGER.info("Thanks for using my mod <3 - Rosenoire");

        ArmorTooltipComponent.registerPreviewHandler(new ArmorTrimTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new HarnessTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new HorseArmorTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new NautilusArmorTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new WolfArmorTooltipPreviewHandler());
        ArmorTooltipComponent.registerPreviewHandler(new HumanoidPreviewHandler());

        Geode.setHookedMod(MOD_ID);
    }
}
