package dev.dooshii.item;

import dev.dooshii.Riptide;
import dev.dooshii.enchantment.ExtraEntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class LoggingItem extends Item {
    public LoggingItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.riptide.logging.tooltip").formatted(Formatting.GOLD, Formatting.ITALIC));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        // Get the current enchantment level.
        if (world instanceof ServerWorld) {
            Riptide.LOGGER.info("Enchant level: {}", user.getAttributeValue(ExtraEntityAttributes.MIDAIR_JUMP));
            return ActionResult.SUCCESS_SERVER;
        }
        return ActionResult.PASS;
    }
}
