package dev.rosenoire.legion.client.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;

public class WolfArmorTooltipPreviewHandler implements ArmorTooltipPreviewHandler{
    @Override
    public boolean validate(ItemStack itemStack, @Nullable EquipmentSlot slot) {
        if (slot == null) {
            return false;
        }

        return itemStack.isOf(Items.WOLF_ARMOR);
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ItemStack itemStack = component.data().itemStack();
        WolfEntity entity = new WolfEntity(EntityType.WOLF, world);
        entity.equipStack(slot, itemStack);

        return new ArmorTooltipComponent.EntityInfo(entity, 28, 0.5f);
    }
}
