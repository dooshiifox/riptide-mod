package dev.dooshii.entity.effect;

import dev.dooshii.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class DampStatusEffect extends StatusEffect {
    public DampStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x4d8db7);
    }

    public static boolean canUseRiptide(PlayerEntity player) {
        return player.hasStatusEffect(Registries.STATUS_EFFECT.getEntry(ModEffects.DAMP_EFFECT)) || player.isTouchingWaterOrRain();
    }

    public static Modifier getModifier(LivingEntity entity) {
        BlockPos pos = entity.getBlockPos();
        World world = entity.getWorld();
        RegistryEntry<Biome> biome = world.getBiome(pos);

        if (biome.isIn(BiomeTags.IS_NETHER)) {
            return Modifier.Nether;
        } else if (biome.value().isCold(pos, world.getSeaLevel())) {
            return Modifier.Cold;
        } else if (entity.isTouchingWaterOrRain()) {
            return Modifier.Water;
        }

        return Modifier.Neutral;
    }

    public enum Modifier {
        Nether,
        Neutral,
        Cold,
        Water;

        public int getTextColor() {
            if (this == Nether) {
                return 0xc15565;
            } else if (this == Cold) {
                return 0x619ea0;
            } else if (this == Water) {
                return 0x5c6fb2;
            }

            return 0x7F7F7F;
        }
    }
}