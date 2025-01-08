package dev.dooshii.enchantment;

import dev.dooshii.Riptide;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class ExtraEnchantmentEffects implements EnchantmentEffectComponentTypes {
    public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> MIDAIR_JUMP = registerEnchantmentEffect(
            "midair_jump",
            builder -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentValueEffect.CODEC, LootContextTypes.ENCHANTED_ITEM).listOf())
    );

    private static <T> ComponentType<T> registerEnchantmentEffect(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Riptide.id(id), ((ComponentType.Builder) builderOperator.apply(ComponentType.builder())).build());
    }

    public static int getMidairJump(ServerWorld world, LivingEntity user) {
        MutableFloat mutableFloat = new MutableFloat(0.0F);
        forEachEnchantment(user, (enchantment, level, context) -> {
            modifyMidairJump(enchantment.value(), world, level, user, mutableFloat);
        });
        return Math.max(0, mutableFloat.intValue());
    }

    private static void forEachEnchantment(LivingEntity entity, ContextAwareConsumer contextAwareConsumer) {
        for (EquipmentSlot equipmentSlot : EquipmentSlot.VALUES) {
            forEachEnchantment(entity.getEquippedStack(equipmentSlot), equipmentSlot, entity, contextAwareConsumer);
        }
    }

    private static void forEachEnchantment(ItemStack stack, EquipmentSlot slot, LivingEntity entity, ContextAwareConsumer contextAwareConsumer) {
        if (stack.isEmpty()) {
            return;
        }
        
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.get(DataComponentTypes.ENCHANTMENTS);
        if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
            EnchantmentEffectContext enchantmentEffectContext = new EnchantmentEffectContext(stack, slot, entity);

            for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
                RegistryEntry<Enchantment> registryEntry = entry.getKey();
                if (registryEntry.value().slotMatches(slot)) {
                    contextAwareConsumer.accept(registryEntry, entry.getIntValue(), enchantmentEffectContext);
                }
            }
        }
    }

    private static void forEachEnchantment(ItemStack stack, Consumer consumer) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);

        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
        }
    }

    @FunctionalInterface
    interface Consumer {
        void accept(RegistryEntry<Enchantment> enchantment, int level);
    }

    @FunctionalInterface
    interface ContextAwareConsumer {
        void accept(RegistryEntry<Enchantment> enchantment, int level, EnchantmentEffectContext context);
    }

    private static void modifyMidairJump(Enchantment enchantment, ServerWorld world, int level, Entity user, MutableFloat tridentReturnAcceleration) {
        modifyValue(enchantment, MIDAIR_JUMP, world, level, user, tridentReturnAcceleration);
    }

    private static void modifyValue(
            Enchantment enchantment,
            ComponentType<List<EnchantmentEffectEntry<EnchantmentValueEffect>>> type,
            ServerWorld world,
            int level,
            Entity user,
            MutableFloat value
    ) {
        applyEffects(
                enchantment.getEffect(type),
                createEnchantedEntityLootContext(world, level, user, user.getPos()),
                effect -> value.setValue(effect.apply(level, user.getRandom(), value.floatValue()))
        );
    }

    private static <T> void applyEffects(List<EnchantmentEffectEntry<T>> entries, LootContext lootContext, java.util.function.Consumer<T> effectConsumer) {
        for (EnchantmentEffectEntry<T> enchantmentEffectEntry : entries) {
            if (enchantmentEffectEntry.test(lootContext)) {
                effectConsumer.accept(enchantmentEffectEntry.effect());
            }
        }
    }

    private static LootContext createEnchantedEntityLootContext(ServerWorld world, int level, Entity entity, Vec3d pos) {
        LootWorldContext lootWorldContext = new LootWorldContext.Builder(world)
                .add(LootContextParameters.THIS_ENTITY, entity)
                .add(LootContextParameters.ENCHANTMENT_LEVEL, level)
                .add(LootContextParameters.ORIGIN, pos)
                .build(LootContextTypes.ENCHANTED_ENTITY);
        return new LootContext.Builder(lootWorldContext).build(Optional.empty());
    }
}
