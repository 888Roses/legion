package net.rose.legion.mixin;

import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(PotionContentsComponent.class)
public class PotionContentsComponentMixin {
    @Inject(method = "buildTooltip", at = @At("HEAD"), cancellable = true)
    private static void legion$appendTooltip(Iterable<StatusEffectInstance> effects, Consumer<Text> textConsumer, float durationMultiplier, float tickRate, CallbackInfo ci) {
        ci.cancel();
    }
}
