package net.mervyn.testosterone.blocks;

import com.simibubi.create.content.decoration.copycat.CopycatBlock;
import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.effects.roidRageEffect;
import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class fragileCopycatBlock extends CopycatBlock {

    public fragileCopycatBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canConnectTexturesToward(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, BlockPos blockPos1, BlockState blockState) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity instanceof Player player && player.hasEffect(testosteroneModEffects.ROID_RAGE_EFFECT.get()) && roidRageEffect.getSpeed(player) > ConfigRegistry.ABILITY_SPEED.get()) {
                if (pLevel instanceof Level level && !level.isClientSide()) {
                    level.destroyBlock(pPos, true, player);
                }

                return Shapes.empty();
            }
        }

        return Shapes.block();
    }

    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        BlockState material = getMaterial(level, pos);

        if (isCreateCopycatBase(neighborState)) {
            return false;
        }

        return material == neighborState;
    }

    @Override
    public boolean canFaceBeOccluded(BlockState state, Direction face) {
        return true;
    }

    private static boolean isCreateCopycatBase(BlockState state) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        return id != null && "create".equals(id.getNamespace()) && "copycat_base".equals(id.getPath());
    }
}
