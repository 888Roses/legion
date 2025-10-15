package net.rose.legion.mixin.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "isFireproof", at = @At("HEAD"), cancellable = true)
    private void isFireproof$legion(CallbackInfoReturnable<Boolean> cir) {
        final var item = (Item) (Object) this;
        Item[] fireproofItems = {
                Items.BLAZE_POWDER, Items.BLAZE_ROD,
                Items.MAGMA_CREAM, Items.MAGMA_BLOCK,
                Items.MUSIC_DISC_PIGSTEP, Items.ENCHANTED_GOLDEN_APPLE,
                Items.OBSIDIAN, Items.CRYING_OBSIDIAN,
                Items.DRAGON_EGG, Items.WITHER_SKELETON_SKULL
        };

        if (Arrays.stream(fireproofItems).anyMatch(other -> item == other)) {
            cir.setReturnValue(true);
        }
    }
}