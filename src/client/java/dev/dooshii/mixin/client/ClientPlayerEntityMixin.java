package dev.dooshii.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.authlib.GameProfile;
import dev.dooshii.enchantment.ExtraEntityAttributes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Unique
    private int timesJumped = 0;
    @Unique
    private int timesBoosted = 0;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Definition(id = "allowFlying", field = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z")
    @Expression("?.allowFlying")
    @Inject(method = "tickMovement", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    // I want to use ordinal instead of index, but it doesn't work beyond 3?
    private void allowMidAirJump(CallbackInfo ci, @Local(index = 9) LocalBooleanRef jumpActionConsumed, @Local(ordinal = 0) boolean jumpingLastFrame) {
        ClientPlayerEntity thisObject = (ClientPlayerEntity) (Object) this;

        if (thisObject.isOnGround()) {
            this.timesJumped = 0;
            this.timesBoosted = 0;
        } else {
            this.timesJumped = Math.max(this.timesJumped, 1);
            if (thisObject.input.playerInput.jump() &&
                    !jumpActionConsumed.get() &&
                    !jumpingLastFrame &&
                    !thisObject.isClimbing() &&
                    !thisObject.isGliding() &&
                    !thisObject.isTouchingWater() &&
                    this.timesJumped < thisObject.getAttributeValue(ExtraEntityAttributes.MIDAIR_JUMP) + 1
            ) {
                jumpActionConsumed.set(true);
                this.timesJumped++;
                thisObject.jump();
                // Allow the player to fly within 7 ticks
                thisObject.abilityResyncCountdown = 7;
            }
        }

        if (thisObject.isGliding() &&
                thisObject.input.playerInput.jump() &&
                !jumpActionConsumed.get() &&
                !jumpingLastFrame &&
                !thisObject.isClimbing() &&
                !thisObject.isTouchingWater() &&
                !thisObject.isOnGround() &&
                this.timesBoosted < thisObject.getAttributeValue(ExtraEntityAttributes.ELYTRA_BOOST)
        ) {
            jumpActionConsumed.set(true);
            this.timesBoosted++;
            Vec3d rotation = thisObject.getRotationVector();
            double d = thisObject.getAttributeValue(ExtraEntityAttributes.ELYTRA_BOOST_STRENGTH);
            double e = 0.1;
            Vec3d velocity = thisObject.getVelocity();
            thisObject.setVelocity(
                    velocity.add(
                            rotation.x * e + (rotation.x * d - velocity.x) * 0.5,
                            rotation.y * e + (rotation.y * d - velocity.y) * 0.5,
                            rotation.z * e + (rotation.z * d - velocity.z) * 0.5
                    )
            );
        }
    }

    @Definition(id = "allowFlying", field = "Lnet/minecraft/entity/player/PlayerAbilities;allowFlying:Z")
    @Expression("?.allowFlying")
    @ModifyExpressionValue(method = "tickMovement", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean dontFlyIfMidAirJumping(boolean original, @Local(index = 9) boolean jumpActionConsumed) {
        return original && !jumpActionConsumed;
    }
}
