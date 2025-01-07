package dev.dooshii.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.dooshii.enchantment.DisableMending;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

// https://github.com/MoriyaShiine/enchancement/blob/58b154a509a2589b143fabb7254e58908242b633/src/main/java/moriyashiine/enchancement/mixin/config/disabledisallowedenchantments/ingame/EnchantmentHelperMixin.java
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Redirect(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private static int increaseEnchantmentsReceived(Random random, int bound) {
        if (bound == 50) {
            return random.nextInt(20);
        }

        return random.nextInt(bound);
    }

    @Inject(method = "apply", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
    private static void disableDisallowedEnchantments(ItemStack stack, Consumer<ItemEnchantmentsComponent.Builder> applier, CallbackInfoReturnable<ItemEnchantmentsComponent> cir) {
        DisableMending.cachedApplyStack = stack;
    }
    @Inject(method = "apply", at = @At(value = "RETURN", ordinal = 1))
    private static void disableDisallowedEnchantmentsReturn(ItemStack stack, Consumer<ItemEnchantmentsComponent.Builder> applier, CallbackInfoReturnable<ItemEnchantmentsComponent> cir) {
        DisableMending.cachedApplyStack = null;
    }
    @ModifyVariable(method = "set", at = @At("HEAD"), argsOnly = true)
    private static ItemEnchantmentsComponent disableDisallowedEnchantments(ItemEnchantmentsComponent value, ItemStack stack) {
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
        value.getEnchantments().forEach(enchantment -> {
            int level = value.getLevel(enchantment);
            if (!DisableMending.isMending(enchantment)) {
                builder.add(enchantment, level);
            } else {
                @Nullable RegistryEntry<Enchantment> replacement = DisableMending.getReplacement(enchantment, stack);
                if (replacement != null) {
                    builder.add(replacement, Math.min(level, replacement.value().getMaxLevel()));
                }
            }
        });
        return builder.build();
    }
    @WrapOperation(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getPossibleEntries(ILnet/minecraft/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;"))
    private static List<EnchantmentLevelEntry> disableDisallowedEnchantments(int level, ItemStack stack, Stream<RegistryEntry<Enchantment>> possibleEnchantments, Operation<List<EnchantmentLevelEntry>> original, Random random) {
        List<EnchantmentLevelEntry> enchantments = original.call(level, stack, possibleEnchantments);
        for (int i = enchantments.size() - 1; i >= 0; i--) {
            RegistryEntry<Enchantment> enchantment = enchantments.get(i).enchantment;
            if (DisableMending.isMending(enchantment)) {
                @Nullable RegistryEntry<Enchantment> replacement = DisableMending.getReplacement(enchantment, stack);
                if (replacement == null) {
                    enchantments.remove(i);
                } else {
                    if (enchantments.stream().anyMatch(entry -> entry.enchantment.equals(replacement))) {
                        enchantments.remove(i);
                    } else {
                        enchantments.set(i, new EnchantmentLevelEntry(replacement, 1));
                    }
                }
            }
        }
        if (enchantments.isEmpty()) {
            @Nullable RegistryEntry<Enchantment> entry = DisableMending.getRandomEnchantment(stack, random);
            if (entry != null) {
                enchantments.add(new EnchantmentLevelEntry(entry, 1));
            }
        }
        return enchantments;
    }
}
