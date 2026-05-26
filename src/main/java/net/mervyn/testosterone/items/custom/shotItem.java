package net.mervyn.testosterone.items.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class shotItem extends Item {

    private final Supplier<MobEffect> effectSupplier;
    private final int amplifier;
    private final boolean improved;

    public shotItem(Properties properties, Supplier<MobEffect> effectSupplier) {
        this(properties, effectSupplier, 0, false);
    }

    public shotItem(Properties properties, Supplier<MobEffect> effectSupplier, int amplifier) {
        this(properties, effectSupplier, amplifier, false);
    }

    public shotItem(Properties properties, Supplier<MobEffect> effectSupplier, int amplifier, boolean improved) {
        super(properties);
        this.effectSupplier = effectSupplier;
        this.amplifier = amplifier;
        this.improved = improved;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide() && entity instanceof Player player) {
            int duration = improved ? 12000 : 6000;
            player.addEffect(new MobEffectInstance(effectSupplier.get(), duration, amplifier, false, false, true));
            player.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0f, 1.5f);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
