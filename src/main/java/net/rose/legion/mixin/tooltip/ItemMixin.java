package net.rose.legion.mixin.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
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
    }

    @Unique
    private static ArrayList<StatusEffectInstance> getStewEffects(ItemStack stew) {
        final var list = new ArrayList<StatusEffectInstance>();

        NbtCompound nbtCompound = stew.getNbt();
        if (nbtCompound != null && nbtCompound.contains("Effects", NbtElement.LIST_TYPE)) {
            NbtList nbtList = nbtCompound.getList("Effects", NbtElement.COMPOUND_TYPE);

            for(int i = 0; i < nbtList.size(); ++i) {
                NbtCompound nbtCompound2 = nbtList.getCompound(i);
                int j;
                if (nbtCompound2.contains("EffectDuration", NbtElement.NUMBER_TYPE)) {
                    j = nbtCompound2.getInt("EffectDuration");
                } else {
                    j = 160;
                }

                StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getInt("EffectId"));
                if (statusEffect != null) {
                    list.add(new StatusEffectInstance(statusEffect, j));
                }
            }
        }

        return list;
    }
}
