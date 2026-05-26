package net.mervyn.testosterone.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class AbstractFluid extends FlowingFluid {
    private final int slopeFindDistance;
    private final int levelDecreasePerBlock;
    private final int tickDelay;

    protected AbstractFluid(int slopeFindDistance, int levelDecreasePerBlock, int tickDelay) {
        this.slopeFindDistance = slopeFindDistance;
        this.levelDecreasePerBlock = levelDecreasePerBlock;
        this.tickDelay = tickDelay;
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing();
    }

    @Override
    protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
        super.createFluidStateDefinition(builder);
        builder.add(LEVEL);
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        Block.dropResources(state, world, pos, world.getBlockEntity(pos));
    }

    @Override
    protected int getSlopeFindDistance(LevelReader world) {
        return slopeFindDistance;
    }

    @Override
    protected int getDropOff(LevelReader world) {
        return levelDecreasePerBlock;
    }

    @Override
    public int getTickDelay(LevelReader world) {
        return tickDelay;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockGetter world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN || !fluid.isSame(this);
    }
}
