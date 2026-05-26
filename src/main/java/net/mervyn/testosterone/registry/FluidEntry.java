package net.mervyn.testosterone.registry;

import net.minecraft.world.level.material.FlowingFluid;
import java.util.function.Supplier;

public class FluidEntry<T extends FlowingFluid> implements Supplier<T> {
    private final T flowing;
    private final FlowingFluid still;
    private RegistryObject<net.minecraft.world.item.Item> bucket;
    private RegistryObject<net.minecraft.world.level.block.Block> block;

    public FluidEntry(T flowing, FlowingFluid still) {
        this.flowing = flowing;
        this.still = still;
    }

    public static <T extends FlowingFluid> FluidEntry<T> of(T flowing, FlowingFluid still) {
        return new FluidEntry<>(flowing, still);
    }

    public FluidEntry<T> setBucket(RegistryObject<net.minecraft.world.item.Item> bucket) {
        this.bucket = bucket;
        return this;
    }

    public FluidEntry<T> setBlock(RegistryObject<net.minecraft.world.level.block.Block> block) {
        this.block = block;
        return this;
    }

    public RegistryObject<net.minecraft.world.item.Item> getBucket() {
        return this.bucket;
    }

    public RegistryObject<net.minecraft.world.level.block.Block> getBlock() {
        return this.block;
    }

    @Override
    public T get() {
        return this.flowing;
    }

    public FlowingFluid getSource() {
        return this.still;
    }
}


