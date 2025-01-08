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
    public static final RegistryEntry<EntityAttribute> ELYTRA_BOOST = register(
            "elytra_boost", new ClampedEntityAttribute("attribute.name.elytra_boost", 0.0, 0.0, 1000.0).setTracked(true)
    );
    public static final RegistryEntry<EntityAttribute> ELYTRA_BOOST_STRENGTH = register(
            "elytra_boost_strength", new ClampedEntityAttribute("attribute.name.elytra_boost_strength", 2, 0.0, 50.0).setTracked(true)
    );

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Riptide.id(id), attribute);
    }

    public static void init() {
        // lazy initialisation of static vars breaks registering, so assign it to a junk variable.
        var _midairJump = ExtraEntityAttributes.MIDAIR_JUMP;
        var _elytraBoost = ExtraEntityAttributes.ELYTRA_BOOST;
        var _elytraBoostStrength = ExtraEntityAttributes.ELYTRA_BOOST_STRENGTH;
    }
}
