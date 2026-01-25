package net.rose.legion.common.tooltip;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientMannequinEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import net.rose.legion.common.Legion;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ArmorTrimTooltipPreviewHandler implements ArmorTooltipPreviewHandler {
    public static final int SWAP_SPEED = 40;

    @Override
    public boolean validate(ItemStack itemStack, @Nullable EquipmentSlot slot) {
        if (slot != null) return false;
        return itemStack.getItem() instanceof SmithingTemplateItem;
    }

    @Override
    public Optional<Integer> getHeight(ArmorTooltipComponent component, EquipmentSlot slot) {
        return Optional.of(48);
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ClientMannequinEntity livingEntity = new ClientMannequinEntity(world, client.getPlayerSkinCache());
        ItemStack armorTrimStack = component.data().itemStack();
        if (armorTrimStack.getItem() instanceof SmithingTemplateItem) {
            if (armorTrimStack.isOf(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)) {
                return null;
            }

            getRandomItemInTag(world, ItemTags.HEAD_ARMOR, 0).ifPresent(itemStack -> {
                itemStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, armorTrimStack, 0));
                livingEntity.equipStack(EquipmentSlot.HEAD, itemStack);
            });

            getRandomItemInTag(world, ItemTags.CHEST_ARMOR, 1).ifPresent(itemStack -> {
                itemStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, armorTrimStack, 1));
                livingEntity.equipStack(EquipmentSlot.CHEST, itemStack);
            });

            getRandomItemInTag(world, ItemTags.LEG_ARMOR, 2).ifPresent(itemStack -> {
                itemStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, armorTrimStack, 2));
                livingEntity.equipStack(EquipmentSlot.LEGS, itemStack);
            });

            getRandomItemInTag(world, ItemTags.FOOT_ARMOR, 3).ifPresent(itemStack -> {
                itemStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, armorTrimStack, 3));
                livingEntity.equipStack(EquipmentSlot.FEET, itemStack);
            });
        }

        float scale = 32f;
        float verticalOffset = 0.8f;

        return new ArmorTooltipComponent.EntityInfo(livingEntity, scale, verticalOffset);
    }

    private static Optional<ItemStack> getRandomItemInTag(World world, TagKey<Item> tag, int equipmentOffset) {
        Optional<RegistryEntryList.Named<Item>> headArmors = Registries.ITEM.getOptional(tag);
        if (headArmors.isPresent()) {
            RegistryEntryList.Named<Item> tagContent = headArmors.get();
            int index = (int) ((world.getTime() / SWAP_SPEED + equipmentOffset) % tagContent.size());
            RegistryEntry<Item> item = tagContent.get(index);
            return Optional.of(new ItemStack(item));
        }

        return Optional.empty();
    }

    private static @Nullable ArmorTrim getRandomMaterialTrim(ClientWorld world, ItemStack itemStack, int materialOffset) {
        DynamicRegistryManager registries = world.getRegistryManager();
        Optional<Registry<ArmorTrimPattern>> potentialTrimPatternRegistry = registries.getOptional(RegistryKeys.TRIM_PATTERN);

        if (potentialTrimPatternRegistry.isPresent()) {
            Registry<ArmorTrimPattern> trimPatternRegistry = potentialTrimPatternRegistry.get();

            Identifier itemIdentifier = Registries.ITEM.getId(itemStack.getItem());
            String armorTrimPatternPath = itemIdentifier.getPath().replace("_armor_trim_smithing_template", "");
            Identifier armorTrimPatternIdentifier = Identifier.of(itemIdentifier.getNamespace(), armorTrimPatternPath);

            ArmorTrimPattern armorTrim = trimPatternRegistry.get(armorTrimPatternIdentifier);
            if (armorTrim != null) {
                Optional<Registry<ArmorTrimMaterial>> potentialTrimMaterialRegistry = registries.getOptional(RegistryKeys.TRIM_MATERIAL);
                if (potentialTrimMaterialRegistry.isPresent()) {
                    Registry<ArmorTrimMaterial> trimMaterialRegistry = potentialTrimMaterialRegistry.get();

                    int index = (int) ((world.getTime() / SWAP_SPEED + materialOffset) % trimMaterialRegistry.size());
                    ArmorTrimMaterial material = trimMaterialRegistry.get(index);

                    if (material == null) {
                        Legion.LOGGER.error("Could not load trim material at index {}/{}!", index, trimMaterialRegistry.size());
                        return null;
                    }

                    return new ArmorTrim(trimMaterialRegistry.getEntry(material), trimPatternRegistry.getEntry(armorTrim));
                }
            }
        }

        return null;
    }

    /// Modifies the given [EntityRenderState] to make sure the [PlayerEntityRenderState#bodyYaw] and
    /// [PlayerEntityRenderState#relativeHeadYaw] are equal to 0. Otherwise, the head glitches and keeps rotating to the
    /// right for a tick before snapping back.
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
