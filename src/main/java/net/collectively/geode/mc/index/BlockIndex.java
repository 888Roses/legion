package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link Block}s.
 */
@SuppressWarnings("unused")
public interface BlockIndex {
    /**
     * Registers a new {@link Block} using the given {@link Identifier}, {@link BlockFactory} and {@link AbstractBlock.Settings}.
     *
     * @param identifier The {@link Identifier} of the registered {@link Block}.
     * @param factory    {@link BlockFactory} responsible for creating the registered {@link Block} instance from the given {@link AbstractBlock.Settings}.
     * @param settings   {@link AbstractBlock.Settings} describing the behaviour of that {@link Block}.
     * @return The registered {@link Block}.
     */
    static Block register(Identifier identifier, BlockFactory factory, AbstractBlock.Settings settings) {
        final var blockKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);
        final var block = factory.bake(settings.registryKey(blockKey));
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    /**
     * Registers a new {@link Block} using the given {@link String} identifier, {@link BlockFactory} and
     * {@link AbstractBlock.Settings}.
     *
     * @param identifier The {@link String} identifier of the registered {@link Block}.
     * @param factory    {@link BlockFactory} responsible for creating the registered {@link Block} instance from the given {@link AbstractBlock.Settings}.
     * @param settings   {@link AbstractBlock.Settings} describing the behaviour of that {@link Block}.
     * @return The registered {@link Block}.
     * @apiNote Behind the scenes, turns the {@link String} identifier into an {@link Identifier} using
     * {@link Geode#id(String)} and calls {@link #register(Identifier, BlockFactory, AbstractBlock.Settings)}.
     */
    static Block register(String identifier, BlockFactory factory, AbstractBlock.Settings settings) {
        return register(Geode.id(identifier), factory, settings);
    }

    /**
     * Registers a new {@link Block} using the given {@link Identifier}, {@link BlockFactory} and {@link AbstractBlock.Settings}.
     * Also registers a {@link BlockItem} linked to that {@link Block}.
     *
     * @param identifier The {@link Identifier} of the registered {@link Block}.
     * @param factory    {@link BlockFactory} responsible for creating the registered {@link Block} instance from the given {@link AbstractBlock.Settings}.
     * @param settings   {@link AbstractBlock.Settings} describing the behaviour of that {@link Block}.
     * @return The registered {@link Block}.
     */
    static Block registerWithItem(Identifier identifier, BlockFactory factory, AbstractBlock.Settings settings) {
        var block = register(identifier, factory, settings);

        final var itemKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey, blockItem);

        return block;
    }

    static Block registerWithItem(Identifier identifier, BlockFactory factory, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        var block = register(identifier, factory, settings);

        final var itemKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
        BlockItem blockItem = new BlockItem(block, itemSettings.registryKey(itemKey).useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey, blockItem);

        return block;
    }


    static Block registerWithItem(String identifier, BlockFactory factory, AbstractBlock.Settings settings, Item.Settings itemSettings) {
        return registerWithItem(Geode.id(identifier), factory, settings, itemSettings);
    }

    /**
     * Registers a new {@link Block} using the given {@link String} identifier, {@link BlockFactory} and
     * {@link AbstractBlock.Settings}.
     * Also registers a {@link BlockItem} linked to that {@link Block}.
     *
     * @param identifier The {@link String} identifier of the registered {@link Block}.
     * @param factory    {@link BlockFactory} responsible for creating the registered {@link Block} instance from the given {@link AbstractBlock.Settings}.
     * @param settings   {@link AbstractBlock.Settings} describing the behaviour of that {@link Block}.
     * @return The registered {@link Block}.
     * @apiNote Behind the scenes, turns the {@link String} identifier into an {@link Identifier} using
     * {@link Geode#id(String)} and calls {@link #registerWithItem(Identifier, BlockFactory, AbstractBlock.Settings)}.
     */
    static Block registerWithItem(String identifier, BlockFactory factory, AbstractBlock.Settings settings) {
        return registerWithItem(Geode.id(identifier), factory, settings);
    }

    /**
     * Responsible for creating the registered {@link Block} instance from the given {@link AbstractBlock.Settings}.
     */
    @FunctionalInterface
    interface BlockFactory {
        /**
         * Creates a new {@link Block} instance from given {@link AbstractBlock.Settings} settings.
         * @param settings The {@link AbstractBlock.Settings} of the created {@link Block}.
         * @return The created instance.
         */
        Block bake(AbstractBlock.Settings settings);
    }
}
