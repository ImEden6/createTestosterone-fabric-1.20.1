package net.mervyn.testosterone.blocks.decanterCentrifuge;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.createmod.catnip.data.Iterate;
import net.mervyn.testosterone.blocks.testosteroneBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

@SuppressWarnings("NullableProblems")
public class decanterCentrifugeBlock extends HorizontalKineticBlock implements IBE<decanterCentrifugeBlockEntity> {

    public decanterCentrifugeBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldView, BlockPos blockPos) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }

    @Override
    public Class<decanterCentrifugeBlockEntity> getBlockEntityClass() {
        return decanterCentrifugeBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends decanterCentrifugeBlockEntity> getBlockEntityType() {
        return testosteroneBlockEntities.DECANTER_CENTRIFUGE.get();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.UP || face == Direction.DOWN;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCrouching()) {
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
        } else {
            if (getPreferredHorizontalFacing(context) != null) {
                return defaultBlockState().setValue(HORIZONTAL_FACING, getPreferredHorizontalFacing(context));
            } else {
                return super.getStateForPlacement(context);
            }
        }
    }

    @Override
    public Direction getPreferredHorizontalFacing(BlockPlaceContext context) {
        Direction preferredSide = null;
        for (Direction side : Iterate.horizontalDirections) {
            BlockPos targetPos = context.getClickedPos().relative(side);
            if (net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage.SIDED.find(context.getLevel(), targetPos, side.getOpposite()) != null) {
                preferredSide = side.getOpposite();
            }
        }
        return preferredSide;
    }
}
