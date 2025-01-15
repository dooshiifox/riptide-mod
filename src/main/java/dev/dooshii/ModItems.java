package dev.dooshii;

import dev.dooshii.item.LoggingItem;
import dev.dooshii.item.TrackingCompassItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Rarity;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModItems {
    public static final RegistryEntry<Potion> DAMP_POTION = Registry.registerReference(
            Registries.POTION,
            Riptide.id("damp"),
            new Potion(
                    "damp",
                    new StatusEffectInstance(
                            Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT),
                            // 3:30
                            4200,
                            0)));
    public static final RegistryEntry<Potion> LONG_DAMP_POTION = Registry.registerReference(
            Registries.POTION,
            Riptide.id("long_damp"),
            new Potion(
                    "long_damp",
                    new StatusEffectInstance(
                            Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT),
                            // 8:00
                            9600,
                            0)));

    public static final Item TRACKING_COMPASS = register("tracking_compass", TrackingCompassItem::new, new Item.Settings());
    public static final Item LOGGING = register("logging", LoggingItem::new, new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    public static final Item AUTUMN_LEAVES = register(ModBlocks.AUTUMN_LEAVES);
    public static final Item MAPLE_LEAVES = register(ModBlocks.MAPLE_LEAVES);
    public static final Item GOLDEN_LEAVES = register(ModBlocks.GOLDEN_LEAVES);

    public static void onInitialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.AWKWARD,
                    Items.ICE,
                    DAMP_POTION
            );
            builder.registerPotionRecipe(
                    DAMP_POTION,
                    Items.REDSTONE,
                    LONG_DAMP_POTION
            );
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> {
            itemGroup.addAfter(Blocks.FLOWERING_AZALEA_LEAVES, ModItems.AUTUMN_LEAVES, ModItems.MAPLE_LEAVES, ModItems.GOLDEN_LEAVES);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register((itemGroup) -> {
            itemGroup.add(ModItems.TRACKING_COMPASS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register((itemGroup) -> {
            itemGroup.add(ModItems.LOGGING);
        });
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Riptide.id(id));
    }

    private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
        return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
    }

    public static Item register(Block block) {
        return register(block, BlockItem::new);
    }

    public static Item register(Block block, Item.Settings settings) {
        return register(block, BlockItem::new, settings);
    }

    public static Item register(Block block, UnaryOperator<Item.Settings> settingsOperator) {
        return register(block, ((blockx, settings) -> new BlockItem(blockx, settingsOperator.apply(settings))));
    }

    public static Item register(Block block, Block... blocks) {
        Item item = register(block);

        for (Block block2 : blocks) {
            Item.BLOCK_ITEMS.put(block2, item);
        }

        return item;
    }

    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
        return register(block, factory, new Item.Settings());
    }

    public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
        return register(
                keyOf(block.getRegistryEntry().registryKey()), itemSettings -> factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey()
        );
    }

    public static Item register(String id, Function<Item.Settings, Item> factory) {
        return register(keyOf(id), factory, new Item.Settings());
    }

    public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(keyOf(id), factory, settings);
    }

    public static Item register(String id, Item.Settings settings) {
        return register(keyOf(id), Item::new, settings);
    }

    public static Item register(String id) {
        return register(keyOf(id), Item::new, new Item.Settings());
    }

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory) {
        return register(key, factory, new Item.Settings());
    }

    public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings.registryKey(key));
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }
}
