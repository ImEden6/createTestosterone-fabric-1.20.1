package net.mervyn.testosterone.effects;

import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public class testosteroneModEffects {
    public static RegistryObject<MobEffect> TESTOSTERONE_EFFECT;
    public static RegistryObject<MobEffect> AFTERLIFE_EFFECT;
    public static RegistryObject<MobEffect> ROID_RAGE_EFFECT;

    public static void registerEffects() {
        TESTOSTERONE_EFFECT = RegistryObject.of(Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(testosterone.MOD_ID, "man_power"), new testosteroneEffect()));
        AFTERLIFE_EFFECT = RegistryObject.of(Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(testosterone.MOD_ID, "afterlife"), new afterlifeEffect()));
        ROID_RAGE_EFFECT = RegistryObject.of(Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(testosterone.MOD_ID, "roid_rage"), new roidRageEffect()));
    }
}
