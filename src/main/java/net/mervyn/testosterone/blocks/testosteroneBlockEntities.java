package net.mervyn.testosterone.blocks;

import net.mervyn.testosterone.blocks.decanterCentrifuge.decanterCentrifugeBlockEntity;
import net.mervyn.testosterone.registry.BlockEntityEntry;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class testosteroneBlockEntities {
    public static BlockEntityEntry<decanterCentrifugeBlockEntity> DECANTER_CENTRIFUGE;

    public static void register() {
        DECANTER_CENTRIFUGE = BlockEntityEntry.of(
            Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(testosterone.MOD_ID, "decanter_centrifuge"),
                BlockEntityType.Builder.of(decanterCentrifugeBlockEntity::new, testosteroneModBlocks.DECANTER_CENTRIFUGE.get()).build(null)
            )
        );
    }
}
