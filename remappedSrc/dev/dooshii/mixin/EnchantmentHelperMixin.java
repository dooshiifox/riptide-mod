package dev.dooshii.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Redirect(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private static int increaseEnchantmentsReceived(Random random, int bound) {
        if (bound == 50) {
            return random.nextInt(20);
        }

        return random.nextInt(bound);
    }
}
