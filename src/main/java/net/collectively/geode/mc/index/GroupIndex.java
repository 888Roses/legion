package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link ItemGroup}s.
 */
@SuppressWarnings("unused")
public interface GroupIndex {
    /**
     * Registers a new {@link ItemGroup} in the form of a {@link Group} using the given {@link Identifier} and the
     * {@link GroupFactory} factory.
     *
     * @param identifier The {@link Identifier} of that group.
     * @param factory    Describes how the group looks and behaves. <b>Warning:</b> Do NOT use
     *                   {@link ItemGroup.Builder#entries(ItemGroup.EntryCollector)} as it may break the system. Please
     *                   use the {@link ItemGroupEvents#modifyEntriesEvent(RegistryKey)} event instead.
     * @return A new {@link Group} containing the {@link Group#registryKey()} of the registered {@link ItemGroup} as
     * well as the registered {@link ItemGroup} itself.
     */
    static Group register(Identifier identifier, GroupFactory factory) {
        final var registryKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), identifier);
        final var group = factory.modify(FabricItemGroup.builder()).build();
        Registry.register(Registries.ITEM_GROUP, registryKey, group);
        return new Group(registryKey, group);
    }

    /**
     * Registers a new {@link ItemGroup} in the form of a {@link Group} using the given {@link String} identifier and
     * the {@link GroupFactory} factory.
     *
     * @param identifier The {@link String} identifier of that group.
     * @param factory    Describes how the group looks and behaves. <b>Warning:</b> Do NOT use
     *                   {@link ItemGroup.Builder#entries(ItemGroup.EntryCollector)} as it may break the system. Please
     *                   use the {@link ItemGroupEvents#modifyEntriesEvent(RegistryKey)} event instead.
     * @return A new {@link Group} containing the {@link Group#registryKey()} of the registered {@link ItemGroup} as
     * well as the registered {@link ItemGroup} itself.
     * @apiNote Behind the scenes, creates a {@link Identifier} out of the given {@link String} identifier using
     * {@link Geode#id(String)} and calls {@link #register(Identifier, GroupFactory)}.
     */
    static Group register(String identifier, GroupFactory factory) {
        return register(Geode.id(identifier), factory);
    }

    /**
     * Is responsible for applying modification on a given {@link ItemGroup.Builder} before returning it, <b>without
     * calling</b> {@link ItemGroup.Builder#build()}.
     */
    @FunctionalInterface
    interface GroupFactory {
        /**
         * Applies modification on the given {@link ItemGroup.Builder} and returns it afterwise <b>without calling </b>
         * {@link ItemGroup.Builder#build()}.
         *
         * @param builder The {@link ItemGroup.Builder} to make modifications on.
         * @return The {@link ItemGroup.Builder} once modified.
         */
        ItemGroup.Builder modify(ItemGroup.Builder builder);
    }

    /**
     * A registered {@link ItemGroup} paired with its {@link RegistryKey<ItemGroup>}.
     * @param registryKey The key used to register this {@link ItemGroup}.
     * @param group The registered group.
     */
    record Group(RegistryKey<ItemGroup> registryKey, ItemGroup group) {
    }
}
