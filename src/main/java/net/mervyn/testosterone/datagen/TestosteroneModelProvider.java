package net.mervyn.testosterone.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.mervyn.testosterone.fluids.testosteroneFluids;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class TestosteroneModelProvider extends FabricModelProvider {
    public TestosteroneModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(testosteroneModBlocks.AEQUALIS.get());

        var cutAequalisPool = blockStateModelGenerator.family(testosteroneModBlocks.CUT_AEQUALIS.get());
        cutAequalisPool.stairs(testosteroneModBlocks.CUT_AEQUALIS_STAIRS.get());
        cutAequalisPool.slab(testosteroneModBlocks.CUT_AEQUALIS_SLAB.get());
        cutAequalisPool.wall(testosteroneModBlocks.CUT_AEQUALIS_WALL.get());

        var polishedCutAequalisPool = blockStateModelGenerator.family(testosteroneModBlocks.POLISHED_CUT_AEQUALIS.get());
        polishedCutAequalisPool.stairs(testosteroneModBlocks.POLISHED_CUT_AEQUALIS_STAIRS.get());
        polishedCutAequalisPool.slab(testosteroneModBlocks.POLISHED_CUT_AEQUALIS_SLAB.get());
        polishedCutAequalisPool.wall(testosteroneModBlocks.POLISHED_CUT_AEQUALIS_WALL.get());

        var cutAequalisBricksPool = blockStateModelGenerator.family(testosteroneModBlocks.CUT_AEQUALIS_BRICKS.get());
        cutAequalisBricksPool.stairs(testosteroneModBlocks.CUT_AEQUALIS_BRICK_STAIRS.get());
        cutAequalisBricksPool.slab(testosteroneModBlocks.CUT_AEQUALIS_BRICK_SLAB.get());
        cutAequalisBricksPool.wall(testosteroneModBlocks.CUT_AEQUALIS_BRICK_WALL.get());

        var smallAequalisBricksPool = blockStateModelGenerator.family(testosteroneModBlocks.SMALL_AEQUALIS_BRICKS.get());
        smallAequalisBricksPool.stairs(testosteroneModBlocks.SMALL_AEQUALIS_BRICK_STAIRS.get());
        smallAequalisBricksPool.slab(testosteroneModBlocks.SMALL_AEQUALIS_BRICK_SLAB.get());
        smallAequalisBricksPool.wall(testosteroneModBlocks.SMALL_AEQUALIS_BRICK_WALL.get());

        blockStateModelGenerator.createTrivialCube(testosteroneModBlocks.SMOOTH_AEQUALIS.get());
        blockStateModelGenerator.createTrivialCube(testosteroneModBlocks.SMOOTH_DIAMOND_AEQUALIS.get());
        blockStateModelGenerator.createTrivialCube(testosteroneModBlocks.SMOOTH_DARK_DIAMOND_AEQUALIS.get());
        blockStateModelGenerator.createTrivialCube(testosteroneModBlocks.CHEESE_BLOCK.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        registerGenerated(itemModelGenerator,
                testosteroneModItems.INCOMPLETE_ANDESITE_ALLOY.get(),
                testosteroneModItems.TESTOSTERONE_PILL.get(),
                testosteroneModItems.TESTOSTERONE_PROTEIN_BAR.get(),
                testosteroneModItems.TESTOSTERONE_SHOT.get(),
                testosteroneModItems.TRENBOLONE_SHOT.get(),
                testosteroneModItems.BETTER_TRENBOLONE_SHOT.get(),
                testosteroneModItems.BEER_MUG.get(),
                testosteroneModItems.TIE.get(),
                testosteroneModItems.AFTERLIFE_TOTEM.get(),
                testosteroneModItems.CHEESE_CURDS.get(),
                testosteroneModItems.WHEY_PROTEIN.get(),
                testosteroneModItems.RAT_FUR.get(),
                testosteroneFluids.CHOLESTEROL_FLUID.getBucket().get(),
                testosteroneFluids.DILUTED_ZINC_FLUID.getBucket().get(),
                testosteroneFluids.TESTOSTERONE_FLUID.getBucket().get(),
                testosteroneFluids.ESTRONE_FLUID.getBucket().get(),
                testosteroneFluids.TRENBOLONE_FLUID.getBucket().get(),
                testosteroneFluids.BEER_FLUID.getBucket().get(),
                testosteroneFluids.WHEY_FLUID.getBucket().get(),
                testosteroneFluids.CHEESE_FLUID.getBucket().get());

        itemModelGenerator.generateFlatItem(testosteroneModItems.CHEESE_ON_A_STICK.get(), ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
    }

    private static void registerGenerated(ItemModelGenerators itemModelGenerator, Item... items) {
        for (Item item : items) {
            itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }
    }
}
