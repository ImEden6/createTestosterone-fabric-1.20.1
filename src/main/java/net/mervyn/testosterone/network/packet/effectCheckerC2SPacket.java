package net.mervyn.testosterone.network.packet;

import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.network.testosteroneModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class effectCheckerC2SPacket {
    private final int livingEntityID;

    public effectCheckerC2SPacket(int livingEntityID) {
        this.livingEntityID = livingEntityID;
    }

    public effectCheckerC2SPacket(FriendlyByteBuf buf) {
        this.livingEntityID = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(livingEntityID);
    }

    public void handle(MinecraftServer server, ServerPlayer player) {
        if (player != null) {
            LivingEntity livingEntity = (LivingEntity) player.level().getEntity(livingEntityID);
            if (livingEntity == null) return;
            int effectInt;

            boolean hasTestosterone = livingEntity.hasEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get());
            boolean hasRoidRage = livingEntity.hasEffect(testosteroneModEffects.ROID_RAGE_EFFECT.get());

            if (hasTestosterone && hasRoidRage) {
                int testosteroneScore = (livingEntity.getEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get()).getAmplifier() + 1) * livingEntity.getEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get()).getDuration();
                int roidRageScore = (livingEntity.getEffect(testosteroneModEffects.ROID_RAGE_EFFECT.get()).getAmplifier() + 1) * livingEntity.getEffect(testosteroneModEffects.ROID_RAGE_EFFECT.get()).getDuration();

                if (testosteroneScore > roidRageScore) {
                    effectInt = 1;
                } else {
                    effectInt = 2;
                }
            } else if (hasTestosterone) {
                effectInt = 1;
            } else if (hasRoidRage) {
                effectInt = 2;
            } else {
                effectInt = 0;
            }

            int[] nums = {livingEntityID, effectInt};
            testosteroneModMessages.sendToPlayer(new effectCheckerS2CPacket(nums), player);
        }
    }
}
