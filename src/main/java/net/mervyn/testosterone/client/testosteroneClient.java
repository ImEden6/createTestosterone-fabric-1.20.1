package net.mervyn.testosterone.client;

import com.simibubi.create.AllPartialModels;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.mervyn.testosterone.blocks.testosteroneBlockEntities;
import net.mervyn.testosterone.blocks.decanterCentrifuge.decanterCentrifugeRenderer;
import net.mervyn.testosterone.entities.testosteroneEntities;
import net.mervyn.testosterone.entities.testosteroneModelLayers;
import net.mervyn.testosterone.entities.rat.ratModel;
import net.mervyn.testosterone.entities.rat.ratRenderer;
import net.mervyn.testosterone.fluids.testosteroneFluids;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.mervyn.testosterone.items.curios.curioTieRenderer;
import net.mervyn.testosterone.items.curios.tieModel;
import net.mervyn.testosterone.network.testosteroneModMessages;
import net.mervyn.testosterone.packages.TestosteronePackageStyles;
import net.mervyn.testosterone.particles.airPassingParticle;
import net.mervyn.testosterone.particles.runParticle;
import net.mervyn.testosterone.particles.testosteroneModParticles;
import net.mervyn.testosterone.ponder.testosteronePonder;
import net.mervyn.testosterone.testosterone;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

public class testosteroneClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register packets
        testosteroneModMessages.registerClient();

        // Render layers for fluids
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.CHOLESTEROL_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.CHOLESTEROL_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.DILUTED_ZINC_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.DILUTED_ZINC_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.TESTOSTERONE_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.TESTOSTERONE_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.ESTRONE_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.ESTRONE_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.TRENBOLONE_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.TRENBOLONE_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.BEER_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.BEER_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.WHEY_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.WHEY_FLUID.get(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.CHEESE_FLUID.getSource(), RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(testosteroneFluids.CHEESE_FLUID.get(), RenderType.translucent());

        // Model Layer Registry
        EntityModelLayerRegistry.registerModelLayer(testosteroneModelLayers.RAT_MODEL_LAYER, ratModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(beardModel.LAYER_LOCATION, beardModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(mustacheModel.LAYER_LOCATION, mustacheModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(tieModel.LAYER_LOCATION, tieModel::createBodyLayer);

        // Entity Renderers
        EntityRendererRegistry.register(testosteroneEntities.RAT.get(), ratRenderer::new);

        // Block Entity Renderers
        BlockEntityRenderers.register(testosteroneBlockEntities.DECANTER_CENTRIFUGE.get(), decanterCentrifugeRenderer::new);

        // Player render features (beard / mustache overlay)
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer playerRenderer) {
                registrationHelper.register(new Layer(playerRenderer));
            }
        });

        // Trinket Tie rendering
        TrinketRendererRegistry.registerRenderer(testosteroneModItems.TIE.get(), new curioTieRenderer());

        // Color provider
        ColorProviderRegistry.ITEM.register(new testosteroneItemColor(), testosteroneModItems.TIE.get());

        // Particle Factories
        ParticleFactoryRegistry.getInstance().register(testosteroneModParticles.TESTOSTERONE_RUN.get(), runParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(testosteroneModParticles.AIR_PASSING.get(), airPassingParticle.Factory::new);

        // Ponder scenes
        PonderIndex.addPlugin(new testosteronePonder());

        // HUD overlay
        HudRenderCallback.EVENT.register(new hudOverlay());

        // Packages custom styles
        TestosteronePackageStyles.TESTOSTERONE_PILL_STYLES.forEach(style -> {
            AllPartialModels.PACKAGES.put(
                    style.getItemId(),
                    PartialModel.of(new ResourceLocation(testosterone.MOD_ID, "item/" + style.getItemId().getPath()))
            );

            AllPartialModels.PACKAGE_RIGGING.put(
                    style.getItemId(),
                    PartialModel.of(style.getRiggingModel())
            );
        });

        TestosteronePackageStyles.TRENBOLONE_VIAL_STYLES.forEach(style -> {
            AllPartialModels.PACKAGES.put(
                    style.getItemId(),
                    PartialModel.of(new ResourceLocation(testosterone.MOD_ID, "item/" + style.getItemId().getPath()))
            );

            AllPartialModels.PACKAGE_RIGGING.put(
                    style.getItemId(),
                    PartialModel.of(style.getRiggingModel())
            );
        });
    }
}
