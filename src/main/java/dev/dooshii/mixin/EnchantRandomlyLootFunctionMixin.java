package dev.dooshii.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.dooshii.enchantment.DisableMending;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Credit:
// https://github.com/MoriyaShiine/enchancement/blob/58b154a509a2589b143fabb7254e58908242b633/src/main/java/moriyashiine/enchancement/mixin/config/disabledisallowedenchantments/ingame/EnchantRandomlyLootFunctionMixin.java
@Mixin(EnchantRandomlyLootFunction.class)
public class EnchantRandomlyLootFunctionMixin {
    @ModifyExpressionValue(method = "process", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getRandomOrEmpty(Ljava/util/List;Lnet/minecraft/util/math/random/Random;)Ljava/util/Optional;"))
    private Optional<RegistryEntry<Enchantment>> disableMending(Optional<RegistryEntry<Enchantment>> original, ItemStack stack, @Local Random random) {
        if (original.isEmpty() || DisableMending.isMending(original.get())) {
            return Optional.ofNullable(DisableMending.getRandomEnchantment(stack, random));
        }
        return original;
    }
}