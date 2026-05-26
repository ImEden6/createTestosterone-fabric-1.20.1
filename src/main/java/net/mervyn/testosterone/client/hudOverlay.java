package net.mervyn.testosterone.client;

import net.mervyn.testosterone.util.EntityDataUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.testosterone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class hudOverlay implements HudRenderCallback {
    public static final String END_OF_COOLDOWN_TICK_KEY = "testosterone:end_of_cooldown_tick_key";
    public static final String ACTUAL_BEGIN_TICK_KEY = "testosterone:actual_begin_tick_key";
    public static final String BEGIN_TICK_KEY = "testosterone:begin_tick_key";
    public static final String DURATION_KEY = "testosterone:duration_key";

    public static final float ALPHA_MULTIPLIER = 0.5f;
    public static final float ALPHA_BASE = 0.3f;

    private static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(testosterone.MOD_ID,
            "textures/overlay/overlay.png");

    @Override
    public void onHudRender(GuiGraphics guiGraphics, float partialTick) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        long endOfCooldownTick = EntityDataUtil.get(player).getLong(END_OF_COOLDOWN_TICK_KEY);
        long actualBeginTick = EntityDataUtil.get(player).getLong(ACTUAL_BEGIN_TICK_KEY);
        long beginTick = EntityDataUtil.get(player).getLong(BEGIN_TICK_KEY);
        long duration = EntityDataUtil.get(player).getLong(DURATION_KEY);

        long currentTick = Minecraft.getInstance().level.getGameTime();

        if (player.isDeadOrDying()) {
            EntityDataUtil.get(player).putLong(END_OF_COOLDOWN_TICK_KEY, 0);
            EntityDataUtil.get(player).putLong(ACTUAL_BEGIN_TICK_KEY, 0);
            EntityDataUtil.get(player).putLong(BEGIN_TICK_KEY, 0);
            EntityDataUtil.get(player).putLong(DURATION_KEY, 0);
        }

        int x = 0;
        int y = 0;
        int screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        long ticksLeft = endOfCooldownTick - currentTick;
        float value = (beginTick > 0 ? (ticksLeft / (float) beginTick) : 0f) * ALPHA_MULTIPLIER + ALPHA_BASE;

        if (currentTick < actualBeginTick + duration && player.hasEffect(testosteroneModEffects.TESTOSTERONE_EFFECT.get())) {
            if (ConfigRegistry.RENDER_TESTOSTERONE_INVINCIBLE.get()) {
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor((float) ConfigRegistry.TESTOSTERONE_R_INVINCIBLE.get() / 255, (float) ConfigRegistry.TESTOSTERONE_G_INVINCIBLE.get() / 255, (float) ConfigRegistry.TESTOSTERONE_B_INVINCIBLE.get() / 255, ALPHA_MULTIPLIER + ALPHA_BASE);
                guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
        } else if (ticksLeft > 0 && ConfigRegistry.RENDER_TESTOSTERONE_COOLDOWN.get()) {
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor((float) ConfigRegistry.TESTOSTERONE_R_COOLDOWN.get() / 255, (float) ConfigRegistry.TESTOSTERONE_G_COOLDOWN.get() / 255, (float) ConfigRegistry.TESTOSTERONE_B_COOLDOWN.get() / 255, value);
            guiGraphics.blit(OVERLAY_TEXTURE, x, y, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
