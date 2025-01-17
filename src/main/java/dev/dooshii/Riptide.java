package dev.dooshii;

import dev.dooshii.enchantment.DisableMending;
import dev.dooshii.enchantment.ExtraEntityAttributes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Riptide implements ModInitializer {
    public static final String MOD_ID = "riptide";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

//    public static final OwoNetChannel NET_CHANNEL = OwoNetChannel.create(Riptide.id("main")).addEndecs(NetworkEndec::registerEndecs);

    @Override
    public void onInitialize() {
        ModBlocks.onInitialize();
        ModItems.onInitialize();
        ModEffects.onInitialize();
        ModTags.onInitialize();
        ModComponents.onInitialize();

        ExtraEntityAttributes.onInitialize();

        DisableMending.onInitialize();
    }

    public static Identifier id(String id) {
        return Identifier.of(Riptide.MOD_ID, id);
    }
}