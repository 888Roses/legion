package dev.rosenoire.legion.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import dev.rosenoire.legion.client.tooltip.PotionTooltipComponent;
import dev.rosenoire.legion.client.tooltip.PotionTooltipData;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class PotionTooltipComponentCallback implements TooltipComponentCallback {
    @Override
    public @Nullable TooltipComponent getComponent(@NonNull TooltipData tooltipData) {
        if (tooltipData instanceof PotionTooltipData potionTooltipData) {
            return new PotionTooltipComponent(potionTooltipData);
        }

        return null;
    }
}
