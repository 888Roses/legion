package net.collectively.geode.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class ModelProvider extends FabricModelProvider {
    protected BlockStateModelGenerator blockBuilder;
    protected ItemModelGenerator itemBuilder;

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NotNull BlockStateModelGenerator blockStateModelGenerator) {
        this.blockBuilder = blockStateModelGenerator;
        this.generateBlocks();
    }

    @Override
    public void generateItemModels(@NotNull ItemModelGenerator itemModelGenerator) {
        this.itemBuilder = itemModelGenerator;
        this.generateItems();
    }

    protected abstract void generateBlocks();

    protected abstract void generateItems();

    protected final Identifier uploadSingletonAndRetrieveIdentifier(Block block, TexturedModel.Factory modelFactory) {
        final var identifier = modelFactory.upload(block, this.blockBuilder.modelCollector);
        this.blockBuilder.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(identifier)));
        return identifier;
    }

    protected final Identifier uploadFactory(TexturedModel.Factory factory, Block block, String suffix) {
        return factory.upload(block, suffix, this.blockBuilder.modelCollector);
    }

    protected final void itemDefinition(Item item) {
        itemBuilder.register(item);
    }
}
