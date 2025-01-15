package dev.dooshii;

import dev.dooshii.entity.effect.DampStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {
    public static final StatusEffect DAMP_EFFECT = Registry.register(Registries.STATUS_EFFECT, Riptide.id("damp"), new DampStatusEffect());

    public static void onInitialize() {
    }
}
