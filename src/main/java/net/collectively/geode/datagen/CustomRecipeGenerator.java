package net.collectively.geode.datagen;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;

public abstract class CustomRecipeGenerator extends RecipeGenerator {
    public CustomRecipeGenerator(
            RegistryWrapper.@NotNull WrapperLookup registries,
            @NotNull RecipeExporter exporter
    ) {
        super(registries, exporter);
    }
}
