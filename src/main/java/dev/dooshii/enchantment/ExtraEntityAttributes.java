package dev.dooshii.enchantment;

import dev.dooshii.Riptide;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ExtraEntityAttributes extends EntityAttributes {
    public static final RegistryEntry<EntityAttribute> MIDAIR_JUMP = register(
            "midair_jump", new ClampedEntityAttribute("attribute.name.midair_jump", 0.0, 0.0, 1000.0).setTracked(true)
    );

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Riptide.id(id), attribute);
    }
}
