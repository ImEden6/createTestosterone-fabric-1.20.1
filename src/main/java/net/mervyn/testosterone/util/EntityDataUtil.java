package net.mervyn.testosterone.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public final class EntityDataUtil {
    private static final Map<Entity, CompoundTag> ENTITY_DATA =
            Collections.synchronizedMap(new WeakHashMap<>());

    private EntityDataUtil() {
    }

    public static CompoundTag get(Entity entity) {
        return ENTITY_DATA.computeIfAbsent(entity, ignored -> new CompoundTag());
    }
}
