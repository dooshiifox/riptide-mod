package dev.dooshii.item;

import dev.dooshii.ModComponents;
import dev.dooshii.Riptide;
import dev.dooshii.component.type.TrackingCompassComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class TrackingCompassItem extends Item {
    public TrackingCompassItem(Settings settings) {
        super(settings);
    }

    static public TrackingCompassComponent getComponent(ItemStack stack) {
        return stack.getOrDefault(ModComponents.TRACKING_COMPASS_COMPONENT, new TrackingCompassComponent(
                Optional.empty(),
                Optional.empty(),
                false
        ));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return getComponent(stack).player().isPresent() || super.hasGlint(stack);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var component = getComponent(stack);

        if (component.player().isEmpty()) {
            tooltip.add(Text.translatable("item.riptide.tracking_compass.info.no_target").formatted(Formatting.GRAY));
            return;
        }

        var player = component.player().get();
        tooltip.add(Text.translatable("item.riptide.tracking_compass.info.player", player.getName()).formatted(Formatting.GOLD));
        // If there is a player but no target, the player is offline.
        // If there is a player and a target but the target is in the wrong world, show an error.
        // If the player is online and in the same world, show no additional tooltip.
        Riptide.LOGGER.info("Target: {} | diff dim: {}", component.target(), component.differentDimension());
        if (component.target().isEmpty()) {
            tooltip.add(Text.translatable("item.riptide.tracking_compass.info.player_offline").formatted(Formatting.GRAY, Formatting.ITALIC));
        } else if (component.differentDimension()) {
            tooltip.add(Text.translatable("item.riptide.tracking_compass.info.player_different_dimension").formatted(Formatting.GRAY, Formatting.ITALIC));
        }
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!(entity instanceof PlayerEntity targetPlayer)) {
            return ActionResult.FAIL;
        }

        // Don't do anything on the client
        if (entity.getWorld().isClient()) {
            return ActionResult.SUCCESS;
        }

        GlobalPos globalPos = TrackingCompassComponent.getEntityGPos(targetPlayer);
        TrackingCompassComponent newComponent = new TrackingCompassComponent(
                Optional.of(globalPos),
                Optional.of(targetPlayer.getGameProfile()),
                false
        );
        stack.set(ModComponents.TRACKING_COMPASS_COMPONENT, newComponent);
        user.setStackInHand(hand, stack);
        return ActionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }

        TrackingCompassComponent component = stack.get(ModComponents.TRACKING_COMPASS_COMPONENT);
        if (component == null) {
            return;
        }

        stack.set(ModComponents.TRACKING_COMPASS_COMPONENT, component.update(serverWorld));
    }
}
