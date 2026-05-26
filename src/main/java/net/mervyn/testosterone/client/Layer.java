package net.mervyn.testosterone.client;

import net.mervyn.testosterone.util.EntityDataUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.network.packet.effectCheckerC2SPacket;
import net.mervyn.testosterone.network.testosteroneModMessages;
import net.mervyn.testosterone.testosterone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class Layer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public static final String EFFECT_CHECKER_KEY = "testosterone:effect_checker_key";

    public static final ResourceLocation BEARD_TEXTURE = new ResourceLocation(testosterone.MOD_ID, "textures/models/beard_texture.png");
    public static final ResourceLocation MUSTACHE_TEXTURE = new ResourceLocation(testosterone.MOD_ID, "textures/models/italianman_texture.png");

    private beardModel beardModelInstance;
    private mustacheModel mustacheModelInstance;

    public Layer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> pRenderer) {
        super(pRenderer);
    }

    private beardModel getBeardModel() {
        if (beardModelInstance == null) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getEntityModels() == null) {
                return null;
            }
            beardModelInstance = new beardModel(minecraft.getEntityModels().bakeLayer(beardModel.LAYER_LOCATION));
        }
        return beardModelInstance;
    }

    private mustacheModel getMustacheModel() {
        if (mustacheModelInstance == null) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getEntityModels() == null) {
                return null;
            }
            mustacheModelInstance = new mustacheModel(minecraft.getEntityModels().bakeLayer(mustacheModel.LAYER_LOCATION));
        }
        return mustacheModelInstance;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, AbstractClientPlayer pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        testosteroneModMessages.sendToServer(new effectCheckerC2SPacket(pLivingEntity.getId()));

        int hasEffectInt = EntityDataUtil.get(pLivingEntity).getInt(EFFECT_CHECKER_KEY);

        if (!ConfigRegistry.RENDER_BEARD.get()) {
            return;
        }

        pPoseStack.pushPose();
        pPoseStack.translate(0, 0, 0);

        if (hasEffectInt == 1) {
            beardModel beardModel = getBeardModel();
            if (beardModel == null) {
                pPoseStack.popPose();
                return;
            }
            VertexConsumer vBuff = pBuffer.getBuffer(RenderType.entityTranslucent(BEARD_TEXTURE));
            getParentModel().head.translateAndRotate(pPoseStack);
            beardModel.renderToBuffer(pPoseStack, vBuff, pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        } else if (hasEffectInt == 2) {
            mustacheModel mustacheModel = getMustacheModel();
            if (mustacheModel == null) {
                pPoseStack.popPose();
                return;
            }
            VertexConsumer vBuff = pBuffer.getBuffer(RenderType.entityTranslucent(MUSTACHE_TEXTURE));
            getParentModel().head.translateAndRotate(pPoseStack);
            mustacheModel.renderToBuffer(pPoseStack, vBuff, pPackedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        }
        pPoseStack.popPose();
    }
}
