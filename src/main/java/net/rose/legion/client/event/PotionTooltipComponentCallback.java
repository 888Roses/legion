package net.rose.legion.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.rose.legion.client.tooltip.PotionTooltipComponent;
import net.rose.legion.common.tooltip.PotionTooltipData;
import org.jetbrains.annotations.Nullable;

public class PotionTooltipComponentCallback implements TooltipComponentCallback {
    @Override
    public @Nullable TooltipComponent getComponent(TooltipData tooltipData) {
        if (tooltipData instanceof PotionTooltipData potionTooltipData) {
            return new PotionTooltipComponent(potionTooltipData);
        }

        return null;
    }
}
