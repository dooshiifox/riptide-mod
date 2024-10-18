package dev.dooshii;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;

public class DampEffect extends StatusEffect {
    protected DampEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x4d8db7);
    }

    // Called every tick to check if the effect can be applied or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        // In our case, we just make it return true so that it applies the effect every tick
        return true;
    }

    public static boolean canUseRiptide(PlayerEntity player) {
        return player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(Riptide.DAMP_EFFECT)) || player.isTouchingWaterOrRain();
    }
}