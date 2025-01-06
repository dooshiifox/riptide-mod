package dev.dooshii;

import dev.dooshii.item.TrackingCompassItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Block;
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
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModItems {
    public static final RegistryEntry<Potion> DAMP_POTION = Registry.registerReference(
            Registries.POTION,
            Riptide.id("damp"),
            new Potion(
                    new StatusEffectInstance(
                            Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT),
                            // 3:30
                            4200,
                            0)));
    public static final RegistryEntry<Potion> LONG_DAMP_POTION = Registry.registerReference(
            Registries.POTION,
            Riptide.id("long_damp"),
            new Potion(
                    "damp",
                    new StatusEffectInstance(
                            Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT),
                            // 8:00
                            9600,
                            0)));

    public static final Item TRACKING_COMPASS = register("tracking_compass", new TrackingCompassItem(new Item.Settings()));

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

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
                .register((itemGroup) -> itemGroup.add(ModItems.TRACKING_COMPASS));
    }

    public static Item register(Block block) {
        return register(new BlockItem(block, new Item.Settings()));
    }

    public static Item register(Block block, UnaryOperator<Item.Settings> settingsOperator) {
        return register(new BlockItem(block, settingsOperator.apply(new Item.Settings())));
    }

    public static Item register(Block block, Block... blocks) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());

        for (Block block2 : blocks) {
            Item.BLOCK_ITEMS.put(block2, blockItem);
        }

        return register(blockItem);
    }

    public static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    public static Item register(Block block, Item item) {
        return register(Registries.BLOCK.getId(block), item);
    }

    public static Item register(String id, Item item) {
        return register(Riptide.id(id), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }
}
