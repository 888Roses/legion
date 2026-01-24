package net.collectively.geode.mixin;

import net.collectively.geode.mc._internal.payload.AttackAwareItemOnAttackC2S;
import net.collectively.geode.mc.item.AttackAwareItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class AttackAwareClientAttackMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "doAttack")
    private void doAttack$attackAwareItemOnAttack(CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity clientPlayer = this.player;

        if (clientPlayer != null) {
            ItemStack itemStack = clientPlayer.getMainHandStack();
            if (itemStack.getItem() instanceof AttackAwareItem attackAwareItem) {
                attackAwareItem.onAttack(clientPlayer, itemStack);
                ClientPlayNetworking.send(new AttackAwareItemOnAttackC2S());
            }
        }
    }
}
