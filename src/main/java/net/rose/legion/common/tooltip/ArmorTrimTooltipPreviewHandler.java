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
import net.minecraft.util.Identifier;
import net.rose.legion.client.tooltip.ArmorTooltipComponent;
import net.rose.legion.common.Legion;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ArmorTrimTooltipPreviewHandler implements ArmorTooltipPreviewHandler {
    public static final int SWAP_SPEED = 40;

    @Override
    public boolean validate(ItemStack itemStack, EquipmentSlot slot) {
        return itemStack.getItem() instanceof SmithingTemplateItem;
    }

    @Override
    public @Nullable ArmorTooltipComponent.EntityInfo getEntityInfo(ArmorTooltipComponent component, EquipmentSlot slot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientWorld world = client.world;

        if (world == null) {
            return null;
        }

        ClientMannequinEntity livingEntity = new ClientMannequinEntity(world, client.getPlayerSkinCache());
        ItemStack itemStack = component.data().itemStack();
        if (itemStack.getItem() instanceof SmithingTemplateItem && !itemStack.isOf(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)) {


            Optional<RegistryEntryList.Named<Item>> headArmors = Registries.ITEM.getOptional(ItemTags.HEAD_ARMOR);
            if (headArmors.isPresent()) {
                RegistryEntryList.Named<Item> tagContent = headArmors.get();
                int index = (int) (world.getTime() / SWAP_SPEED % tagContent.size());
                RegistryEntry<Item> item = tagContent.get(index);
                ItemStack equipmentStack = new ItemStack(item);
                equipmentStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, itemStack, 0));
                livingEntity.equipStack(EquipmentSlot.HEAD, equipmentStack);
            }

            Optional<RegistryEntryList.Named<Item>> chestArmor = Registries.ITEM.getOptional(ItemTags.CHEST_ARMOR);
            if (chestArmor.isPresent()) {
                RegistryEntryList.Named<Item> tagContent = chestArmor.get();
                int index = (int) ((world.getTime() / SWAP_SPEED + 1) % tagContent.size());
                RegistryEntry<Item> item = tagContent.get(index);
                ItemStack equipmentStack = new ItemStack(item);
                equipmentStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, itemStack, 1));
                livingEntity.equipStack(EquipmentSlot.CHEST, equipmentStack);
            }

            Optional<RegistryEntryList.Named<Item>> legArmor = Registries.ITEM.getOptional(ItemTags.LEG_ARMOR);
            if (legArmor.isPresent()) {
                RegistryEntryList.Named<Item> tagContent = legArmor.get();
                int index = (int) ((world.getTime() / SWAP_SPEED + 2) % tagContent.size());
                RegistryEntry<Item> item = tagContent.get(index);
                ItemStack equipmentStack = new ItemStack(item);
                equipmentStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, itemStack, 2));
                livingEntity.equipStack(EquipmentSlot.LEGS, equipmentStack);
            }

            Optional<RegistryEntryList.Named<Item>> footArmor = Registries.ITEM.getOptional(ItemTags.FOOT_ARMOR);
            if (footArmor.isPresent()) {
                RegistryEntryList.Named<Item> tagContent = footArmor.get();
                int index = (int) ((world.getTime() / SWAP_SPEED + 3) % tagContent.size());
                RegistryEntry<Item> item = tagContent.get(index);
                ItemStack equipmentStack = new ItemStack(item);
                equipmentStack.set(DataComponentTypes.TRIM, getRandomMaterialTrim(world, itemStack, 3));
                livingEntity.equipStack(EquipmentSlot.FEET, equipmentStack);
            }
        }

        float scale = 32f;
        float verticalOffset = 0.8f;

        return new ArmorTooltipComponent.EntityInfo(livingEntity, scale, verticalOffset);
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

    @Override
    public Optional<Integer> getHeight(ArmorTooltipComponent component, EquipmentSlot slot) {
        return Optional.of(48);
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
