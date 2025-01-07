package dev.dooshii.event;

import dev.dooshii.enchantment.DisableMending;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;

// https://github.com/MoriyaShiine/enchancement/blob/main/src/main/java/moriyashiine/enchancement/common/event/CacheEnchantmentRegistryEvent.java
public class CacheEnchantmentRegistryEvent implements ServerLifecycleEvents.ServerStarted {
    @Override
    public void onServerStarted(MinecraftServer server) {
        DisableMending.ENCHANTMENTS.clear();
        DisableMending.ENCHANTMENTS.addAll(server.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).streamEntries().toList());
    }
}