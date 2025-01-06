package dev.dooshii;

import dev.dooshii.component.type.TrackingCompassComponent;
import dev.dooshii.tracking_compass.TrackingCompassProperty;
import net.fabricmc.api.ClientModInitializer;
//import net.minecraft.client.item.CompassAnglePredicateProvider;
//import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.util.Identifier;

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