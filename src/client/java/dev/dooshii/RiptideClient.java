package dev.dooshii;

import dev.dooshii.component.type.TrackingCompassComponent;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;

import java.util.UUID;

public class RiptideClient implements ClientModInitializer {
	public static void registerModelPredicateProviders() {
		ModelPredicateProviderRegistry.register(ModItems.TRACKING_COMPASS, Identifier.ofVanilla("angle"), new CompassAnglePredicateProvider((world, stack, entity) -> {
			TrackingCompassComponent component = stack.get(ModComponents.TRACKING_COMPASS_COMPONENT);
			// Nothing is tracked
			if (component == null) return null;

			// Player holding item is one being tracked
			if (component.isTargetedPlayer(entity)) return null;

			return component.target().orElse(null);
		}));
	}

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		registerModelPredicateProviders();
	}
}