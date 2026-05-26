package net.mervyn.testosterone.effects;

import net.mervyn.testosterone.util.EntityDataUtil;
import com.simibubi.create.foundation.damageTypes.CreateDamageSources;
import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.particles.airPassingParticleData;
import net.mervyn.testosterone.particles.runParticleData;
import net.mervyn.testosterone.sounds.testosteroneModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class roidRageEffect extends MobEffect {
    public static final String MARKED_KEY = "testosterone:marked_key";
    public static final String MARKED_BY_KEY = "testosterone:marked_by_key";

    public static final String SPEED_KEY = "testosterone:speed_key";
    public static final String JUMPED_TICK_KEY = "testosterone:jumped_tick_key";
    public static final String READY_TO_JUMP_KEY = "testosterone:ready_to_jump_key";
    public static final String IN_JUMP_KEY = "testosterone:in_jump_key";
    public static final String SWIMMING_KEY = "testosterone:swimming_key";

    private final UUID speedAttributeUUID;

    public static int getSpeed(Player player) {
        return EntityDataUtil.get(player).getInt(SPEED_KEY);
    }

    public static boolean isSwimming(Player player) {
        return EntityDataUtil.get(player).getBoolean(SWIMMING_KEY);
    }

    public roidRageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCC0000);
        speedAttributeUUID = UUID.fromString("a3c377ee-6949-4a9c-a49f-c87a8420200a");

        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                speedAttributeUUID.toString(),
                0,
                AttributeModifier.Operation.ADDITION
        );
    }

    private static void applyForce(Player player, double x, double y, double z) {
        if (player instanceof ServerPlayer serverPlayer) {
            player.push(x, y, z);
            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        entity.setMaxUpStep(1.6f);

        if (entity instanceof Player player) {
            Level level = player.level();
            int speed = EntityDataUtil.get(player).getInt(SPEED_KEY);
            boolean swimming = EntityDataUtil.get(player).getBoolean(SWIMMING_KEY);

            if (level.isClientSide() && ConfigRegistry.DISPLAY_SPEED.get()) {
                player.displayClientMessage(Component.literal(String.valueOf(speed)), true);
            }

            if (player.isFallFlying() && !ConfigRegistry.ALLOW_ELYTRA.get()) {
                player.setSprinting(false);
            }

            if (swimming) {
                player.setPose(Pose.SWIMMING);
            }

            long jumpedTick = EntityDataUtil.get(player).getLong(JUMPED_TICK_KEY);
            boolean readyToJump = EntityDataUtil.get(player).getBoolean(READY_TO_JUMP_KEY);
            boolean inJump = EntityDataUtil.get(player).getBoolean(IN_JUMP_KEY);

            AttributeInstance attributeInstance = player.getAttribute(Attributes.MOVEMENT_SPEED);

            if (attributeInstance != null) {
                attributeInstance.removeModifier(speedAttributeUUID);
                AttributeModifier newModifier = new AttributeModifier(speedAttributeUUID, "trenspeed", speed * ConfigRegistry.SPEED_MULTIPLIER.get(), AttributeModifier.Operation.ADDITION);
                attributeInstance.addTransientModifier(newModifier);
            }

            if (player.onGround()) {
                if (inJump && level.getGameTime() - jumpedTick > 5) {
                    inJump = false;
                }
                swimming = false;
            }

            if (player.isSprinting()) {
                if (speed < ConfigRegistry.MAX_SPEED.get() * (amplifier + 1)) {
                    speed++;
                }
            } else if (!player.isCrouching()) {
                speed = 0;
            }

            if (player.isCrouching()) {
                if (inJump) {
                    applyForce(player, 0, -1 * speed * ConfigRegistry.JUMP_MULTIPLIER.get(), 0);
                    swimming = true;
                } else {
                    readyToJump = true;
                }
            } else if (readyToJump && player.onGround()) {
                applyForce(player, 0, speed * ConfigRegistry.JUMP_MULTIPLIER.get(), 0);
                readyToJump = false;
                inJump = true;
                jumpedTick = level.getGameTime();
            }

            if (speed > ConfigRegistry.ABILITY_SPEED.get()) {
                if (!level.isClientSide() && !player.isCrouching()) {
                    ServerLevel serverLevel = (ServerLevel) level;
                    serverLevel.sendParticles(new runParticleData(player.getUUID(), ConfigRegistry.TRAIL_DURATION.get(), serverLevel.getGameTime()),
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            0,
                            0, 0, 0,
                            0
                    );

                    if (new Random().nextInt(ConfigRegistry.MAX_SPEED.get()) < speed) {
                        serverLevel.sendParticles(new airPassingParticleData(player.getUUID()),
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                0,
                                0, 0, 0,
                                0
                        );
                    }
                }

                AABB playerBB = player.getBoundingBox();
                List<Entity> collidingEntities = level.getEntities(
                        player,
                        playerBB,
                        each -> each != player && entity.getBoundingBox().intersects(playerBB)
                );

                float rot = player.getYRot();
                double rotRad = Math.toRadians(rot);

                if (!inJump && !player.onGround()) {
                    double mul = ConfigRegistry.TREN_IN_AIR_MUL.get() * 0.001;
                    player.addDeltaMovement(new Vec3(-Math.sin(rotRad) * speed * mul, 0, Math.cos(rotRad) * speed * mul));
                }

                for (Entity other : collidingEntities) {
                    if (other instanceof LivingEntity) {
                        EntityDataUtil.get(other).putLong(MARKED_KEY, level.getGameTime());
                        EntityDataUtil.get(other).putUUID(MARKED_BY_KEY, player.getUUID());

                        other.hurt(CreateDamageSources.runOver(level, player), (float) speed / 50);
                        level.playSound(null, player.blockPosition(), testosteroneModSounds.ENEMY_HIT_SFX.get(), SoundSource.PLAYERS);

                        other.addDeltaMovement(new Vec3(-Math.sin(rotRad) * speed * 0.01, speed * 0.002, Math.cos(rotRad) * speed * 0.01));
                    }
                }

                BlockPos blockAtFeet = new BlockPos(
                        (int) Math.floor(player.getX()),
                        (int) Math.floor(player.getY()),
                        (int) Math.floor(player.getZ())
                );

                if (level.getBlockState(blockAtFeet).getCollisionShape(level, blockAtFeet, CollisionContext.of(player)).isEmpty()) {
                    BlockPos blockBelow = new BlockPos(
                            (int) Math.floor(player.getX()),
                            (int) Math.floor(player.getY() - 0.2),
                            (int) Math.floor(player.getZ())
                    );

                    if (!level.getFluidState(blockBelow).isEmpty()) {
                        if (player.isCrouching()) {
                            speed = 0;
                        } else {
                            Vec3 motion = player.getDeltaMovement();
                            if (motion.y < 0) {
                                player.setDeltaMovement(motion.x, 0, motion.z);
                            }
                            player.setPos(player.getX(), blockBelow.getY() + 1, player.getZ());
                            player.setOnGround(true);
                            player.fallDistance = 0;
                        }
                    }
                }
            }

            EntityDataUtil.get(player).putInt(SPEED_KEY, speed);
            EntityDataUtil.get(player).putLong(JUMPED_TICK_KEY, jumpedTick);
            EntityDataUtil.get(player).putBoolean(READY_TO_JUMP_KEY, readyToJump);
            EntityDataUtil.get(player).putBoolean(IN_JUMP_KEY, inJump);
            EntityDataUtil.get(player).putBoolean(SWIMMING_KEY, swimming);
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.removeAttributeModifiers(entity, attributes, amplifier);
        entity.setMaxUpStep(0.6f);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
