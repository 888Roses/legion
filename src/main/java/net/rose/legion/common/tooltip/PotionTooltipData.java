package net.rose.legion.common.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;

import java.util.ArrayList;
import java.util.List;

public class PotionTooltipData implements TooltipData {
    private final List<StatusEffectInstance> effects;
    private final double durationMultiplier;

    public PotionTooltipData(
            List<StatusEffectInstance> effects,
            double durationMultiplier
    ) {
        this.effects = effects;
        this.durationMultiplier = durationMultiplier;
    }

    public PotionTooltipData(
            ItemStack stack,
            double durationMultiplier
    ) {
        this(new ArrayList<>(), durationMultiplier);

        final var potionEffects = PotionUtil.getPotionEffects(stack);
        final var customPotionEffects = PotionUtil.getCustomPotionEffects(stack);

        this.effects.addAll(potionEffects);
        this.effects.addAll(customPotionEffects);
    }

    public double getDurationMultiplier() {
        return this.durationMultiplier;
    }

    public List<StatusEffectInstance> getEffects() {
        return this.effects;
    }
}
