package net.rose.legion.mixin.tooltip;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(
        TippedArrowItem.class
)
public class TippedArrowItemMixin {
    @Inject(
            method = "appendTooltip",
            at = @At("HEAD"),
            cancellable = true
    )
    private void legion$appendTooltip(
            ItemStack stack,
            @Nullable World world,
            List<Text> tooltip,
            TooltipContext context,
            CallbackInfo ci
    ) {
        if (PotionUtil.getPotionEffects(stack).isEmpty()) {
            return;
        }

        ci.cancel();
    }
}
