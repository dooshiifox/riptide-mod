package dev.dooshii;

import dev.dooshii.component.type.TrackingCompassComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {
    public static final ComponentType<TrackingCompassComponent> TRACKING_COMPASS_COMPONENT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Riptide.id("tracking_compass_target"),
            ComponentType.<TrackingCompassComponent>builder().codec(TrackingCompassComponent.CODEC).build()
    );

    protected static void initialize() {}

}
