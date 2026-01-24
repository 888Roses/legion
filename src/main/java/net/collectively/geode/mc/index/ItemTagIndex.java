package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link TagKey<Item>}s.
 */
@SuppressWarnings("unused")
public interface ItemTagIndex {
    /**
     * Registers a new {@link TagKey<Item>} with the given {@link Identifier}.
     *
     * @param identifier The {@link Identifier} of that tag.
     * @return The created {@link TagKey<Item>}.
     */
    static TagKey<Item> register(Identifier identifier) {
        return TagKey.of(RegistryKeys.ITEM, identifier);
    }

    /**
     * Registers a new {@link TagKey<Item>} with the given {@link String} identifier.
     *
     * @param identifier The {@link String} identifier of that tag.
     * @return The created {@link TagKey<Item>}.
     * @apiNote Behind the scenes, converts the {@link String} identifier into a {@link Identifier} using
     * {@link Geode#id(String)} and calls {@link #register(Identifier)}.
     */
    static TagKey<Item> register(String identifier) {
        return register(Geode.id(identifier));
    }
}
