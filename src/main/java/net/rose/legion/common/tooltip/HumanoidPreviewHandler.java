package net.rose.legion.common.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientMannequinEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import org.jetbrains.annotations.Nullable;

public class HumanoidPreviewHandler implements ArmorTooltipPreviewHandler {
    @Override
    public boolean validate(ItemStack itemStack, EquipmentSlot slot) {
        return slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR || slot == EquipmentSlot.OFFHAND;
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ClientMannequinEntity livingEntity = new ClientMannequinEntity(world, client.getPlayerSkinCache());
        livingEntity.equipStack(slot, component.data().itemStack());

        float scale = 32f;
        float verticalOffset = 0f;

        if (slot == EquipmentSlot.HEAD) verticalOffset = 1.475f;
        if (slot == EquipmentSlot.CHEST) verticalOffset = 1.025f;
        if (slot == EquipmentSlot.LEGS) verticalOffset = 0.425f;
        if (slot == EquipmentSlot.FEET) verticalOffset = 0.125f;
        if (slot == EquipmentSlot.OFFHAND) {
            scale = 28;
            verticalOffset = 0.8f;
        }

        return new ArmorTooltipComponent.EntityInfo(livingEntity, scale, verticalOffset);
    }

    @Override
    public void modifyRenderState(ArmorTooltipComponent component, EntityRenderState renderState) {
        if (renderState instanceof PlayerEntityRenderState playerEntityRenderState) {
            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayer = client.player;

            if (clientPlayer == null) {
                return;
            }

            playerEntityRenderState.skinTextures = clientPlayer.getSkin();
            playerEntityRenderState.bodyYaw = 0;
            playerEntityRenderState.relativeHeadYaw = 0;
        }
    }
}
