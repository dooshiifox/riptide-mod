package dev.dooshii.mixin;

import dev.dooshii.ModTags;
import dev.dooshii.Riptide;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block implements Waterloggable {
    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, new Vec3d(1.3f, 1.7f, 1.3f));

        if (world.isClient) {
            Random random = world.getRandom();
            boolean positionChanged = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
            if (positionChanged && random.nextInt(5) == 1) {
                int multiplier = (int) Math.sqrt(4 * entity.getVelocity().length()) + 1;
                BlockSoundGroup soundGroup = BlockSoundGroup.AZALEA_LEAVES;
                world.playSoundAtBlockCenter(pos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, soundGroup.getVolume() * 0.2F,
                        soundGroup.getPitch(), false);

                // spawn fancy particle
                int particleCount = multiplier * 2;
                BlockState blockState = world.getBlockState(pos);
                for (int i = 0; i < particleCount; ++i) {
                    double velocityX = world.random.nextGaussian() * 0.15f;
                    double velocityY = world.random.nextGaussian() * 0.15f;
                    double velocityZ = world.random.nextGaussian() * 0.15f;

                    world.addParticle(
                            new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState),
                            false,
                            false,
                            entity.getX(),
                            entity.getY(),
                            entity.getZ(),
                            velocityX,
                            velocityY,
                            velocityZ
                    );
                }
            }
        }
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9F, 1.0);
                }


                if (entity instanceof FallingBlockEntity || canWalkOnLeaves(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false)) {
                    Riptide.LOGGER.info("Walking on top!");
                    return VoxelShapes.fullCube();
                }
            }
        }

        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Unique
    private static boolean canWalkOnLeaves(Entity entity) {
        return entity.getType().isIn(ModTags.LEAVES_WALKABLE) || entity.isSneaking();
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }
}
