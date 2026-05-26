package net.mervyn.testosterone.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class testosteronePonder implements PonderPlugin {

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        ResourceLocation dreamBlockId = new ResourceLocation("estrogen", "dream_block");
        Block dormantDreamBlock = BuiltInRegistries.BLOCK.get(dreamBlockId);
        ResourceLocation johnRockId = BuiltInRegistries.BLOCK.getKey(testosteroneModBlocks.JOHN_ROCK.get());
        ResourceLocation decanterId = BuiltInRegistries.BLOCK.getKey(testosteroneModBlocks.DECANTER_CENTRIFUGE.get());

        String schematic = (dormantDreamBlock != null && dormantDreamBlock != Blocks.AIR) ? "john_bell_estrogen" : "john_bell";

        helper.forComponents(johnRockId)
                .addStoryBoard(schematic, johnScene::john_bell);
        helper.forComponents(johnRockId)
                .addStoryBoard("john_active_inactive", johnScene::john_active_inactive);
        helper.forComponents(decanterId)
                .addStoryBoard("decanter_main", decanterScene::decanter_main);
    }

    @Override
    public @NotNull String getModId() {
        return testosterone.MOD_ID;
    }
}
