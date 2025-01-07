package dev.dooshii.enchantment;

import dev.dooshii.event.CacheEnchantmentRegistryEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DisableMending {
    public static final List<RegistryEntry.Reference<Enchantment>> ENCHANTMENTS = new ArrayList<>();
    public static ItemStack cachedApplyStack = null;

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(new CacheEnchantmentRegistryEvent());
    }

    public static boolean isMending(RegistryEntry<Enchantment> original) {
        return original.getKey().isEmpty() || original.getKey().get().getValue().equals(Identifier.ofVanilla("mending"));
    }

    public static String getTranslationKey(RegistryEntry<Enchantment> enchantment) {
        if (enchantment.value().description().getContent() instanceof TranslatableTextContent translatable) {
            return translatable.getKey();
        }
        return enchantment.value().description().getString();
    }

    public static List<RegistryEntry<Enchantment>> getAllowedEnchants(ItemStack stack) {
        List<RegistryEntry<Enchantment>> enchantments = new ArrayList<>();
        for (RegistryEntry<Enchantment> entry : ENCHANTMENTS) {
            if (!isMending(entry)) {
                if (stack.isOf(Items.BOOK) || stack.isOf(Items.ENCHANTED_BOOK) || stack.canBeEnchantedWith(entry, EnchantingContext.ACCEPTABLE)) {
                    enchantments.add(entry);
                }
            }
        }
        return enchantments;
    }

    @Nullable
    public static RegistryEntry<Enchantment> getReplacement(RegistryEntry<Enchantment> enchantment, ItemStack stack) {
        if (enchantment.getKey().isEmpty()) {
            return null;
        }
        List<RegistryEntry<Enchantment>> enchantments = getAllowedEnchants(stack);
        if (enchantments.isEmpty()) {
            return null;
        }
        int index = (enchantment.getKey().get().getValue().hashCode() + Registries.ITEM.getId(stack.getItem()).hashCode()) % enchantments.size();
        if (index < 0) {
            index += enchantments.size();
        }
        return enchantments.get(index);
    }

    public static RegistryEntry<Enchantment> getRandomEnchantment(ItemStack stack, Random random) {
        List<RegistryEntry<Enchantment>> enchantments = DisableMending.getAllowedEnchants(stack);
        if (!enchantments.isEmpty()) {
            return enchantments.get(random.nextInt(enchantments.size()));
        }
        return null;
    }
}
