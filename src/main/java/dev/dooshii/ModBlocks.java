package dev.dooshii;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public class ModBlocks {
    public static final Block AUTUMN_LEAVES = register("autumn_leaves", LeavesBlock::new, Blocks.createLeavesSettings(BlockSoundGroup.GRASS));
    public static final Block MAPLE_LEAVES = register("maple_leaves", LeavesBlock::new, Blocks.createLeavesSettings(BlockSoundGroup.GRASS));
    public static final Block GOLDEN_LEAVES = register("golden_leaves", LeavesBlock::new, Blocks.createLeavesSettings(BlockSoundGroup.GRASS));

    public static void onInitialize() {
    }

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Riptide.id(id));
    }

    private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        return Blocks.register(keyOf(id), factory, settings);
    }
}
