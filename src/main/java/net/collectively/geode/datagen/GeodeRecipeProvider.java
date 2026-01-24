package net.collectively.geode.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class GeodeRecipeProvider extends FabricRecipeProvider {
    public GeodeRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Recipe Definitions";
    }

    protected abstract CustomRecipeGenerator create(
            RegistryWrapper.@NotNull WrapperLookup registries,
            @NotNull RecipeExporter exporter
    );

    @Override
    protected final @NotNull RecipeGenerator getRecipeGenerator(
            RegistryWrapper.@NotNull WrapperLookup registries,
            @NotNull RecipeExporter exporter
    ) {
        return create(registries, exporter);
    }
}