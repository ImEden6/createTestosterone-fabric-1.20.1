package net.mervyn.testosterone.registry;

import net.minecraft.world.level.block.Block;
import java.util.function.Supplier;

public class BlockEntry<T extends Block> implements Supplier<T> {
    private final T block;

    public BlockEntry(T block) {
        this.block = block;
    }

    public static <T extends Block> BlockEntry<T> of(T block) {
        return new BlockEntry<>(block);
    }

    @Override
    public T get() {
        return this.block;
    }

    public T getDefaultState() {
        return this.block;
    }
}


