package net.mervyn.testosterone.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

public class BlockEntityEntry<T extends net.minecraft.world.level.block.entity.BlockEntity> implements Supplier<BlockEntityType<T>> {
    private final BlockEntityType<T> type;

    public BlockEntityEntry(BlockEntityType<T> type) {
        this.type = type;
    }

    public static <T extends net.minecraft.world.level.block.entity.BlockEntity> BlockEntityEntry<T> of(BlockEntityType<T> type) {
        return new BlockEntityEntry<>(type);
    }

    @Override
    public BlockEntityType<T> get() {
        return this.type;
    }
}


