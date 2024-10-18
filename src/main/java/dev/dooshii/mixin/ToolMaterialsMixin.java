package dev.dooshii.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ToolMaterials.class)
public class ToolMaterialsMixin {
    @Mutable
    @Final
    @Shadow
    private int itemDurability;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ctor(String string, int i, TagKey<Block> inverseTag, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient, CallbackInfo ci) {
        // Wood
        if (itemDurability == 59) this.itemDurability = 100;
        // Stone
        else if (itemDurability == 131) this.itemDurability = 200;
        // Iron
        else if (itemDurability == 250) this.itemDurability = 500;
        // Diamond
        else if (itemDurability == 1561) this.itemDurability = 4000;
        // Gold
        else if (itemDurability == 32) this.itemDurability = 64;
        // Netherite
        else if (itemDurability == 2031) this.itemDurability = 40000;
    }
}
