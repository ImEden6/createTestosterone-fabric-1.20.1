package net.mervyn.testosterone.network.packet;

import net.mervyn.testosterone.util.EntityDataUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;

public class hudS2CPacket {
    public static final String END_OF_COOLDOWN_TICK_KEY = "testosterone:end_of_cooldown_tick_key";
    public static final String ACTUAL_BEGIN_TICK_KEY = "testosterone:actual_begin_tick_key";
    public static final String BEGIN_TICK_KEY = "testosterone:begin_tick_key";
    public static final String DURATION_KEY = "testosterone:duration_key";

    private final long[] data;

    public hudS2CPacket(long[] data) {
        this.data = data;
    }

    public hudS2CPacket(FriendlyByteBuf buf) {
        this.data = buf.readLongArray();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeLongArray(data);
    }

    public void handle(Minecraft client) {
        LocalPlayer player = client.player;
        if (player != null) {
            EntityDataUtil.get(player).putLong(END_OF_COOLDOWN_TICK_KEY, data[0]);
            EntityDataUtil.get(player).putLong(ACTUAL_BEGIN_TICK_KEY, data[1]);
            EntityDataUtil.get(player).putLong(BEGIN_TICK_KEY, data[0] - data[1]);
            EntityDataUtil.get(player).putLong(DURATION_KEY, data[2]);
        }
    }
}
