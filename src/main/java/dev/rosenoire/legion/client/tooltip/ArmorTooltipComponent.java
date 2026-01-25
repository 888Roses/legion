package dev.rosenoire.legion.client.tooltip;

import net.collectively.geode.core.math;
import net.collectively.geode.core.types.double3;
import net.collectively.geode.mc.util.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import dev.rosenoire.legion.common.tooltip.ArmorTooltipData;
import dev.rosenoire.legion.common.tooltip.ArmorTooltipPreviewHandler;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public record ArmorTooltipComponent(ArmorTooltipData data) implements TooltipComponent {
    private static final List<ArmorTooltipPreviewHandler> PREVIEW_HANDLERS = new ArrayList<>();
    public static final EquipmentSlot BASE_EQUIPMENT_SLOT = null;
    private static final int BASE_HEIGHT = 32;

    public static void registerPreviewHandler(ArmorTooltipPreviewHandler handler) {
        PREVIEW_HANDLERS.add(handler);
    }

    public static @Nullable ArmorTooltipPreviewHandler getTooltipPreviewHandler(ItemStack itemStack) {
        EquippableComponent equippableComponent = itemStack.get(DataComponentTypes.EQUIPPABLE);
        EquipmentSlot slot = equippableComponent == null ? BASE_EQUIPMENT_SLOT : equippableComponent.slot();

        for (ArmorTooltipPreviewHandler handler : PREVIEW_HANDLERS) {
            if (handler.validate(itemStack, slot)) {
                return handler;
            }
        }

        return null;
    }

    @Override
    public int getHeight(TextRenderer textRenderer) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayer = client.player;
        World world = client.world;

        if (world == null || clientPlayer == null) {
            return BASE_HEIGHT;
        }

        EquippableComponent equippableComponent = data.itemStack().get(DataComponentTypes.EQUIPPABLE);
        EquipmentSlot slot = BASE_EQUIPMENT_SLOT;

        if (equippableComponent != null) {
            slot = equippableComponent.slot();
        }

        ArmorTooltipPreviewHandler handler = getTooltipPreviewHandler(data().itemStack());
        return BASE_HEIGHT + (handler == null ? 0 : handler.getHeight(this, slot).orElse(0));
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 32 + 16;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, int width, int height, DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayer = client.player;
        World world = client.world;

        if (world == null || clientPlayer == null) {
            return;
        }

        EquippableComponent equippableComponent = data.itemStack().get(DataComponentTypes.EQUIPPABLE);
        EquipmentSlot slot = BASE_EQUIPMENT_SLOT;

        if (equippableComponent != null) {
            slot = equippableComponent.slot();
        }

        EntityInfo entityInfo = null;
        ArmorTooltipPreviewHandler validHandler = null;

        for (ArmorTooltipPreviewHandler handler : PREVIEW_HANDLERS) {
            if (handler.validate(data.itemStack(), slot)) {
                entityInfo = handler.getEntityInfo(this, slot);
                validHandler = handler;
                break;
            }
        }

        if (entityInfo == null) {
            return;
        }

        EntityRenderState entityRenderState = entityInfo.renderState();
        if (entityRenderState == null) {
            return;
        }

        validHandler.modifyRenderState(this, entityRenderState);

        double smoothTime = math.lerp(RenderHelper.getTickDelta(), world.getTime() - 1, world.getTime());
        Quaternionf rotation = RenderHelper.rotation(new double3(25, smoothTime, 180).modify(math::deg2rad));
        Quaternionf cameraAngle = RenderHelper.rotation(new double3(0, 0, 0).modify(math::deg2rad));

        context.addEntity(
                entityRenderState, entityInfo.scale(), new Vector3f(0f, entityInfo.verticalOffset(), 0f),
                rotation, cameraAngle,
                x, y, x + getWidth(textRenderer), y + getHeight(textRenderer)
        );
    }

    public record EntityInfo(LivingEntity livingEntity, float scale, float verticalOffset) {
        public @Nullable EntityRenderState renderState() {
            EntityRenderManager entityRenderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();
            EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderManager.getRenderer(livingEntity);

            if (entityRenderer == null) {
                return null;
            }

            EntityRenderState renderState = entityRenderer.getAndUpdateRenderState(livingEntity, RenderHelper.getTickDelta());
            renderState.light = LightmapTextureManager.MAX_LIGHT_COORDINATE;
            renderState.shadowPieces.clear();
            renderState.outlineColor = 0;

            return renderState;
        }
    }
}
