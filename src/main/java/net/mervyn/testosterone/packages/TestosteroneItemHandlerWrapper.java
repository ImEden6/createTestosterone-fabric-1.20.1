package net.mervyn.testosterone.packages;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestosteroneItemHandlerWrapper implements TestosteroneItemHandler {

    private final ItemStackHandler handler;

    public TestosteroneItemHandlerWrapper(ItemStackHandler handler) {
        this.handler = handler;
    }

    @Override
    public int getSlots() {
        return handler.getSlots().size();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public CompoundTag serializeNBT() {
        return handler.serializeNBT();
    }
}
