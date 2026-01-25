package net.rose.legion.common.tooltip;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ArmorTooltipPreviewHandler {
    boolean validate(ItemStack itemStack, @Nullable EquipmentSlot slot);

    @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot);

    default void modifyRenderState(ArmorTooltipComponent component, EntityRenderState renderState) {
    }

    default Optional<Integer> getHeight(ArmorTooltipComponent component, EquipmentSlot slot) {
        return Optional.empty();
    }
}
