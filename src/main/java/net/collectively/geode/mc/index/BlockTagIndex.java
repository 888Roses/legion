package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link TagKey<Block>}s.
 */
@SuppressWarnings("unused")
public interface BlockTagIndex {
    /**
     * Registers a new {@link TagKey<Block>} with the given {@link Identifier}.
     *
     * @param identifier The {@link Identifier} of that tag.
     * @return The created {@link TagKey<Block>}.
     */
    static TagKey<Block> register(Identifier identifier) {
        return TagKey.of(RegistryKeys.BLOCK, identifier);
    }

    /**
     * Registers a new {@link TagKey<Block>} with the given {@link String} identifier.
     *
     * @param identifier The {@link String} identifier of that tag.
     * @return The created {@link TagKey<Block>}.
     * @apiNote Behind the scenes, converts the {@link String} identifier into a {@link Identifier} using
     * {@link Geode#id(String)} and calls {@link #register(Identifier)}.
     */
    static TagKey<Block> register(String identifier) {
        return register(Geode.id(identifier));
    }
}
