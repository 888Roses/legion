package net.rose.legion.mixin.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
        DrawContext.class
)
public abstract class DrawContextMixin {
    @Shadow
    @Final
    private MatrixStack matrices;

    @Shadow
    public abstract int drawText(
            TextRenderer textRenderer,
            Text text,
            int x,
            int y,
            int color,
            boolean shadow
    );

    @Inject(
            method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getItemCooldownManager()Lnet/minecraft/entity/player/ItemCooldownManager;"
            )
    )
    private void drawItem$maidens_departure(
            TextRenderer textRenderer,
            ItemStack stack,
            int x,
            int y,
            String countOverride,
            CallbackInfo ci
    ) {
        final var player = MinecraftClient.getInstance().player;

        if (player == null) {
            return;
        }

        final var cooldownManager = player.getItemCooldownManager();

        if (!cooldownManager.isCoolingDown(stack.getItem())) {
            return;
        }

        matrices.translate(0.0F, 0.0F, 200.0F);

        final var entry = cooldownManager.entries.get(stack.getItem());
        final var cooldown = entry.endTick - cooldownManager.tick;

        var text = "";
        final var cooldownDurationSeconds = cooldown / 20d;

        if (cooldownDurationSeconds > 60) {
            final var cooldownDurationMinutes = cooldownDurationSeconds / 60d;
            text = Math.round(cooldownDurationMinutes) + "m";
        } else {
            text = Math.round(cooldownDurationSeconds) + "s";
        }

        this.drawText(
                textRenderer,
                Text.literal(text),
                x + 1,
                y + 1,
                0xFFFFFFFF,
                true
        );
    }
}