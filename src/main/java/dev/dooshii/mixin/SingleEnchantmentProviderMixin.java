package dev.dooshii.mixin;

import dev.dooshii.enchantment.DisableMending;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.provider.SingleEnchantmentProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.random.Random;

@Mixin(SingleEnchantmentProvider.class)
public class SingleEnchantmentProviderMixin {
    @Inject(method = "provideEnchantments", at = @At("HEAD"))
    private void disableDisallowedEnchantments(ItemStack stack, ItemEnchantmentsComponent.Builder componentBuilder, Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        DisableMending.cachedApplyStack = stack;
    }

    @Inject(method = "provideEnchantments", at = @At("HEAD"))
    private void disableDisallowedEnchantmentsTail(ItemStack stack, ItemEnchantmentsComponent.Builder componentBuilder, Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        DisableMending.cachedApplyStack = null;
    }
}