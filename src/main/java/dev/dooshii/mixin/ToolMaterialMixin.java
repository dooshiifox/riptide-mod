package dev.dooshii.mixin;

import dev.dooshii.Riptide;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {
    @Mutable
    @Final
    @Shadow
    private int durability;
    @Mutable
    @Final
    @Shadow
    private TagKey<Item> repairItems;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ctor(TagKey<Block> tagKey, int durability, float f, float g, int j, TagKey<Item> repairItems, CallbackInfo ci) {
        // Wood
        if (durability == 59) this.durability = 100;
        // Stone
        else if (durability == 131) this.durability = 200;
        // Iron
        else if (durability == 250) this.durability = 500;
        // Diamond
        else if (durability == 1561) this.durability = 4000;
        // Gold
        else if (durability == 32) this.durability = 64;
        // Netherite
        else if (durability == 2031) this.durability = 40000;

        this.repairItems = repairItems;
        if (this.repairItems == ItemTags.NETHERITE_TOOL_MATERIALS) {
            this.repairItems = TagKey.of(RegistryKeys.ITEM, Riptide.id("netherite_tool_repair"));
        }
    }
}
