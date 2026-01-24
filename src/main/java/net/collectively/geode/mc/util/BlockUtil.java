package net.collectively.geode.mc.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public interface BlockUtil {
    static void modifyBlockState(World world, BlockState blockState, BlockPos blockPos, Function<BlockState, BlockState> factory) {
        final var newBlockState = factory.apply(blockState);
        world.setBlockState(blockPos, newBlockState, Block.NOTIFY_ALL);
        world.onBlockStateChanged(blockPos, blockState, newBlockState);
    }
}
