package dev.rosenoire.legion.mixin;

import com.google.common.collect.ImmutableList;
import dev.rosenoire.legion.client.config.LegionConfig;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.tooltip.TooltipData;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipComponent;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipData;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipPreviewHandler;
import dev.rosenoire.legion.client.tooltip.PotionTooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "getTooltipData", at = @At("TAIL"), cancellable = true)
    private void legion$getTooltipData(ItemStack itemStack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        if (itemStack.getItem() instanceof PotionItem || itemStack.getItem() instanceof TippedArrowItem) {
            cir.setReturnValue(Optional.of(new PotionTooltipData(
                    itemStack,
                    itemStack.getItem() instanceof TippedArrowItem ? 0.125 : 1
            )));
        }

        if (LegionConfig.showSuspiciousStewInfo) {
            SuspiciousStewEffectsComponent suspiciousStewComponent = itemStack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
            if (suspiciousStewComponent != null) {
                List<StatusEffectInstance> stewEffects = suspiciousStewComponent.effects()
                        .stream()
                        .map(SuspiciousStewEffectsComponent.StewEffect::createStatusEffectInstance)
                        .toList();

                cir.setReturnValue(Optional.of(new PotionTooltipData(stewEffects, 1)));
            }
        }

        ConsumableComponent consumableComponent = itemStack.get(DataComponentTypes.CONSUMABLE);
        if (consumableComponent != null) {
            List<ConsumeEffect> consumeEffects = consumableComponent.onConsumeEffects();
            if (consumeEffects != null && !consumeEffects.isEmpty()) {
                List<StatusEffectInstance> statusEffectInstances = new ArrayList<>();
                List<Float> chances = new ArrayList<>();

                for (ConsumeEffect consumeEffect : consumeEffects) {
                    if (consumeEffect instanceof ApplyEffectsConsumeEffect(List<StatusEffectInstance> effects, float probability)) {
                        effects.forEach(x -> {
                            statusEffectInstances.add(x);
                            chances.add(probability);
                        });
                    }
                }

                cir.setReturnValue(Optional.of(new PotionTooltipData(statusEffectInstances, 1).withChances(chances)));
            }
        }

        if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
            cir.setReturnValue(Optional.of(new PotionTooltipData(
                    ImmutableList.<StatusEffectInstance>builder()
                            .add(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1))
                            .add(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1))
                            .add(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0))
                            .build(),
                    1
            )));
        }

        ArmorTooltipPreviewHandler armorTooltipPreviewHandler = ArmorTooltipComponent.getTooltipPreviewHandler(itemStack);
        if (armorTooltipPreviewHandler != null) cir.setReturnValue(Optional.of(new ArmorTooltipData(itemStack)));
    }
}
