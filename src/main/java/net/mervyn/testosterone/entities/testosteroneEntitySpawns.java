package net.mervyn.testosterone.entities;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.mervyn.testosterone.entities.rat.ratEntity;

public class testosteroneEntitySpawns {
    private static final int RAT_WEIGHT = 5;
    private static final int RAT_MIN_COUNT = 2;
    private static final int RAT_MAX_COUNT = 5;

    public static void register() {
        SpawnPlacements.register(
                testosteroneEntities.RAT.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ratEntity::checkAnimalSpawnRules
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_FOREST),
                MobCategory.CREATURE,
                testosteroneEntities.RAT.get(),
                RAT_WEIGHT,
                RAT_MIN_COUNT,
                RAT_MAX_COUNT
        );
    }
}
