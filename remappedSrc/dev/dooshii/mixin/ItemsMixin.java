package dev.dooshii.mixin;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ItemsMixin {
    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 64),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/FlintAndSteelItem;"),
                    to = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/FlintAndSteelItem;", shift = At.Shift.BY, by = 5)
            ),
            allow = 1
    )
    private static int flintAndSteelDurability(int constant) {
        return 250;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 384),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/BowItem;"),
                    to = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/BowItem;", shift = At.Shift.BY, by = 5)
            ),
            allow = 1
    )
    private static int bowDurability(int constant) {
        return 1250;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 64),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/FishingRodItem;"),
                    to = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/FishingRodItem;", shift = At.Shift.BY, by = 5)
            ),
            allow = 1
    )
    private static int fishingRodDurability(int constant) {
        return 400;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 238),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/ShearsItem;"),
                    to = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/ShearsItem;", shift = At.Shift.BY, by = 5)
            ),
            allow = 1
    )
    private static int shearsDurability(int constant) {
        return 750;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 500),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/MaceItem;")
            ),
            allow = 1
    )
    private static int maceDurability(int constant) {
        return 5000;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 336),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/ShieldItem;"),
                    to = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/ShieldItem;", shift = At.Shift.BY, by = 5)
            ),
            allow = 1
    )
    private static int shieldDurability(int constant) {
        return 1000;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 250),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/TridentItem;")
            ),
            allow = 1
    )
    private static int tridentDurability(int constant) {
        return 3000;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 465),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/CrossbowItem;")
            ),
            allow = 1
    )
    private static int crossbowDurability(int constant) {
        // Give the crossbow more durability than a bow so it has more reason to exist
        return 2000;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 64),
            slice = @Slice(
                    from = @At(value = "NEW", target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/BrushItem;")
            ),
            allow = 1
    )
    private static int brushDurability(int constant) {
        return 200;
    }
}
