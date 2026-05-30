package net.mervyn.testosterone.blocks;

import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.sounds.testosteroneModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;

@SuppressWarnings("deprecation")
public class johnRock extends Block {
    public static final BooleanProperty TOGGLED = BooleanProperty.create("toggled");
    public static final BooleanProperty PRESSED = BooleanProperty.create("pressed");

    private static final class ClusterTickState {
        long tick = -1;
        final Set<BlockPos> positions = new HashSet<>();
    }

    private static final Map<Level, ClusterTickState> CLUSTER_POSITIONS =
            Collections.synchronizedMap(new WeakHashMap<>());

    private static Set<BlockPos> clusterPositionsThisTick(Level level) {
        ClusterTickState state = CLUSTER_POSITIONS.computeIfAbsent(level, ignored -> new ClusterTickState());
        long currentTick = level.getGameTime();
        if (state.tick != currentTick) {
            state.positions.clear();
            state.tick = currentTick;
        }
        return state.positions;
    }

    public johnRock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TOGGLED, false)
                .setValue(PRESSED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOGGLED, PRESSED);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide()) {
            Set<BlockPos> updatedThisTick = clusterPositionsThisTick(level);
            if (updatedThisTick.contains(pos)) {
                return;
            }
            boolean isPowered = level.hasNeighborSignal(pos);
            if (isPowered && !state.getValue(PRESSED)) {
                boolean newToggled = !state.getValue(TOGGLED);
                BlockState newState = state.setValue(PRESSED, true).setValue(TOGGLED, newToggled);
                level.setBlockAndUpdate(pos, newState);
                updatedThisTick.add(pos);
                propagateConnectedToggling(level, pos, newToggled, updatedThisTick);
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide()) {
            Set<BlockPos> updatedThisTick = clusterPositionsThisTick(level);
            if (updatedThisTick.contains(pos)) {
                super.neighborChanged(state, level, pos, neighborBlock, fromPos, isMoving);
                return;
            }
            boolean isPowered = level.hasNeighborSignal(pos);
            boolean wasPressed = state.getValue(PRESSED);
            if (isPowered && !wasPressed) {
                boolean newToggled = !state.getValue(TOGGLED);
                BlockState newState = state.setValue(PRESSED, true).setValue(TOGGLED, newToggled);
                level.setBlockAndUpdate(pos, newState);
                updatedThisTick.add(pos);
                propagateConnectedToggling(level, pos, newToggled, updatedThisTick);
            } else if (!isPowered && wasPressed) {
                level.setBlockAndUpdate(pos, state.setValue(PRESSED, false));
            }
        }
        super.neighborChanged(state, level, pos, neighborBlock, fromPos, isMoving);
    }

    private void propagateConnectedToggling(Level level, BlockPos origin, boolean newToggled, Set<BlockPos> updatedThisTick) {
        level.playSound(
                null,
                origin.getX(),
                origin.getY(),
                origin.getZ(),
                newToggled ? testosteroneModSounds.JOHN_ROCK_DEACTIVATION.get() : testosteroneModSounds.JOHN_ROCK_ACTIVATION.get(),
                SoundSource.BLOCKS,
                1.0F,
                1.0F
        );

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();

        int limit = ConfigRegistry.JOHN_ROCK_LIMIT.get();
        int toggledCount = 0;

        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = origin.relative(direction);
            BlockState adjacentState = level.getBlockState(adjacentPos);
            if (adjacentState.getBlock() instanceof johnRock) {
                if (visited.add(adjacentPos)) {
                    queue.add(adjacentPos);
                }
            }
        }

        while (!queue.isEmpty() && toggledCount < limit) {
            BlockPos currentPos = queue.poll();
            updatedThisTick.add(currentPos);

            BlockState currentState = level.getBlockState(currentPos);
            if (currentState.getValue(johnRock.TOGGLED) != newToggled) {
                level.setBlockAndUpdate(currentPos, currentState.setValue(johnRock.TOGGLED, newToggled));
            }
            toggledCount++;

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = currentPos.relative(direction);
                if (!visited.contains(neighborPos)) {
                    BlockState neighborState = level.getBlockState(neighborPos);
                    if (neighborState.getBlock() instanceof johnRock) {
                        visited.add(neighborPos);
                        queue.add(neighborPos);
                    }
                }
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null && state.getValue(TOGGLED)) {
                return Shapes.empty();
            }
        }

        return Shapes.block();
    }


    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.getValue(TOGGLED)) {
            return Shapes.empty();
        }

        return super.getOcclusionShape(pState, pLevel, pPos);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        if (adjacentBlockState.getBlock() == this) {
            return state.getValue(TOGGLED);
        }
        return super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.getValue(TOGGLED) ? 0 : super.getLightBlock(state, worldIn, pos);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getValue(TOGGLED);
    }

}
