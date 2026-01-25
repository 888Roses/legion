package net.rose.legion.common.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import org.jetbrains.annotations.Nullable;

public class HarnessTooltipPreviewHandler implements ArmorTooltipPreviewHandler {
    @Override
    public boolean validate(ItemStack itemStack, EquipmentSlot slot) {
        return itemStack.isIn(ItemTags.HARNESSES);
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ItemStack itemStack = component.data().itemStack();
        HappyGhastEntity entity = new HappyGhastEntity(EntityType.HAPPY_GHAST, world);
        entity.equipStack(slot, itemStack);

        return new ArmorTooltipComponent.EntityInfo(entity, 6, 1f);
    }
}
