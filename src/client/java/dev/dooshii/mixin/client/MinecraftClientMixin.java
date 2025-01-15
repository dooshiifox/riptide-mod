package dev.dooshii.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.dooshii.Riptide;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Nullable
    public HitResult crosshairTarget;

    // Retrieves all the vehicles that the player is riding
    @Unique
    private static List<Entity> riptide$getAllVehicles(PlayerEntity player) {
        List<Entity> allVehicles = new ArrayList<>();
        Entity vehicleEntity = player.getVehicle();
        while (vehicleEntity != null) {
            vehicleEntity = vehicleEntity.getVehicle();
            allVehicles.add(vehicleEntity);
        }
        return allVehicles;
    }

    @ModifyExpressionValue(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/HitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;"))
    private HitResult.Type riptide$doAttack$skipCollisionlessBlocks(HitResult.Type original) {
        // No need to check if hitResult is null since the injected method already checks.
        if (crosshairTarget == null || player == null || crosshairTarget.getType() != HitResult.Type.BLOCK) {
            return original;
        }

        // The vec3 location of where player is looking.
        Vec3d hitResultLocation = crosshairTarget.getPos();
        // The pos of the block the player is looking at
        BlockPos blockPos = BlockPos.ofFloored(hitResultLocation);
        // is the collision shape empty? Aka can we walk through it?
        if (player.getWorld().getBlockState(blockPos).getCollisionShape(player.getWorld(), blockPos).isEmpty()) {
            // starting pos
            Vec3d start = player.getEyePos();
            // direction that player is facing
            Vec3d direction = player.getRotationVector(player.getPitch(), player.getYaw());
            // direction multiplied by entity interaction range which is then added to starting pos to get final entity pos
            Vec3d end = start.add(direction.multiply(player.getEntityInteractionRange()));

            EntityHitResult entityHitResult = ProjectileUtil.getEntityCollision(
                    player.getWorld(),
                    // we are the projectile lmao
                    player,
                    start,
                    end,
                    // bounding box
                    new Box(start, end),
                    EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR
                            .and(e -> e != null
                                    // can be hurt?
                                    && !e.isInvulnerable()
                                    // is alive and isn't like a boat
                                    && e instanceof LivingEntity
                                    // is not a vehicle that the player is in
                                    && !riptide$getAllVehicles(player).contains(e)
                            )
            );
            Riptide.LOGGER.info("Hit result: {}", entityHitResult);
            if (entityHitResult != null) {
                // replaces the current hit result with the entity hit result
                this.crosshairTarget = entityHitResult;
                // prevents block breaks
                MinecraftClient.getInstance().attackCooldown = 10;
                return entityHitResult.getType();
            }
        }

        return original;
    }

}