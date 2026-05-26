package net.mervyn.testosterone.packages;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface TestosteroneItemHandler {
    int getSlots();

    @NotNull ItemStack getStackInSlot(int slot);

    CompoundTag serializeNBT();
}
