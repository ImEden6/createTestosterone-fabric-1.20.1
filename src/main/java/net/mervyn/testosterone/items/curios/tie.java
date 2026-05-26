package net.mervyn.testosterone.items.curios;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class tie extends Item implements Trinket {

    public tie(Properties pProperties) {
        super(new Item.Properties().stacksTo(1).defaultDurability(0));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (stack.getDisplayName().getString().equals("[matej]")) {
            MobEffectInstance effect = entity.getEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());

            if (effect != null) {
                if (effect.getAmplifier() == 0) {
                    MobEffectInstance effectInstance = new MobEffectInstance(testosteroneModEffects.TESTOSTERONE_EFFECT.get(),
                            effect.getDuration(), 1, effect.isAmbient(), effect.isVisible(), effect.showIcon());

                    entity.addEffect(effectInstance);
                }
            }
        }
    }

    public static ItemStack getTieByColor(String color) {
        ItemStack stack = new ItemStack(testosteroneModItems.TIE.get());
        CompoundTag nbtData = new CompoundTag();
        nbtData.putString("color", color);
        stack.setTag(nbtData);
        return stack;
    }
}


