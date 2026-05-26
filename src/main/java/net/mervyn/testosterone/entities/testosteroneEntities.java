package net.mervyn.testosterone.entities;

import net.mervyn.testosterone.entities.rat.ratEntity;
import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class testosteroneEntities {
    public static RegistryObject<EntityType<ratEntity>> RAT;

    public static void register() {
        RAT = RegistryObject.of(Registry.register(BuiltInRegistries.ENTITY_TYPE,
                new ResourceLocation(testosterone.MOD_ID, "rat"),
                EntityType.Builder.of(ratEntity::new, MobCategory.CREATURE)
                        .sized(1f, 1f)
                        .build("rat")));
    }
}
