package dev.dooshii;

import dev.dooshii.enchantment.DisableMending;
import dev.dooshii.enchantment.ExtraEnchantmentEffects;
import dev.dooshii.entity.effect.DampStatusEffect;
import dev.dooshii.event.CacheEnchantmentRegistryEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Riptide implements ModInitializer {
	public static final String MOD_ID = "riptide";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final StatusEffect DAMP_EFFECT;

	static {
		DAMP_EFFECT = Registry.register(Registries.STATUS_EFFECT, Riptide.id("damp"), new DampStatusEffect());
	}

	@Override
	public void onInitialize() {
		ModItems.onInitialize();
		ModComponents.initialize();

		// lazy initialisation of static vars breaks registering this enchantment effect,
		// so assign it to a junk variable.
		var _midairJump = ExtraEnchantmentEffects.MIDAIR_JUMP;

		DisableMending.init();
	}

	public static Identifier id(String id) {
		return Identifier.of(Riptide.MOD_ID, id);
	}
}