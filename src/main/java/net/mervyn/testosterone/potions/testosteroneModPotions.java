package net.mervyn.testosterone.potions;

import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class testosteroneModPotions {
    public static RegistryObject<Potion> TESTOSTERONE_POTION;
    public static RegistryObject<Potion> ROID_RAGE_POTION;

    public static void register() {
        TESTOSTERONE_POTION = RegistryObject.of(Registry.register(BuiltInRegistries.POTION,
                new ResourceLocation(testosterone.MOD_ID, "man_power_potion"),
                new Potion(new MobEffectInstance(testosteroneModEffects.TESTOSTERONE_EFFECT.get(), 12000, 0))));

        ROID_RAGE_POTION = RegistryObject.of(Registry.register(BuiltInRegistries.POTION,
                new ResourceLocation(testosterone.MOD_ID, "roid_rage_potion"),
                new Potion(new MobEffectInstance(testosteroneModEffects.ROID_RAGE_EFFECT.get(), 12000, 0))));
    }
}
