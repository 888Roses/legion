package net.rose.legion.common.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.NautilusEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import org.jetbrains.annotations.Nullable;

public class NautilusArmorTooltipPreviewHandler implements ArmorTooltipPreviewHandler {
    @Override
    public boolean validate(ItemStack itemStack, EquipmentSlot slot) {
        return itemStack.isOf(Items.COPPER_NAUTILUS_ARMOR)
                || itemStack.isOf(Items.IRON_NAUTILUS_ARMOR)
                || itemStack.isOf(Items.GOLDEN_NAUTILUS_ARMOR)
                || itemStack.isOf(Items.DIAMOND_NAUTILUS_ARMOR)
                || itemStack.isOf(Items.NETHERITE_NAUTILUS_ARMOR);
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ItemStack itemStack = component.data().itemStack();
        NautilusEntity entity = new NautilusEntity(EntityType.NAUTILUS, world);
        entity.equipStack(slot, itemStack);

        return new ArmorTooltipComponent.EntityInfo(entity, 20, 0.5f);
    }
}
