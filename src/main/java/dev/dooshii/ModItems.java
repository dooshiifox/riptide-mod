package dev.dooshii;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final RegistryEntry<Potion> DAMP_POTION = Registry.registerReference(
            Registries.POTION,
            Identifier.of(Riptide.MOD_ID, "damp"),
            new Potion(
                    new StatusEffectInstance(
                            Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT),
                            // 3:30
                            4200,
                            0)));
    public static final RegistryEntry<Potion> LONG_DAMP_POTION = Registry.registerReference(
            Registries.POTION,
            Identifier.of(Riptide.MOD_ID, "long_damp"),
            new Potion(
                    "damp",
                    new StatusEffectInstance(
                            Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT),
                            // 8:00
                            9600,
                            0)));

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
    }
}
