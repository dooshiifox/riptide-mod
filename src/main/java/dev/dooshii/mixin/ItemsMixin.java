package dev.dooshii.mixin;

import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.function.Function;

@Mixin(Items.class)
public class ItemsMixin {
    @Inject(method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;", at = @At("HEAD"))
    private static void a(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir) {
        record DamageAdjuster(RegistryKey<Item> key, Item.Settings settings) {
            DamageAdjuster adjust(String id, int damage) {
                if (key.getValue().equals(Identifier.ofVanilla(id))) {
                    settings.maxDamage(damage);
                }
                return this;
            }
        }

        new DamageAdjuster(key, settings)
                .adjust("flint_and_steel", 250)
                .adjust("carrot_on_a_stick", 100)
                .adjust("warped_fungus_on_a_stick", 250)
                .adjust("bow", 1250)
                .adjust("fishing_rod", 400)
                .adjust("shears", 750)
                // Maces are hard to get, make them durable!
                .adjust("mace", 5000)
                .adjust("shield", 1000)
                // Tridents are hard to get, make them durable!
                .adjust("trident", 3000)
                // Give the crossbow more durability than a bow so it has more reason to exist
                .adjust("crossbow", 2000)
                .adjust("brush", 200);
    }
}
