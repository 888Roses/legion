package net.rose.legion.common.tooltip;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.EquipmentSlot;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import org.jetbrains.annotations.Nullable;

public interface ArmorTooltipPreviewHandler {
    boolean validate(ArmorTooltipComponent component, EquipmentSlot slot);
    @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot);
    default void modifyRenderState(ArmorTooltipComponent component, EntityRenderState renderState) {}
}
