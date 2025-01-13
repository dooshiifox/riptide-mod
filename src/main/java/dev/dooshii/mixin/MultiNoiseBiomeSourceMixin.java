package dev.dooshii.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(net.minecraft.world.biome.source.MultiNoiseBiomeSource.class)
public class MultiNoiseBiomeSourceMixin {
    @Inject(method = "addDebugInfo", at = @At("HEAD"), cancellable = true)
    public void addDebugInfo(List<String> info, BlockPos pos, MultiNoiseUtil.MultiNoiseSampler noiseSampler, CallbackInfo ci) {
        int x = BiomeCoords.fromBlock(pos.getX());
        int y = BiomeCoords.fromBlock(pos.getY());
        int z = BiomeCoords.fromBlock(pos.getZ());
        MultiNoiseUtil.NoiseValuePoint noiseValuePoint = noiseSampler.sample(x, y, z);

        info.add("Biome builder: " +
                MultiNoiseBiomeSourceMixin.getTemperatureDescription(noiseValuePoint) +
                ", " +
                MultiNoiseBiomeSourceMixin.getHumidityDescription(noiseValuePoint) +
                ", " +
                MultiNoiseBiomeSourceMixin.getContinentDescription(noiseValuePoint) +
                ", " +
                MultiNoiseBiomeSourceMixin.getErosionDescription(noiseValuePoint) +
                ", " +
                MultiNoiseBiomeSourceMixin.getRidgesAndWeirdnessDescription(noiseValuePoint));
        ci.cancel();
    }

    @Unique
    private static String getTemperatureDescription(MultiNoiseUtil.NoiseValuePoint noiseValuePoint) {
        float t = MultiNoiseUtil.toFloat(noiseValuePoint.temperatureNoise());
        if (t < -0.45) {
            return "Icy";
        } else if (t < -0.15) {
            return "Cold";
        } else if (t < 0.2) {
            return "Mild";
        } else if (t < 0.55) {
            return "Warm";
        } else {
            return "Hot";
        }
    }

    @Unique
    private static String getHumidityDescription(MultiNoiseUtil.NoiseValuePoint noiseValuePoint) {
        float h = MultiNoiseUtil.toFloat(noiseValuePoint.humidityNoise());
        if (h < -0.35) {
            return "Arid";
        } else if (h < -0.1) {
            return "Dry";
        } else if (h < 0.1) {
            return "Neutral";
        } else if (h < 0.3) {
            return "Wet";
        } else {
            return "Humid";
        }
    }

    @Unique
    private static String getContinentDescription(MultiNoiseUtil.NoiseValuePoint noiseValuePoint) {
        float c = MultiNoiseUtil.toFloat(noiseValuePoint.continentalnessNoise());
        if (c < -1.05) {
            return "Island";
        } else if (c < -0.455) {
            return "Deep Ocean";
        } else if (c < -0.19) {
            return "Ocean";
        } else if (c < -0.11) {
            return "Coastal";
        } else if (c < 0.03) {
            return "Near Inland";
        } else if (c < 0.3) {
            return "Mid Inland";
        } else {
            return "Far Inland";
        }
    }

    @Unique
    private static String getErosionDescription(MultiNoiseUtil.NoiseValuePoint noiseValuePoint) {
        float e = MultiNoiseUtil.toFloat(noiseValuePoint.erosionNoise());
        if (e < -0.78) {
            return "Extreme Mountains";
        } else if (e < -0.375) {
            return "Mountains";
        } else if (e < -0.2225) {
            return "Hills";
        } else if (e < 0.05) {
            return "Slopes";
        } else if (e < 0.45) {
            return "Flat";
        } else if (e < 0.55) {
            return "Very Flat";
        } else {
            return "Extremely Flat";
        }
    }

    @Unique
    private static String getRidgesAndWeirdnessDescription(MultiNoiseUtil.NoiseValuePoint noiseValuePoint) {
        float w = MultiNoiseUtil.toFloat(noiseValuePoint.weirdnessNoise());
        double r = Math.abs(w);
        StringBuilder str = new StringBuilder();
        if (r < 0.05) {
            str.append("Valley");
        } else if (r < 0.266) {
            str.append("Low");
        } else if (r < 0.4) {
            str.append("Mid (Desc)");
        } else if (r < 0.566) {
            str.append("High (Desc)");
        } else if (r < 0.766) {
            str.append("Peak");
        } else if (r < 0.933) {
            str.append("High (Asc)");
        } else {
            str.append("Mid (Asc)");
        }

        if (w < 0) {
            str.append(" (Normal)");
        } else {
            str.append(" (Variant)");
        }

        return str.toString();
    }
}
