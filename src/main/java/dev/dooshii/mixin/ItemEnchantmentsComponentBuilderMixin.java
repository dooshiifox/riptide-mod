package dev.dooshii.mixin;

import dev.dooshii.Riptide;
import dev.dooshii.enchantment.DisableMending;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/MoriyaShiine/enchancement/blob/58b154a509a2589b143fabb7254e58908242b633/src/main/java/moriyashiine/enchancement/mixin/config/disabledisallowedenchantments/ingame/ItemEnchantmentsComponentBuilderMixin.java
@Mixin(ItemEnchantmentsComponent.Builder.class)
public abstract class ItemEnchantmentsComponentBuilderMixin {
    @Shadow
    public abstract void set(RegistryEntry<Enchantment> enchantment, int level);

    @Shadow
    public abstract void add(RegistryEntry<Enchantment> enchantment, int level);

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private void disableDisallowedEnchantmentsSet(RegistryEntry<Enchantment> enchantment, int level, CallbackInfo ci) {
        if (enchantment == null) {
            Riptide.LOGGER.warn("Attempted to set a null enchantment");
            ci.cancel();
        } else if (DisableMending.isMending(enchantment)) {
            if (DisableMending.cachedApplyStack != null) {
                @Nullable RegistryEntry<Enchantment> replacement = DisableMending.getReplacement(enchantment, DisableMending.cachedApplyStack);
                if (replacement != null) {
                    set(replacement, Math.min(level, replacement.value().getMaxLevel()));
                    ci.cancel();
                    return;
                }
            }
            Riptide.LOGGER.warn("Attempted to set a disabled enchantment {}", DisableMending.getTranslationKey(enchantment));
            ci.cancel();
        }
    }

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void disableDisallowedEnchantmentsAdd(RegistryEntry<Enchantment> enchantment, int level, CallbackInfo ci) {
        if (enchantment == null) {
            Riptide.LOGGER.warn("Attempted to add a null enchantment");
            ci.cancel();
        } else if (DisableMending.isMending(enchantment)) {
            if (DisableMending.cachedApplyStack != null) {
                @Nullable RegistryEntry<Enchantment> replacement = DisableMending.getReplacement(enchantment, DisableMending.cachedApplyStack);
                if (replacement != null) {
                    add(replacement, Math.min(level, replacement.value().getMaxLevel()));
                    ci.cancel();
                    return;
                }
            }
            Riptide.LOGGER.warn("Attempted to add a disabled enchantment {}", DisableMending.getTranslationKey(enchantment));
            ci.cancel();
        }
    }
}
