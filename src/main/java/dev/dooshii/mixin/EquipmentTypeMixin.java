package dev.dooshii.mixin;

import net.minecraft.item.equipment.EquipmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EquipmentType.class)
public class EquipmentTypeMixin {
    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    public void getMaxDamage(int multiplier, CallbackInfoReturnable<Integer> cir) {
        // Leather
        if (multiplier == 5) cir.setReturnValue(80);
        // Chainmail / Iron
        else if (multiplier == 15) cir.setReturnValue(250);
        // Diamond
        else if (multiplier == 33) cir.setReturnValue(1250);
        // Gold
        else if (multiplier == 7) cir.setReturnValue(60);
        // Netherite
        else if (multiplier == 37) cir.setReturnValue(8000);
    }
}
