package net.collectively.geode.datagen;

import net.collectively.geode.Geode;
import net.collectively.geode.mc.options.CustomOptionIndex;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * An advanced version of a {@link FabricLanguageProvider}, containing more presets and an easier workflow.
 */
public abstract class LanguageProvider extends FabricLanguageProvider {
    protected TranslationBuilder builder;

    public LanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.@NotNull WrapperLookup registryLookup, @NotNull TranslationBuilder translationBuilder) {
        this.builder = translationBuilder;

        this.builder.add("options.slider_value", "%s: %s");

        this.generate();
    }

    protected abstract void generate();

    protected final void block(Block block, String name) {
        this.builder.add(block, name);
    }

    protected final void item(Item item, String name) {
        this.builder.add(item, name);
    }

    protected final void sound(SoundEvent event, String name) {
        this.builder.add(event, name);
    }

    protected final void option(CustomOptionIndex.Option<?> option, String name) {
        this.builder.add("option." + Geode.HOOKED_MOD_ID + "." + option.id(), name);
    }
}
