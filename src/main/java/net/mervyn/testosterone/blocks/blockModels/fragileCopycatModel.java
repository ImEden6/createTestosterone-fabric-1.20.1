package net.mervyn.testosterone.blocks.blockModels;

import com.simibubi.create.content.decoration.copycat.CopycatModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.mervyn.testosterone.blocks.fragileCopycatBase;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class fragileCopycatModel extends CopycatModel {
    public fragileCopycatModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    protected void emitBlockQuadsInner(BlockAndTintGetter level, BlockState state, BlockPos pos,
                                       Supplier<RandomSource> randomSupplier, RenderContext context,
                                       BlockState material, CullFaceRemovalData cullFaceRemovalData,
                                       OcclusionData occlusionData) {
        RandomSource random = randomSupplier.get();
        BlockState renderedMaterial = material;

        if (isCreateCopycatBase(material)) {
            renderedMaterial = testosteroneModBlocks.FRAGILE_COPYCAT_BASE.get()
                    .defaultBlockState()
                    .setValue(fragileCopycatBase.STATE, random.nextInt(5));
        }

        BakedModel originalModel = getModelOf(renderedMaterial);
        originalModel.emitBlockQuads(level, renderedMaterial, pos, () -> random, context);
    }

    private static boolean isCreateCopycatBase(BlockState state) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return id != null && "create".equals(id.getNamespace()) && "copycat_base".equals(id.getPath());
    }
}
