package dev.dooshii;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static TagKey<EntityType<?>> LEAVES_WALKABLE = of("leaves_walkable");

    public static void onInitialize() {
    }

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Riptide.id(id));
    }
}
