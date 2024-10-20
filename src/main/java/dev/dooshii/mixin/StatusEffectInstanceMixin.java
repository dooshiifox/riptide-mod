package dev.dooshii.mixin;

import dev.dooshii.DampEffect;
import dev.dooshii.Riptide;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin {
    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;updateDuration()I", shift = At.Shift.AFTER))
    public void dampEffectUpdate(LivingEntity entity, Runnable overwriteCallback, CallbackInfoReturnable<Boolean> cir) {
        StatusEffectInstance thisObject = (StatusEffectInstance)(Object)this;
        if (!thisObject.getEffectType().matchesId(Identifier.of(Riptide.MOD_ID, "damp"))) {
            return;
        }

        DampEffect.Modifier modifier = DampEffect.getModifier(entity);

        if (modifier == DampEffect.Modifier.Nether) {
            // If in the nether, make the effect timer go twice as fast
            thisObject.duration = thisObject.mapDuration(duration -> duration - 1);
        } else if (modifier == DampEffect.Modifier.Cold) {
            // If in a [cold biome](https://minecraft.wiki/w/Biome#List_of_biome_climates),
            // make the entity start freezing to death
            entity.setInPowderSnow(true);
        } else if (modifier == DampEffect.Modifier.Water) {
            // Make the effect timer deplete at half speed when in water or rain
            thisObject.duration = thisObject.mapDuration(duration -> entity.age % 2 == 0 ? duration : duration + 1);
        }
    }
}
