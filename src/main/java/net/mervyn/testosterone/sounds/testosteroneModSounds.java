package net.mervyn.testosterone.sounds;

import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class testosteroneModSounds {
    public static final RegistryObject<SoundEvent> JOHN_ROCK_DEACTIVATION = registerSoundEvents("john_rock_deactivation");
    public static final RegistryObject<SoundEvent> JOHN_ROCK_ACTIVATION = registerSoundEvents("john_rock_activation");
    public static final RegistryObject<SoundEvent> MACH_1_SFX = registerSoundEvents("mach_1_sfx");
    public static final RegistryObject<SoundEvent> MACH_2_SFX = registerSoundEvents("mach_2_sfx");
    public static final RegistryObject<SoundEvent> GROUND_SLAM_SFX = registerSoundEvents("ground_slam_sfx");
    public static final RegistryObject<SoundEvent> ENEMY_HIT_SFX = registerSoundEvents("enemy_hit_sfx");

    public static final RegistryObject<SoundEvent> RAT_SNIFF1 = registerSoundEvents("rat_sniff1");
    public static final RegistryObject<SoundEvent> RAT_SNIFF2 = registerSoundEvents("rat_sniff2");
    public static final RegistryObject<SoundEvent> RAT_SQUEAK1 = registerSoundEvents("rat_squeak1");
    public static final RegistryObject<SoundEvent> RAT_SQUEAK2 = registerSoundEvents("rat_squeak2");
    public static final RegistryObject<SoundEvent> RAT_RUN = registerSoundEvents("rat_run");
    public static final RegistryObject<SoundEvent> RAT_HURT1 = registerSoundEvents("rat_hurt1");
    public static final RegistryObject<SoundEvent> RAT_HURT2 = registerSoundEvents("rat_hurt2");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(testosterone.MOD_ID, name);
        SoundEvent event = SoundEvent.createVariableRangeEvent(id);
        Registry.register(BuiltInRegistries.SOUND_EVENT, id, event);
        return RegistryObject.of(event);
    }

    public static void register() {
        // Triggers static block loading
    }
}
