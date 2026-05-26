package net.mervyn.testosterone.fluids;

import net.mervyn.testosterone.registry.FluidEntry;
import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class testosteroneFluids {

    private static <STILL extends FlowingFluid, FLOWING extends FlowingFluid> FluidEntry<FLOWING> registerFluid(
            String name, STILL still, FLOWING flowing, Block block, Item bucket) {
        
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(testosterone.MOD_ID, name), still);
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(testosterone.MOD_ID, name + "_flowing"), flowing);
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(testosterone.MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(testosterone.MOD_ID, name + "_bucket"), bucket);
        
        FluidEntry<FLOWING> entry = new FluidEntry<>(flowing, still);
        entry.setBucket(RegistryObject.of(bucket));
        entry.setBlock(RegistryObject.of(block));
        return entry;
    }

    // 1. Cholesterol
    public static final FlowingFluid CHOLESTEROL_STILL = new CholesterolStill();
    public static final FlowingFluid CHOLESTEROL_FLOWING = new CholesterolFlowing();
    public static final Block CHOLESTEROL_BLOCK = new LiquidBlock(CHOLESTEROL_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item CHOLESTEROL_BUCKET = new BucketItem(CHOLESTEROL_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    public static final FluidEntry<FlowingFluid> CHOLESTEROL_FLUID = registerFluid("cholesterol_fluid", CHOLESTEROL_STILL, CHOLESTEROL_FLOWING, CHOLESTEROL_BLOCK, CHOLESTEROL_BUCKET);

    public static class CholesterolStill extends AbstractFluid {
        public CholesterolStill() { super(3, 2, 20); }
        @Override public Fluid getFlowing() { return CHOLESTEROL_FLOWING; }
        @Override public Fluid getSource() { return CHOLESTEROL_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return CHOLESTEROL_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return CHOLESTEROL_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class CholesterolFlowing extends AbstractFluid {
        public CholesterolFlowing() { super(3, 2, 20); }
        @Override public Fluid getFlowing() { return CHOLESTEROL_FLOWING; }
        @Override public Fluid getSource() { return CHOLESTEROL_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return CHOLESTEROL_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return CHOLESTEROL_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 2. Diluted Zinc
    public static final FlowingFluid DILUTED_ZINC_STILL = new DilutedZincStill();
    public static final FlowingFluid DILUTED_ZINC_FLOWING = new DilutedZincFlowing();
    public static final Block DILUTED_ZINC_BLOCK = new LiquidBlock(DILUTED_ZINC_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item DILUTED_ZINC_BUCKET = new BucketItem(DILUTED_ZINC_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    public static final FluidEntry<FlowingFluid> DILUTED_ZINC_FLUID = registerFluid("diluted_zinc_fluid", DILUTED_ZINC_STILL, DILUTED_ZINC_FLOWING, DILUTED_ZINC_BLOCK, DILUTED_ZINC_BUCKET);

    public static class DilutedZincStill extends AbstractFluid {
        public DilutedZincStill() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return DILUTED_ZINC_FLOWING; }
        @Override public Fluid getSource() { return DILUTED_ZINC_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return DILUTED_ZINC_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return DILUTED_ZINC_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class DilutedZincFlowing extends AbstractFluid {
        public DilutedZincFlowing() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return DILUTED_ZINC_FLOWING; }
        @Override public Fluid getSource() { return DILUTED_ZINC_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return DILUTED_ZINC_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return DILUTED_ZINC_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 3. Testosterone
    public static final FlowingFluid TESTOSTERONE_STILL = new TestosteroneStill();
    public static final FlowingFluid TESTOSTERONE_FLOWING = new TestosteroneFlowing();
    public static final Block TESTOSTERONE_BLOCK = new LiquidBlock(TESTOSTERONE_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item TESTOSTERONE_BUCKET = new BucketItem(TESTOSTERONE_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.RARE));
    public static final FluidEntry<FlowingFluid> TESTOSTERONE_FLUID = registerFluid("testosterone_fluid", TESTOSTERONE_STILL, TESTOSTERONE_FLOWING, TESTOSTERONE_BLOCK, TESTOSTERONE_BUCKET);

    public static class TestosteroneStill extends AbstractFluid {
        public TestosteroneStill() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return TESTOSTERONE_FLOWING; }
        @Override public Fluid getSource() { return TESTOSTERONE_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return TESTOSTERONE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return TESTOSTERONE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class TestosteroneFlowing extends AbstractFluid {
        public TestosteroneFlowing() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return TESTOSTERONE_FLOWING; }
        @Override public Fluid getSource() { return TESTOSTERONE_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return TESTOSTERONE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return TESTOSTERONE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 4. Estrone
    public static final FlowingFluid ESTRONE_STILL = new EstroneStill();
    public static final FlowingFluid ESTRONE_FLOWING = new EstroneFlowing();
    public static final Block ESTRONE_BLOCK = new LiquidBlock(ESTRONE_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item ESTRONE_BUCKET = new BucketItem(ESTRONE_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.RARE));
    public static final FluidEntry<FlowingFluid> ESTRONE_FLUID = registerFluid("estrone_fluid", ESTRONE_STILL, ESTRONE_FLOWING, ESTRONE_BLOCK, ESTRONE_BUCKET);

    public static class EstroneStill extends AbstractFluid {
        public EstroneStill() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return ESTRONE_FLOWING; }
        @Override public Fluid getSource() { return ESTRONE_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return ESTRONE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return ESTRONE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class EstroneFlowing extends AbstractFluid {
        public EstroneFlowing() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return ESTRONE_FLOWING; }
        @Override public Fluid getSource() { return ESTRONE_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return ESTRONE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return ESTRONE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 5. Trenbolone
    public static final FlowingFluid TRENBOLONE_STILL = new TrenboloneStill();
    public static final FlowingFluid TRENBOLONE_FLOWING = new TrenboloneFlowing();
    public static final Block TRENBOLONE_BLOCK = new LiquidBlock(TRENBOLONE_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item TRENBOLONE_BUCKET = new BucketItem(TRENBOLONE_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.RARE));
    public static final FluidEntry<FlowingFluid> TRENBOLONE_FLUID = registerFluid("trenbolone_fluid", TRENBOLONE_STILL, TRENBOLONE_FLOWING, TRENBOLONE_BLOCK, TRENBOLONE_BUCKET);

    public static class TrenboloneStill extends AbstractFluid {
        public TrenboloneStill() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return TRENBOLONE_FLOWING; }
        @Override public Fluid getSource() { return TRENBOLONE_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return TRENBOLONE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return TRENBOLONE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class TrenboloneFlowing extends AbstractFluid {
        public TrenboloneFlowing() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return TRENBOLONE_FLOWING; }
        @Override public Fluid getSource() { return TRENBOLONE_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return TRENBOLONE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return TRENBOLONE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 6. Beer
    public static final FlowingFluid BEER_STILL = new BeerStill();
    public static final FlowingFluid BEER_FLOWING = new BeerFlowing();
    public static final Block BEER_BLOCK = new LiquidBlock(BEER_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item BEER_BUCKET = new BucketItem(BEER_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    public static final FluidEntry<FlowingFluid> BEER_FLUID = registerFluid("beer_fluid", BEER_STILL, BEER_FLOWING, BEER_BLOCK, BEER_BUCKET);

    public static class BeerStill extends AbstractFluid {
        public BeerStill() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return BEER_FLOWING; }
        @Override public Fluid getSource() { return BEER_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return BEER_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return BEER_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class BeerFlowing extends AbstractFluid {
        public BeerFlowing() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return BEER_FLOWING; }
        @Override public Fluid getSource() { return BEER_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return BEER_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return BEER_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 7. Whey
    public static final FlowingFluid WHEY_STILL = new WheyStill();
    public static final FlowingFluid WHEY_FLOWING = new WheyFlowing();
    public static final Block WHEY_BLOCK = new LiquidBlock(WHEY_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item WHEY_BUCKET = new BucketItem(WHEY_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    public static final FluidEntry<FlowingFluid> WHEY_FLUID = registerFluid("whey_fluid", WHEY_STILL, WHEY_FLOWING, WHEY_BLOCK, WHEY_BUCKET);

    public static class WheyStill extends AbstractFluid {
        public WheyStill() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return WHEY_FLOWING; }
        @Override public Fluid getSource() { return WHEY_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return WHEY_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return WHEY_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class WheyFlowing extends AbstractFluid {
        public WheyFlowing() { super(3, 1, 5); }
        @Override public Fluid getFlowing() { return WHEY_FLOWING; }
        @Override public Fluid getSource() { return WHEY_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return WHEY_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return WHEY_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    // 8. Cheese
    public static final FlowingFluid CHEESE_STILL = new CheeseStill();
    public static final FlowingFluid CHEESE_FLOWING = new CheeseFlowing();
    public static final Block CHEESE_BLOCK = new LiquidBlock(CHEESE_STILL, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable());
    public static final Item CHEESE_BUCKET = new BucketItem(CHEESE_STILL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));
    public static final FluidEntry<FlowingFluid> CHEESE_FLUID = registerFluid("cheese_fluid", CHEESE_STILL, CHEESE_FLOWING, CHEESE_BLOCK, CHEESE_BUCKET);

    public static class CheeseStill extends AbstractFluid {
        public CheeseStill() { super(3, 2, 20); }
        @Override public Fluid getFlowing() { return CHEESE_FLOWING; }
        @Override public Fluid getSource() { return CHEESE_STILL; }
        @Override public boolean isSource(FluidState state) { return true; }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public Item getBucket() { return CHEESE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return CHEESE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }
    public static class CheeseFlowing extends AbstractFluid {
        public CheeseFlowing() { super(3, 2, 20); }
        @Override public Fluid getFlowing() { return CHEESE_FLOWING; }
        @Override public Fluid getSource() { return CHEESE_STILL; }
        @Override public boolean isSource(FluidState state) { return false; }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public Item getBucket() { return CHEESE_BUCKET; }
        @Override protected BlockState createLegacyBlock(FluidState state) { return CHEESE_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)); }
    }

    public static void register() {
        // Handled via static fields during class loading
    }

    public static void registerFluidInteractions() {
        // Left as placeholder for compatibility; interactions are handled dynamically.
    }
}
