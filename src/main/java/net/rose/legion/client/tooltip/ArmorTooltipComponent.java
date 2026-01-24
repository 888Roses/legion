package net.rose.legion.client.tooltip;

import net.collectively.geode.core.math;
import net.collectively.geode.core.types.double3;
import net.collectively.geode.mc.util.RenderHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.network.ClientMannequinEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HappyGhastEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.NautilusEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;
import net.rose.legion.common.tooltip.ArmorTooltipData;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public record ArmorTooltipComponent(ArmorTooltipData armorTooltipData) implements TooltipComponent {
    @Override
    public int getHeight(TextRenderer textRenderer) {
        return 32;
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

        EquippableComponent equippableComponent = armorTooltipData.itemStack().get(DataComponentTypes.EQUIPPABLE);
        if (equippableComponent != null) {
            EquipmentSlot slot = equippableComponent.slot();

            LivingEntity livingEntity = null;
            float scale = 1;
            float yOffset = 0;

            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR || slot == EquipmentSlot.OFFHAND) {
                livingEntity = new ClientMannequinEntity(world, client.getPlayerSkinCache());

                scale = 32;

                if (slot == EquipmentSlot.HEAD) yOffset = 1.475f;
                if (slot == EquipmentSlot.CHEST) yOffset = 1.025f;
                if (slot == EquipmentSlot.LEGS) yOffset = 0.425f;
                if (slot == EquipmentSlot.FEET) yOffset = 0.125f;
                if (slot == EquipmentSlot.OFFHAND) {
                    scale = 28;
                    yOffset = 0.8f;
                }
            }

            if (armorTooltipData.itemStack().isIn(ItemTags.HARNESSES)) {
                livingEntity = new HappyGhastEntity(EntityType.HAPPY_GHAST, world);

                scale = 6;
                yOffset = 1f;
            }

            if (armorTooltipData.itemStack().isOf(Items.SADDLE)
                    || armorTooltipData.itemStack().isOf(Items.LEATHER_HORSE_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.COPPER_HORSE_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.IRON_HORSE_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.GOLDEN_HORSE_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.DIAMOND_HORSE_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.NETHERITE_HORSE_ARMOR)) {
                livingEntity = new HorseEntity(EntityType.HORSE, world);
                scale = 16;
                yOffset = 1.1f;
            }

            if (armorTooltipData.itemStack().isOf(Items.COPPER_NAUTILUS_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.IRON_NAUTILUS_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.GOLDEN_NAUTILUS_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.DIAMOND_NAUTILUS_ARMOR)
                    || armorTooltipData.itemStack().isOf(Items.NETHERITE_NAUTILUS_ARMOR)) {
                livingEntity = new NautilusEntity(EntityType.NAUTILUS, world);
                scale = 20;
                yOffset = 0.5f;
            }

            if (livingEntity == null) {
                return;
            }

            livingEntity.equipStack(slot, armorTooltipData.itemStack());

            EntityRenderState renderState = drawEntity(livingEntity);
            if (renderState instanceof LivingEntityRenderState livingEntityRenderState) {
                livingEntityRenderState.bodyYaw = 0;
                livingEntityRenderState.relativeHeadYaw = 0;

                if (livingEntityRenderState instanceof PlayerEntityRenderState playerRenderState) {
                    playerRenderState.skinTextures = clientPlayer.getSkin();
                }
            }

            double smoothTime = math.lerp(RenderHelper.getTickDelta(), world.getTime() - 1, world.getTime());
            Quaternionf rotation = RenderHelper.rotation(new double3(25, smoothTime, 180).modify(math::deg2rad));
            Quaternionf cameraAngle = RenderHelper.rotation(new double3(0, 0, 0).modify(math::deg2rad));

            context.addEntity(
                    renderState, scale, new Vector3f(0f, yOffset, 0f),
                    rotation, cameraAngle,
                    x, y, x + getWidth(textRenderer), y + getHeight(textRenderer)
            );
        }
    }

    private static @Nullable EntityRenderState drawEntity(LivingEntity entity) {
        EntityRenderManager entityRenderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderManager.getRenderer(entity);
        if (entityRenderer == null) return null;
        EntityRenderState entityRenderState = entityRenderer.getAndUpdateRenderState(entity, 1.0F);
        entityRenderState.light = LightmapTextureManager.MAX_LIGHT_COORDINATE;
        entityRenderState.shadowPieces.clear();
        entityRenderState.outlineColor = 0;
        return entityRenderState;
    }
}
