package net.rose.legion.common.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;

import java.util.ArrayList;
import java.util.List;

public class PotionTooltipData implements TooltipData {
    private final List<StatusEffectInstance> effects;
    private List<Float> chances;
    private final double durationMultiplier;

    public PotionTooltipData(
            List<StatusEffectInstance> effects,
            double durationMultiplier
    ) {
        this.effects = effects;
        this.durationMultiplier = durationMultiplier;

        this.chances = new ArrayList<>(effects.size());
        for (var i = 0; i < effects.size(); i++) {
            this.chances.add(i, 1F);
        }
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

    public PotionTooltipData withChances(List<Float> chances) {
        this.chances = chances;
        return this;
    }

    public List<Float> getChances() {
        return this.chances;
    }

    public double getDurationMultiplier() {
        return this.durationMultiplier;
    }

    public List<StatusEffectInstance> getEffects() {
        return this.effects;
    }

    public float getChance(StatusEffectInstance instance) {
        if (this.chances.isEmpty() || this.effects.isEmpty()) {
            return 1;
        }

        if (this.effects.size() != this.chances.size()) {
            return 1;
        }

        if (this.effects.contains(instance)) {
            return this.chances.get(this.effects.indexOf(instance));
        }

        return 1;
    }
}
