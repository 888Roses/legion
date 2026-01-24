package net.collectively.geode.mc._internal.index;

import net.collectively.geode.mc._internal.payload.AttackAwareItemOnAttackC2S;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public interface GeodeMinecraftPayloadRegistry {
    static void registerAll() {
        PayloadTypeRegistry.playC2S().register(AttackAwareItemOnAttackC2S.ID, AttackAwareItemOnAttackC2S.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(AttackAwareItemOnAttackC2S.ID, AttackAwareItemOnAttackC2S::receiveOnServer);
    }
}
