package dev.rosenoire.legion.common.tooltip;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;

import java.util.ArrayList;
import java.util.List;

public class PotionTooltipData implements TooltipData {
    private final List<StatusEffectInstance> effects;
    private final double durationMultiplier;
    private List<Float> chances;

    public PotionTooltipData(List<StatusEffectInstance> effects, double durationMultiplier) {
        this.effects = effects;
        this.durationMultiplier = durationMultiplier;

        chances = new ArrayList<>(effects.size());
        for (int i = 0; i < effects.size(); i++) chances.add(i, 1F);
    }

    public PotionTooltipData(ItemStack stack, double durationMultiplier) {
        this(new ArrayList<>(), durationMultiplier);
        PotionContentsComponent component = stack.get(DataComponentTypes.POTION_CONTENTS);
        if (component != null) component.forEachEffect(effects::add, 1);
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
