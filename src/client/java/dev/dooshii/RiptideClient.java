package dev.dooshii;

import dev.dooshii.tracking_compass.TrackingCompassProperty;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.numeric.NumericProperties;

public class RiptideClient implements ClientModInitializer {
	public void registerModelPredicateProviders() {
		NumericProperties.ID_MAPPER.put(Riptide.id("angle"), TrackingCompassProperty.CODEC);
	}

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		registerModelPredicateProviders();
	}
}