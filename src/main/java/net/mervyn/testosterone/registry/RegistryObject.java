package net.mervyn.testosterone.registry;

import java.util.function.Supplier;

public class RegistryObject<T> implements Supplier<T> {
    private final T value;

    public RegistryObject(T value) {
        this.value = value;
    }

    public static <T> RegistryObject<T> of(T value) {
        return new RegistryObject<>(value);
    }

    @Override
    public T get() {
        return this.value;
    }
}


