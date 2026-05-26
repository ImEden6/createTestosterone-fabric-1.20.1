package net.mervyn.testosterone;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mervyn.testosterone.advancements.testosteroneAdvancementUtils;
import net.mervyn.testosterone.blocks.testosteroneBlockEntities;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.mervyn.testosterone.config.ConfigRegistry;
import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.entities.testosteroneEntitySpawns;
import net.mervyn.testosterone.entities.testosteroneEntities;
import net.mervyn.testosterone.entities.rat.ratEntity;
import net.mervyn.testosterone.fluids.testosteroneFluids;
import net.mervyn.testosterone.items.testosteroneModCreativeModTabs;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.mervyn.testosterone.loot.testosteroneLootTableModifiers;
import net.mervyn.testosterone.network.testosteroneModMessages;
import net.mervyn.testosterone.particles.testosteroneModParticles;
import net.mervyn.testosterone.potions.testosteroneModPotions;
import net.mervyn.testosterone.recipes.testosteroneModRecipes;
import net.mervyn.testosterone.sounds.testosteroneModSounds;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class testosterone implements ModInitializer {
    public static final String MOD_ID = "testosterone";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        // Register Config
        ConfigRegistry.register();

        // Register registries
        testosteroneModEffects.registerEffects();
        testosteroneEntities.register();
        testosteroneModItems.register();
        testosteroneModCreativeModTabs.register();
        testosteroneModBlocks.register();
        testosteroneBlockEntities.register();
        testosteroneFluids.register();
        testosteroneModPotions.register();
        testosteroneModSounds.register();
        testosteroneModParticles.register();
        testosteroneModRecipes.register();
        testosteroneLootTableModifiers.register();

        // Entity attributes
        FabricDefaultAttributeRegistry.register(testosteroneEntities.RAT.get(), ratEntity.createAttributes());
        testosteroneEntitySpawns.register();

        // Networking
        testosteroneModMessages.register();

        // Advancement criteria
        testosteroneAdvancementUtils.register();

        LOGGER.info("Create: Testosterone initialized successfully!");
    }
}
