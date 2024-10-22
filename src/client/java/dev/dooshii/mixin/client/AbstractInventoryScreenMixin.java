package dev.dooshii.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.dooshii.entity.effect.DampStatusEffect;
import dev.dooshii.Riptide;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin {
	@Inject(
			method = "drawStatusEffectDescriptions",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I", shift = At.Shift.AFTER),
			slice = @Slice(
					from = @At(
							value = "INVOKE",
							target = "Lnet/minecraft/entity/effect/StatusEffectUtil;getDurationText(Lnet/minecraft/entity/effect/StatusEffectInstance;FF)Lnet/minecraft/text/Text;"
					)
			)
	)
	private void drawDampEffectTimer(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, CallbackInfo ci, @Local(ordinal = 2) int i, @Local StatusEffectInstance statusEffectInstance, @Local(ordinal = 1) Text durationText) {
		if (!statusEffectInstance.getEffectType().matchesId(Identifier.of(Riptide.MOD_ID, "damp"))) {
			return;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		if (player == null) {
			return;
		}

		int color = DampStatusEffect.getModifier(player).getTextColor();
		context.drawTextWithShadow(client.textRenderer, durationText, x + 10 + 18, i + 6 + 10, color);
	}
}