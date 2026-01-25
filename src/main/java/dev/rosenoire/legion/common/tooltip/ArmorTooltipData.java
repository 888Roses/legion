package dev.rosenoire.legion.common.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;

public record ArmorTooltipData(ItemStack itemStack) implements TooltipData {
}
