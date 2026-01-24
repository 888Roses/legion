package net.collectively.geode.mc._internal.payload;

import net.collectively.geode.Geode;
import net.collectively.geode.mc.item.AttackAwareItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record AttackAwareItemOnAttackC2S() implements CustomPayload {
    private static final Identifier IDENTIFIER = Geode.internalId("attack_aware_item_on_attack_c2s");
    public static final Id<AttackAwareItemOnAttackC2S> ID = new Id<>(IDENTIFIER);

    public static final PacketCodec<PacketByteBuf, AttackAwareItemOnAttackC2S> CODEC = PacketCodec.of(
            (value, buf) -> {
            },
            buf -> new AttackAwareItemOnAttackC2S()
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receiveOnServer(ServerPlayNetworking.Context context) {
        ServerPlayerEntity serverPlayerEntity = context.player();
        ItemStack itemStack = serverPlayerEntity.getMainHandStack();

        if (itemStack.getItem() instanceof AttackAwareItem attackAwareItem) {
            attackAwareItem.onAttack(serverPlayerEntity, itemStack);
        }
    }
}
