package dev.dooshii.mixin;

import dev.dooshii.entity.effect.DampStatusEffect;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin {
	// Controls the 'about to throw' animation
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (EnchantmentHelper.getTridentSpinAttackStrength(itemStack, user) > 0.0F && DampStatusEffect.canUseRiptide(user)) {
			user.setCurrentHand(hand);
			cir.setReturnValue(TypedActionResult.consume(itemStack));
		}
	}

	// Whether the player can use the riptide enchantment when they release the `use` button.
	@Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
	public boolean canUse(PlayerEntity player) {
		return DampStatusEffect.canUseRiptide(player);
	}
}