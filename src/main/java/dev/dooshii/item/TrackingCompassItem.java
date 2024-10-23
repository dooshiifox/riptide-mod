package dev.dooshii.item;

import com.mojang.authlib.GameProfile;
import dev.dooshii.ModComponents;
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

    public Optional<GameProfile> getTrackedPlayer(ItemStack stack) {
        TrackingCompassComponent component = stack.getOrDefault(ModComponents.TRACKING_COMPASS_COMPONENT, new TrackingCompassComponent(Optional.empty(), Optional.empty()));
        return component.player();
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return this.getTrackedPlayer(stack).isPresent() || super.hasGlint(stack);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        this.getTrackedPlayer(stack).ifPresentOrElse(playerName -> {
            tooltip.add(Text.translatable("item.riptide.tracking_compass.info.player", playerName.getName()).formatted(Formatting.GOLD));
        }, () -> {
            tooltip.add(Text.translatable("item.riptide.tracking_compass.info.no_target").formatted(Formatting.GRAY));
        });
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
        TrackingCompassComponent newComponent = new TrackingCompassComponent(Optional.of(globalPos), Optional.of(targetPlayer.getGameProfile()));
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
