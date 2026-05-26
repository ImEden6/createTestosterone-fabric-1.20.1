package net.mervyn.testosterone.items.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.mervyn.testosterone.testosterone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class curioTieRenderer implements TrinketRenderer {
    public static final ResourceLocation tieTexture = new ResourceLocation(testosterone.MOD_ID, "textures/models/tie_texture.png");
    private tieModel tieModelInstance;

    private tieModel getTieModel() {
        if (tieModelInstance == null) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.getEntityModels() == null) {
                return null;
            }
            tieModelInstance = new tieModel(minecraft.getEntityModels().bakeLayer(tieModel.LAYER_LOCATION));
        }
        return tieModelInstance;
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (contextModel instanceof PlayerModel<?> playerModel) {
            tieModel model = getTieModel();
            if (model == null) {
                return;
            }
            VertexConsumer vBuff = vertexConsumers.getBuffer(RenderType.entityTranslucent(tieTexture));
            matrices.pushPose();

            playerModel.body.translateAndRotate(matrices);

            long currentTick = 0;
            if (Minecraft.getInstance().level != null) {
                currentTick = Minecraft.getInstance().level.getGameTime();
            }

            short colorId = (short) ((currentTick / 12) % 16);
            float[] color = DyeColor.byId(colorId).getTextureDiffuseColors();

            if (stack.getTag() != null) {
                String nbtColor = stack.getTag().getString("color");
                for (int pId = 0; pId < 16; pId++) {
                    if (DyeColor.byId(pId).name().toLowerCase().equals(nbtColor)) {
                        color = DyeColor.byId(pId).getTextureDiffuseColors();
                        break;
                    }
                }
            }

            model.renderToBuffer(matrices, vBuff, light, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1f);
            matrices.popPose();
        }
    }
}
