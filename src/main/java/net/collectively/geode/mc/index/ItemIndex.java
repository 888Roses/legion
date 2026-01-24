package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link Item}s.
 */
@SuppressWarnings("unused")
public interface ItemIndex {
    /**
     * Registers a new {@link Item} using the given {@link Identifier} and {@link ItemFactory}.
     *
     * @param identifier The {@link Identifier} of the {@link Item} and how it is registered in-game.
     * @param factory    Responsible for actually creating the registered {@link Item} using the given {@link Item.Settings}.
     * @param settings   Describes the basic behaviour of the {@link Item}.
     * @return The registered {@link Item}.
     */
    static Item register(Identifier identifier, ItemFactory factory, Item.Settings settings) {
        final var key = RegistryKey.of(RegistryKeys.ITEM, identifier);
        settings = settings.registryKey(key);
        return Registry.register(Registries.ITEM, key, factory.bake(settings));
    }

    /**
     * Registers a new {@link Item} using the given {@link Identifier} and {@link ItemFactory}.
     *
     * @param identifier The {@link String} identifier of the {@link Item} and how it is registered in-game.
     * @param factory    Responsible for actually creating the registered {@link Item} using the given {@link Item.Settings}.
     * @param settings   Describes the basic behaviour of the {@link Item}.
     * @return The registered {@link Item}.
     * @apiNote Behind the scenes, converts the given {@link String} identifier into an {@link Identifier} by using
     * {@link Geode#id(String)} and calls {@link #register(Identifier, ItemFactory, Item.Settings)}.
     */
    static Item register(String identifier, ItemFactory factory, Item.Settings settings) {
        return register(Geode.id(identifier), factory, settings);
    }

    /**
     * Functional interface responsible for creating an {@link Item} using its {@link Item.Settings} so it can be registered.
     */
    @FunctionalInterface
    interface ItemFactory {
        /**
         * Creates an {@link Item} from its {@link Item.Settings}.
         *
         * @param settings The {@link Item.Settings} of the {@link Item} to create.
         * @return The created {@link Item}, ready to be registered.
         */
        Item bake(Item.Settings settings);
    }
}
