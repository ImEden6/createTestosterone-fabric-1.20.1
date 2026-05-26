package net.mervyn.testosterone.effects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class testosteroneEffect extends MobEffect {
    public static final String BEGIN_TICK = "testosterone:begin_tick";
    public static final String DAMAGE_TAKEN = "testosterone:damage_taken_key";
    public static final String END_OF_BLOCK_TICK = "testosterone:end_of_block_tick";

    public testosteroneEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF8C0A);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        ResourceLocation estrogenEffectId = new ResourceLocation("estrogen", "estrogen");
        MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(estrogenEffectId);

        if (effect != null && livingEntity.hasEffect(effect)) {
            livingEntity.removeEffect(effect);
            livingEntity.removeEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
