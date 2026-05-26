package net.mervyn.testosterone.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.mervyn.testosterone.testosterone;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class mustacheModel extends Model {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(testosterone.MOD_ID, "mustachemodel"), "main");
	private final ModelPart bb_main;

	public mustacheModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 38).mirror().addBox(0.5F, -27.0F, -4.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 38).addBox(-2.5F, -27.0F, -4.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).mirror().addBox(1.5F, -26.0F, -4.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 40).addBox(-3.5F, -26.0F, -4.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 52).addBox(-4.0F, -36.0F, -4.0F, 8.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 38).addBox(-5.0F, -39.0F, -5.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
