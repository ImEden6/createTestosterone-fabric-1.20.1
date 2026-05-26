package net.mervyn.testosterone.network;

import net.mervyn.testosterone.network.packet.effectCheckerC2SPacket;
import net.mervyn.testosterone.network.packet.effectCheckerS2CPacket;
import net.mervyn.testosterone.network.packet.hudS2CPacket;
import net.mervyn.testosterone.testosterone;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class testosteroneModMessages {
    public static final ResourceLocation EFFECT_CHECKER_C2S = new ResourceLocation(testosterone.MOD_ID, "effect_checker_c2s");
    public static final ResourceLocation EFFECT_CHECKER_S2C = new ResourceLocation(testosterone.MOD_ID, "effect_checker_s2c");
    public static final ResourceLocation HUD_S2C = new ResourceLocation(testosterone.MOD_ID, "hud_s2c");

    public static void register() {
        // Register C2S packets on the server
        ServerPlayNetworking.registerGlobalReceiver(EFFECT_CHECKER_C2S, (server, player, handler, buf, responseSender) -> {
            effectCheckerC2SPacket packet = new effectCheckerC2SPacket(buf);
            server.execute(() -> packet.handle(server, player));
        });
    }

    public static void registerClient() {
        // Register S2C packets on the client
        ClientPlayNetworking.registerGlobalReceiver(EFFECT_CHECKER_S2C, (client, handler, buf, responseSender) -> {
            effectCheckerS2CPacket packet = new effectCheckerS2CPacket(buf);
            client.execute(() -> packet.handle(client));
        });

        ClientPlayNetworking.registerGlobalReceiver(HUD_S2C, (client, handler, buf, responseSender) -> {
            hudS2CPacket packet = new hudS2CPacket(buf);
            client.execute(() -> packet.handle(client));
        });
    }

    public static void sendToServer(effectCheckerC2SPacket packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.toBytes(buf);
        ClientPlayNetworking.send(EFFECT_CHECKER_C2S, buf);
    }

    public static void sendToPlayer(effectCheckerS2CPacket packet, ServerPlayer player) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.toBytes(buf);
        ServerPlayNetworking.send(player, EFFECT_CHECKER_S2C, buf);
    }

    public static void sendToPlayer(hudS2CPacket packet, ServerPlayer player) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.toBytes(buf);
        ServerPlayNetworking.send(player, HUD_S2C, buf);
    }
}
