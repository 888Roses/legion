package net.collectively.geode.mc.index;

import net.collectively.geode.Geode;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public interface BlockEntityIndex {
    static <T extends BlockEntity> BlockEntityType<T> register(
            String identifier,
            FabricBlockEntityTypeBuilder.Factory<? extends @NotNull T> factory,
            Block... blocks
    ) {
        return register(Geode.id(identifier), factory, blocks);
    }

    static <T extends BlockEntity> BlockEntityType<T> register(
            Identifier identifier,
            FabricBlockEntityTypeBuilder.Factory<? extends @NotNull T> factory,
            Block... blocks
    ) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                identifier,
                FabricBlockEntityTypeBuilder.<T>create(factory, blocks).build()
        );
    }
}
