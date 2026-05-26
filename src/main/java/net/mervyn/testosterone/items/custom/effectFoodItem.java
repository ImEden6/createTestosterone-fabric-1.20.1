package net.mervyn.testosterone.items.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class effectFoodItem extends Item {

    private final Supplier<MobEffect> effectSupplier;
    private final int duration;
    private final int amplifier;

    public effectFoodItem(Properties properties, Supplier<MobEffect> effectSupplier, int duration, int amplifier) {
        super(properties);
        this.effectSupplier = effectSupplier;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide() && entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(effectSupplier.get(), duration, amplifier, false, false, true));
        }

        return result;
    }
}
