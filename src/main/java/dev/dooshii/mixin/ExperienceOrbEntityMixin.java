package dev.dooshii.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @Inject(method = "repairPlayerGears", at = @At("HEAD"), cancellable = true)
    private void repairPlayerGears(ServerPlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.repairPlayerGears(player, amount));
    }

    @Unique
    private int repairPlayerGears(ServerPlayerEntity player, int amount) {
        Optional<ItemStack> optional = chooseRepairableEquipment(player);
        if (optional.isPresent()) {
            ItemStack itemStack = optional.get();
            int j = Math.min(2 * amount, itemStack.getDamage());
            itemStack.setDamage(itemStack.getDamage() - j);
            if (j > 0) {
                int k = amount - j * amount / (2 * amount);
                if (k > 0) {
                    return this.repairPlayerGears(player, k);
                }
            }

            return 0;
        } else {
            return amount;
        }
    }

    @Unique
    private static Optional<ItemStack> chooseRepairableEquipment(LivingEntity entity) {
        List<ItemStack> list = new ArrayList<>();

        for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
            ItemStack itemStack = entity.getEquippedStack(equipmentSlot);
            if (itemStack.isDamaged() && itemStack.getItem() == Items.ELYTRA) {
                list.add(itemStack);
            }
        }

        return Util.getRandomOrEmpty(list, entity.getRandom());
    }
}
