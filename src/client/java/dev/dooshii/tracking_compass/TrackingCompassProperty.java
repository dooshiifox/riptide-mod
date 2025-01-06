package dev.dooshii.tracking_compass;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TrackingCompassProperty implements NumericProperty {
    public static final MapCodec<TrackingCompassProperty> CODEC = TrackingCompassState.CODEC.xmap(
            TrackingCompassProperty::new,
            property -> property.state);
    private final TrackingCompassState state;

    public TrackingCompassProperty(boolean wobble) {
        this(new TrackingCompassState(wobble));
    }

    private TrackingCompassProperty(TrackingCompassState state) {
        this.state = state;
    }

    @Override
    public float getValue(ItemStack stack, ClientWorld world, LivingEntity holder, int seed) {
        return this.state.getValue(stack, world, holder, seed);
    }

    @Override
    public MapCodec<TrackingCompassProperty> getCodec() {
        return CODEC;
    }
}
