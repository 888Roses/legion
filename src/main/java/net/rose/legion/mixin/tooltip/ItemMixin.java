package net.rose.legion.mixin.tooltip;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtElement;
import net.rose.legion.common.tooltip.PotionTooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Optional;

@Mixin(
        Item.class
)
public class ItemMixin {
    @Inject(
            method = "getTooltipData",
            at = @At("TAIL"),
            cancellable = true
    )
    private void legion$getTooltipData(
            ItemStack itemStack,
            CallbackInfoReturnable<Optional<TooltipData>> cir
    ) {
        if (itemStack.getItem() instanceof PotionItem || itemStack.getItem() instanceof TippedArrowItem) {
            cir.setReturnValue(Optional.of(new PotionTooltipData(
                    itemStack,
                    itemStack.getItem() instanceof TippedArrowItem ? 0.125 : 1
            )));
        }

        if (itemStack.getItem() instanceof SuspiciousStewItem suspiciousStewItem) {
            cir.setReturnValue(Optional.of(new PotionTooltipData(
                    getStewEffects(itemStack),
                    1
            )));
        }

        if (itemStack.getItem().isFood()) {
            final var foodComponent = itemStack.getItem().getFoodComponent();
            if (foodComponent != null) {
                final var statusEffectPairs = foodComponent.getStatusEffects();
                if (statusEffectPairs != null && !statusEffectPairs.isEmpty()) {

                    cir.setReturnValue(Optional.of(
                            new PotionTooltipData(
                                    statusEffectPairs
                                            .stream()
                                            .map(Pair::getFirst)
                                            .toList(),
                                    1
                            ).withChances(
                                    statusEffectPairs
                                            .stream()
                                            .map(Pair::getSecond)
                                            .toList()
                            )
                    ));
                }
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
    }

    @Unique
    private static ArrayList<StatusEffectInstance> getStewEffects(ItemStack stew) {
        final var list = new ArrayList<StatusEffectInstance>();
        final var nbt = stew.getNbt();

        if (nbt == null) {
            return list;
        }

        if (nbt.contains("Effects", NbtElement.LIST_TYPE)) {
            final var effects = nbt.getList("Effects", NbtElement.COMPOUND_TYPE);

            for (var i = 0; i < effects.size(); ++i) {
                final var effectNbt = effects.getCompound(i);
                var effectDuration = 0;

                if (effectNbt.contains("EffectDuration", NbtElement.NUMBER_TYPE)) {
                    effectDuration = effectNbt.getInt("EffectDuration");
                }
                else {
                    effectDuration = 160;
                }

                final var effect = StatusEffect.byRawId(effectNbt.getInt("EffectId"));

                if (effect == null) {
                    continue;
                }

                list.add(new StatusEffectInstance(effect, effectDuration));
            }
        }

        return list;
    }
}
