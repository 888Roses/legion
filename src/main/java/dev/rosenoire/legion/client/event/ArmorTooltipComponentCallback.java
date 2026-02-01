package dev.rosenoire.legion.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.item.tooltip.TooltipData;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipComponent;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipData;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class ArmorTooltipComponentCallback implements TooltipComponentCallback {
    @Override
    public @Nullable TooltipComponent getComponent(@NonNull TooltipData tooltipData) {
        return tooltipData instanceof ArmorTooltipData armorTooltipData
                ? new ArmorTooltipComponent(armorTooltipData)
                : null;
    }
}
