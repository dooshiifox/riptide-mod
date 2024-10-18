package dev.dooshii;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Riptide implements ModInitializer {
	public static final String MOD_ID = "riptide";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final StatusEffect DAMP_EFFECT;

	static {
		DAMP_EFFECT = Registry.register(Registries.STATUS_EFFECT, Identifier.of(Riptide.MOD_ID, "damp"), new DampEffect());
	}

	@Override
	public void onInitialize() {
		ModItems.onInitialize();
	}
}