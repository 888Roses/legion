package net.collectively.geode.datagen;

import net.collectively.geode.Geode;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class SoundProvider extends FabricSoundsProvider {
    protected SoundExporter builder;

    public SoundProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Sound Definitions";
    }

    @Override
    protected final void configure(RegistryWrapper.@NotNull WrapperLookup registryLookup, @NotNull SoundExporter exporter) {
        this.builder = exporter;
        this.generate();
    }

    protected abstract void generate();

    protected final SoundBuilder create(SoundEvent event) {
        return new SoundBuilder(event, this);
    }

    public static class SoundBuilder {
        private final SoundEvent event;
        private final SoundProvider provider;

        private final List<SoundTypeBuilder.EntryBuilder> entries = new ArrayList<>();
        private String subtitle = "";
        private boolean replace = false;

        private SoundBuilder(SoundEvent event, SoundProvider provider) {
            this.event = event;
            this.provider = provider;
        }

        public SoundBuilder subtitle(String identifier) {
            return this.subtitle(Geode.id(identifier));
        }

        public SoundBuilder subtitle(Identifier identifier) {
            this.subtitle = Util.createTranslationKey("subtitles", identifier);
            return this;
        }

        public SoundBuilder subtitle() {
            return this.subtitle(this.event.id());
        }

        public SoundBuilder literalSubtitle(String soundName) {
            this.subtitle = soundName;
            return this;
        }

        public SoundBuilder sound(SoundEvent target) {
            this.entries.add(SoundTypeBuilder.EntryBuilder.ofEvent(target));
            return this;
        }

        public SoundBuilder sound(RegistryEntry<SoundEvent> target) {
            this.entries.add(SoundTypeBuilder.EntryBuilder.ofEvent(target));
            return this;
        }

        public SoundBuilder sound(Identifier target) {
            this.entries.add(SoundTypeBuilder.EntryBuilder.ofFile(target));
            return this;
        }

        public SoundBuilder sound(String target) {
            return this.sound(Geode.id(target));
        }

        public SoundBuilder replace(boolean replace) {
            this.replace = replace;
            return this;
        }

        public void build() {
            final var builder = SoundTypeBuilder.of();
            if (!this.subtitle.isEmpty()) builder.subtitle(this.subtitle);
            for (var i : this.entries) builder.sound(i);
            builder.replace(this.replace);
            this.provider.builder.add(event, builder);
        }
    }
}
