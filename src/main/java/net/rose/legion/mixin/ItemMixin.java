package net.rose.legion.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.rose.legion.common.tooltip.ArmorTooltipData;
import net.rose.legion.common.tooltip.PotionTooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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

        SuspiciousStewEffectsComponent suspiciousStewComponent = itemStack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
        if (suspiciousStewComponent != null) {
            List<StatusEffectInstance> stewEffects = suspiciousStewComponent.effects()
                    .stream()
                    .map(SuspiciousStewEffectsComponent.StewEffect::createStatusEffectInstance)
                    .toList();

            cir.setReturnValue(Optional.of(new PotionTooltipData(stewEffects, 1)));
        }

        ConsumableComponent consumableComponent = itemStack.get(DataComponentTypes.CONSUMABLE);
        if (consumableComponent != null) {
            List<ConsumeEffect> consumeEffects = consumableComponent.onConsumeEffects();
            if (consumeEffects != null && !consumeEffects.isEmpty()) {
                List<StatusEffectInstance> statusEffectInstances = new ArrayList<>();
                List<Float> chances = new ArrayList<>();

                for (ConsumeEffect consumeEffect : consumeEffects) {
                    if (consumeEffect instanceof ApplyEffectsConsumeEffect applyEffectsConsumeEffect) {
                        applyEffectsConsumeEffect.effects().forEach(x -> {
                            statusEffectInstances.add(x);
                            chances.add(applyEffectsConsumeEffect.probability());
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

        EquippableComponent equippableComponent = itemStack.get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null) {
            cir.setReturnValue(Optional.of(new ArmorTooltipData(itemStack)));
        }
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"), cancellable = true)
    private void legion$appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type, CallbackInfo ci) {
        if (stack.get(DataComponentTypes.POTION_CONTENTS) != null || stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS) != null) {
            ci.cancel();
        }
    }
}
