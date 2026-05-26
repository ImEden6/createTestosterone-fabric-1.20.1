package net.mervyn.testosterone.mixin;

import net.mervyn.testosterone.util.EntityDataUtil;
import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.fluids.testosteroneFluids;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.mervyn.testosterone.network.packet.hudS2CPacket;
import net.mervyn.testosterone.network.testosteroneModMessages;
import net.mervyn.testosterone.advancements.testosteroneAdvancementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.emi.trinkets.api.TrinketsApi;

import java.util.List;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "checkTotemDeathProtection", at = @At("HEAD"), cancellable = true)
    private void onCheckTotemDeathProtection(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player && !damageSource.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            ItemStack totem = null;
            boolean hasTotem = false;

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (stack.is(testosteroneModItems.AFTERLIFE_TOTEM.get())) {
                    totem = stack;
                    hasTotem = true;
                    break;
                }
            }

            if (hasTotem) {
                player.setHealth(player.getMaxHealth());
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(
                        testosteroneModEffects.AFTERLIFE_EFFECT.get(),
                        1200, // AFTERLIFE_DURATION
                        0,
                        true,
                        false,
                        true));
                totem.shrink(1);
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!entity.level().isClientSide()) {
            if (isTouchingFluid(entity, testosteroneFluids.TESTOSTERONE_STILL, testosteroneFluids.TESTOSTERONE_FLOWING)) {
                entity.addEffect(new MobEffectInstance(testosteroneModEffects.TESTOSTERONE_EFFECT.get(), 20, 0));
            }
            if (isTouchingFluid(entity, testosteroneFluids.TRENBOLONE_STILL, testosteroneFluids.TRENBOLONE_FLOWING)) {
                entity.addEffect(new MobEffectInstance(testosteroneModEffects.ROID_RAGE_EFFECT.get(), 20, 0));
            }
        }
    }

    @Inject(method = "knockback", at = @At("HEAD"), cancellable = true)
    private void onKnockback(double strength, double ratioX, double ratioZ, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player && !getEquippedTie(player).isEmpty()) {
            ci.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        boolean hasEffect = entity.hasEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());
        boolean notBlocking = !entity.isBlocking();

        if (hasEffect && notBlocking) {
            boolean hasTie = false;
            boolean matej = false;

            if (entity instanceof Player player) {
                ItemStack tieStack = getEquippedTie(player);
                if (!tieStack.isEmpty()) {
                    hasTie = true;
                    if (tieStack.hasCustomHoverName() && tieStack.getHoverName().getString().equals("[matej]")) {
                        matej = true;
                    }
                }
            }

            long currentTick = entity.level().getGameTime();
            int amplifier = entity.getEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get()).getAmplifier() + 1;

            int damageTaken = EntityDataUtil.get(entity).getInt("testosterone:damage_taken_key");
            long endOfBlockTick = EntityDataUtil.get(entity).getLong("testosterone:end_of_block_tick");
            long beginTick = EntityDataUtil.get(entity).getLong("testosterone:begin_tick");

            if (source.is(DamageTypes.FELL_OUT_OF_WORLD) && !matej) {
                // Do not cancel generic/void death
            } else if (currentTick < endOfBlockTick) {
                damageTaken += (int) amount;

                if (hasTie && damageTaken > 100) {
                    damageTaken = 100;
                }

                EntityDataUtil.get(entity).putInt("testosterone:damage_taken_key", damageTaken);
                
                if (entity instanceof ServerPlayer player) {
                    if (amount >= 100) {
                        testosteroneAdvancementUtils.DAMAGE_TAKEN.trigger(player);
                    }
                }
                
                sendHudPacket(entity, endOfBlockTick, beginTick, (long) ConfigRegistry.TESTOSTERONE_DURATION.get() * amplifier, damageTaken);
                cir.setReturnValue(false);
            } else if (currentTick < endOfBlockTick + ((long) damageTaken * ConfigRegistry.TESTOSTERONE_MULTIPLIER.get()) / amplifier) {
                // Let the damage go through normally
            } else {
                endOfBlockTick = currentTick + ((long) ConfigRegistry.TESTOSTERONE_DURATION.get() * amplifier);
                EntityDataUtil.get(entity).putLong("testosterone:end_of_block_tick", endOfBlockTick);
                EntityDataUtil.get(entity).putLong("testosterone:begin_tick", currentTick);

                if (hasTie && amount > ConfigRegistry.TESTOSTERONE_MAX_DAMAGE.get()) {
                    damageTaken = ConfigRegistry.TESTOSTERONE_MAX_DAMAGE.get();
                } else {
                    damageTaken = (int) amount;
                }

                EntityDataUtil.get(entity).putInt("testosterone:damage_taken_key", damageTaken);

                if (entity instanceof ServerPlayer player) {
                    if (amount >= 100) {
                        testosteroneAdvancementUtils.DAMAGE_TAKEN.trigger(player);
                    }
                }

                sendHudPacket(entity, endOfBlockTick, currentTick, (long) ConfigRegistry.TESTOSTERONE_DURATION.get() * amplifier, damageTaken);
                cir.setReturnValue(false);
            }
        }
    }

    private void sendHudPacket(LivingEntity entity, long endOfBlockTick, long beginTick, long duration, int damageTaken) {
        if (entity instanceof ServerPlayer player) {
            int amplifier = entity.getEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get()).getAmplifier() + 1;
            long endOfCoolDownTick = endOfBlockTick + ((long) damageTaken * ConfigRegistry.TESTOSTERONE_MULTIPLIER.get()) / amplifier;
            long[] toSend = {endOfCoolDownTick, beginTick, duration};
            testosteroneModMessages.sendToPlayer(new hudS2CPacket(toSend), player);
        }
    }

    private boolean isTouchingFluid(LivingEntity entity, Fluid stillFluid, Fluid flowingFluid) {
        AABB box = entity.getBoundingBox();
        BlockPos minPos = BlockPos.containing(box.minX + 1.0E-3, box.minY + 1.0E-3, box.minZ + 1.0E-3);
        BlockPos maxPos = BlockPos.containing(box.maxX - 1.0E-3, box.maxY - 1.0E-3, box.maxZ - 1.0E-3);

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            var fluidState = entity.level().getFluidState(pos);
            if (fluidState.is(stillFluid) || fluidState.is(flowingFluid)) {
                return true;
            }
        }

        return false;
    }

    private ItemStack getEquippedTie(Player player) {
        try {
            Optional<?> trinketComponent = (Optional<?>) (Object) TrinketsApi.getTrinketComponent(player);
            if (trinketComponent.isEmpty()) {
                return ItemStack.EMPTY;
            }

            Object equipped = trinketComponent.get()
                    .getClass()
                    .getMethod("getEquipped", net.minecraft.world.item.Item.class)
                    .invoke(trinketComponent.get(), testosteroneModItems.TIE.get());

            if (!(equipped instanceof List<?> equippedList) || equippedList.isEmpty()) {
                return ItemStack.EMPTY;
            }

            Object firstMatch = equippedList.get(0);
            if (firstMatch instanceof Tuple<?, ?> tuple && tuple.getB() instanceof ItemStack stack) {
                return stack;
            }
        } catch (ReflectiveOperationException ignored) {
        }

        return ItemStack.EMPTY;
    }
}
