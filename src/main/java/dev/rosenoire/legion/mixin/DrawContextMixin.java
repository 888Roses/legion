package dev.rosenoire.legion.mixin;

import dev.rosenoire.legion.client.config.LegionConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow
    public abstract void drawText(TextRenderer textRenderer, Text text, int x, int y, int color, boolean shadow);

    @Inject(method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCooldownProgress(Lnet/minecraft/item/ItemStack;II)V"))
    private void legion$drawStackOverlay(TextRenderer textRenderer, ItemStack itemStack, int x, int y, String stackCountText, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        if (LegionConfig.showCooldownInfo) {
            ItemCooldownManager cooldownManager = player.getItemCooldownManager();
            if (!cooldownManager.isCoolingDown(itemStack)) return;

            ItemCooldownManager.Entry entry = cooldownManager.entries.get(cooldownManager.getGroup(itemStack));
            int cooldown = entry.endTick - cooldownManager.tick;
            double cooldownDurationSeconds = cooldown / 20d;

            String text;
            if (cooldownDurationSeconds > 60) {
                double cooldownDurationMinutes = cooldownDurationSeconds / 60d;
                text = Math.round(cooldownDurationMinutes) + "m";
            } else text = Math.round(cooldownDurationSeconds) + "s";

            drawText(textRenderer, Text.literal(text), x, y, 0xffFFFFFF, true);
        }
    }
}