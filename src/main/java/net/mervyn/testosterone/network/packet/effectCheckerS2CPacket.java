package net.mervyn.testosterone.network.packet;

import net.mervyn.testosterone.util.EntityDataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class effectCheckerS2CPacket {
    public static final String EFFECT_CHECKER_KEY = "testosterone:effect_checker_key";
    private final int[] data;

    public effectCheckerS2CPacket(int[] hasEffect) {
        this.data = hasEffect;
    }

    public effectCheckerS2CPacket(FriendlyByteBuf buf) {
        this.data = buf.readVarIntArray();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(data);
    }

    public void handle(Minecraft client) {
        if (client.level != null) {
            Entity entity = client.level.getEntity(data[0]);
            if (entity != null) {
                EntityDataUtil.get(entity).putInt(EFFECT_CHECKER_KEY, data[1]);
            }
        }
    }
}
