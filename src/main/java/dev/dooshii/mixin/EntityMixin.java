package dev.dooshii.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    public abstract World getWorld();

    @Inject(method = "playStepSound", at = @At("TAIL"))
    private void riptide$playLeafStepSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (this.riptide$isInLeaves(pos)) {
            BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
            this.playSound(soundGroup.getBreakSound(), soundGroup.getVolume() * 0.6F,
                    soundGroup.getPitch());
        }
    }

    @Unique
    public boolean riptide$isInLeaves(BlockPos blockPos) {
        Box contractedBoundingBox = this.getBoundingBox().contract(0.1F);
        return BlockPos.stream(contractedBoundingBox).anyMatch((pos) -> {
            BlockState blockState = this.getWorld().getBlockState(pos);
            return blockState.getBlock() instanceof LeavesBlock && pos.equals(blockPos);
        });
    }
}
