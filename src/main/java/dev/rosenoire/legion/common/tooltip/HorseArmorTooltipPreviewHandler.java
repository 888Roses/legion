package dev.rosenoire.legion.common.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import dev.rosenoire.legion.client.tooltip.ArmorTooltipComponent;
import org.jetbrains.annotations.Nullable;

public class HorseArmorTooltipPreviewHandler implements ArmorTooltipPreviewHandler{
    @Override
    public boolean validate(ItemStack itemStack, @Nullable EquipmentSlot slot) {
        if (slot == null) return false;
        return itemStack.isOf(Items.SADDLE)
                || itemStack.isOf(Items.LEATHER_HORSE_ARMOR)
                || itemStack.isOf(Items.COPPER_HORSE_ARMOR)
                || itemStack.isOf(Items.IRON_HORSE_ARMOR)
                || itemStack.isOf(Items.GOLDEN_HORSE_ARMOR)
                || itemStack.isOf(Items.DIAMOND_HORSE_ARMOR)
                || itemStack.isOf(Items.NETHERITE_HORSE_ARMOR);
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ItemStack itemStack = component.data().itemStack();
        HorseEntity entity = new HorseEntity(EntityType.HORSE, world);
        entity.equipStack(slot, itemStack);

        return new ArmorTooltipComponent.EntityInfo(entity, 16, 1.1f);
    }
}
