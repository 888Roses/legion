package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link EntityType}s.
 */
@SuppressWarnings("unused")
public interface EntityTypeIndex {
    /**
     * Registers a new {@link EntityType<T>} with the given {@link Identifier} by following the {@link EntityType.Builder}.
     *
     * @param identifier The {@link Identifier} of that {@link EntityType<T>}.
     * @param builder    Describes how the registered entity behaves.
     * @param <T>        The type of {@link Entity} to register.
     * @return The registered {@link EntityType<T>}.
     */
    static <T extends Entity> EntityType<T> register(Identifier identifier, EntityType.Builder<T> builder) {
        final var registryKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier);
        return Registry.register(Registries.ENTITY_TYPE, registryKey, builder.build(registryKey));
    }

    /**
     * Registers a new {@link EntityType<T>} with the given {@link String} identifier by following the {@link EntityType.Builder}.
     *
     * @param identifier The {@link String} identifier of that {@link EntityType<T>}.
     * @param builder    Describes how the registered entity behaves.
     * @param <T>        The type of {@link Entity} to register.
     * @return The registered {@link EntityType<T>}.
     * @apiNote Behind the scenes, turns the {@link String} identifier into an {@link Identifier} using
     * {@link Geode#id(String)} and calls {@link #register(Identifier, EntityType.Builder)}.
     */
    static <T extends Entity> EntityType<T> register(String identifier, EntityType.Builder<T> builder) {
        return register(Geode.id(identifier), builder);
    }
}