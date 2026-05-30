package net.mervyn.testosterone.effects;

import net.mervyn.testosterone.util.EntityDataUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class afterlifeEffect extends MobEffect {
    public static final String CORPSE_KEY = "testosterone:corpse";
    public static final String PROGRESS_KEY = "testosterone:progress";

    protected afterlifeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFF);
    }

    public static void cleanupCorpse(Player player, ServerLevel level, boolean teleportToCorpse) {
        CompoundTag data = EntityDataUtil.get(player);
        if (!data.hasUUID(CORPSE_KEY)) {
            return;
        }

        UUID corpseUuid = data.getUUID(CORPSE_KEY);
        Entity corpse = level.getEntity(corpseUuid);
        if (corpse != null) {
            if (teleportToCorpse) {
                player.teleportTo(corpse.getX(), corpse.getY(), corpse.getZ());
            }
            corpse.discard();
        }

        data.remove(CORPSE_KEY);
        data.remove(PROGRESS_KEY);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        if (entity instanceof Player player && player.level() instanceof ServerLevel serverLevel) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1, 1f);

            cleanupCorpse(player, serverLevel, false);

            ArmorStand corpseStand = EntityType.ARMOR_STAND.create(player.level());
            if (corpseStand != null) {
                corpseStand.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                corpseStand.setNoGravity(true);
                corpseStand.setSilent(true);
                corpseStand.setInvisible(true);
                corpseStand.setInvulnerable(true);

                ItemStack mobHead = new ItemStack(Items.SKELETON_SKULL);
                corpseStand.setItemSlot(EquipmentSlot.HEAD, mobHead);

                player.level().addFreshEntity(corpseStand);
                EntityDataUtil.get(player).putUUID(CORPSE_KEY, corpseStand.getUUID());
                EntityDataUtil.get(player).putInt(PROGRESS_KEY, 0);
            }
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);
        if (entity instanceof Player player && player.level() instanceof ServerLevel serverLevel) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1, 1f);
            player.setInvulnerable(false);
            cleanupCorpse(player, serverLevel, true);
        }
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity instanceof Player player) {
            Entity corpse = null;

            player.setInvulnerable(true);
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 20, 3, true, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20, 1, true, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, true, false, false));

            if (player.level() instanceof ServerLevel serverLevel && EntityDataUtil.get(player).hasUUID(CORPSE_KEY)) {
                UUID playerCorpse = EntityDataUtil.get(player).getUUID(CORPSE_KEY);
                corpse = serverLevel.getEntity(playerCorpse);
            }

            MobEffectInstance effectInstance = player.getEffect(testosteroneModEffects.AFTERLIFE_EFFECT.get());
            if (effectInstance != null) {
                int duration = effectInstance.getDuration();
                if (duration < 20 && duration >= 0) {
                    if (player.level() instanceof ServerLevel serverLevel) {
                        cleanupCorpse(player, serverLevel, true);
                    }
                    player.setInvulnerable(false);
                    player.kill();
                } else {
                    if (corpse != null) {
                        int progress = EntityDataUtil.get(player).getInt(PROGRESS_KEY);

                        if (player.isShiftKeyDown() && player.distanceToSqr(corpse) < 10) {
                            progress++;

                            player.addEffect(new MobEffectInstance(
                                    testosteroneModEffects.AFTERLIFE_EFFECT.get(),
                                    duration + 1,
                                    0,
                                    true,
                                    false,
                                    true));

                            if (progress >= 100) {
                                player.removeAllEffects();
                            }
                        } else if (progress > 0) {
                            progress--;
                        }

                        EntityDataUtil.get(player).putInt(PROGRESS_KEY, progress);
                        player.sendSystemMessage(Component.literal(String.valueOf(progress)));
                    }
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
