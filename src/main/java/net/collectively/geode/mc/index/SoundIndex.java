package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

/**
 * Utility class to easily register {@link SoundEvent}s.
 */
@SuppressWarnings("unused")
public interface SoundIndex {
    /**
     * Registers a new {@link SoundEvent} with the given {@link Identifier}.
     *
     * @param identifier The {@link Identifier} of the registered {@link SoundEvent}.
     * @return The registered {@link SoundEvent}.
     */
    static SoundEvent register(Identifier identifier) {
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    /**
     * Registers a new {@link SoundEvent} with the given {@link String} identifier.
     *
     * @param identifier The {@link String} identifier of the registered {@link SoundEvent}.
     * @return The registered {@link SoundEvent}.
     * @apiNote Behind the scenes, converts the given {@link String} identifier into an {@link Identifier} by using
     * {@link Geode#id(String)} and calls {@link #register(Identifier)}.
     */
    static SoundEvent register(String identifier) {
        return register(Geode.id(identifier));
    }
}