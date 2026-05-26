package net.mervyn.testosterone.registry;

import net.minecraft.world.item.Item;
import java.util.function.Supplier;

public class ItemEntry<T extends Item> implements Supplier<T> {
    private final T item;

    public ItemEntry(T item) {
        this.item = item;
    }

    public static <T extends Item> ItemEntry<T> of(T item) {
        return new ItemEntry<>(item);
    }

    @Override
    public T get() {
        return this.item;
    }
}


