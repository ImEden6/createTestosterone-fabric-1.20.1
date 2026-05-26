package net.mervyn.testosterone.particles;

import com.mojang.serialization.Codec;
import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;

public final class testosteroneModParticles {
    public static RegistryObject<ParticleType<runParticleData>> TESTOSTERONE_RUN;
    public static RegistryObject<ParticleType<airPassingParticleData>> AIR_PASSING;

    public static void register() {
        TESTOSTERONE_RUN = RegistryObject.of(Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                new ResourceLocation(testosterone.MOD_ID, "testosterone_run"),
                new ParticleType<>(false, runParticleData.DESERIALIZER) {
                    @Override
                    public Codec<runParticleData> codec() {
                        return null;
                    }
                }));

        AIR_PASSING = RegistryObject.of(Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                new ResourceLocation(testosterone.MOD_ID, "air_passing"),
                new ParticleType<>(false, airPassingParticleData.DESERIALIZER) {
                    @Override
                    public Codec<airPassingParticleData> codec() {
                        return null;
                    }
                }));
    }
}
