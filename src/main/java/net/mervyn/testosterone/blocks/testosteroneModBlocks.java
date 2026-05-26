package net.mervyn.testosterone.blocks;

import com.simibubi.create.content.decoration.palettes.ConnectedPillarBlock;
import net.mervyn.testosterone.blocks.decanterCentrifuge.decanterCentrifugeBlock;
import net.mervyn.testosterone.items.testosteroneModFoods;
import net.mervyn.testosterone.registry.BlockEntry;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class testosteroneModBlocks {

    private static <T extends Block> BlockEntry<T> register(String name, T block) {
        return register(name, block, new Item.Properties());
    }

    private static <T extends Block> BlockEntry<T> register(String name, T block, Item.Properties properties) {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(testosterone.MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(testosterone.MOD_ID, name), new BlockItem(block, properties));
        return BlockEntry.of(block);
    }

    private static <T extends Block> BlockEntry<T> registerNoItem(String name, T block) {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(testosterone.MOD_ID, name), block);
        return BlockEntry.of(block);
    }

    private static SoundType resolvePillSound() {
        Block b = BuiltInRegistries.BLOCK.get(new ResourceLocation("estrogen", "estrogen_pill_block"));
        return b != null ? b.defaultBlockState().getSoundType() : SoundType.BONE_BLOCK;
    }

    public static final BlockEntry<Block> LAYERED_AEQUALIS =
            register("layered_aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<ConnectedPillarBlock> AEQUALIS_PILLAR =
            register("aequalis_pillar", new ConnectedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<testosteronePillBox> TESTOSTERONE_PILL_BLOCK =
            register("testosterone_pill_box", new testosteronePillBox(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(1f, 1f).sound(resolvePillSound())));

    public static final BlockEntry<Block> AEQUALIS =
            register("aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<Block> CUT_AEQUALIS =
            register("cut_aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<StairBlock> CUT_AEQUALIS_STAIRS =
            register("cut_aequalis_stairs", new StairBlock(Blocks.STONE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<SlabBlock> CUT_AEQUALIS_SLAB =
            register("cut_aequalis_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<WallBlock> CUT_AEQUALIS_WALL =
            register("cut_aequalis_wall", new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<Block> POLISHED_CUT_AEQUALIS =
            register("polished_cut_aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<StairBlock> POLISHED_CUT_AEQUALIS_STAIRS =
            register("polished_cut_aequalis_stairs", new StairBlock(Blocks.STONE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<SlabBlock> POLISHED_CUT_AEQUALIS_SLAB =
            register("polished_cut_aequalis_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<WallBlock> POLISHED_CUT_AEQUALIS_WALL =
            register("polished_cut_aequalis_wall", new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<Block> CUT_AEQUALIS_BRICKS =
            register("cut_aequalis_bricks", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<StairBlock> CUT_AEQUALIS_BRICK_STAIRS =
            register("cut_aequalis_brick_stairs", new StairBlock(Blocks.STONE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<SlabBlock> CUT_AEQUALIS_BRICK_SLAB =
            register("cut_aequalis_brick_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<WallBlock> CUT_AEQUALIS_BRICK_WALL =
            register("cut_aequalis_brick_wall", new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<Block> SMALL_AEQUALIS_BRICKS =
            register("small_aequalis_bricks", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<StairBlock> SMALL_AEQUALIS_BRICK_STAIRS =
            register("small_aequalis_brick_stairs", new StairBlock(Blocks.STONE_STAIRS.defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<SlabBlock> SMALL_AEQUALIS_BRICK_SLAB =
            register("small_aequalis_brick_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<WallBlock> SMALL_AEQUALIS_BRICK_WALL =
            register("small_aequalis_brick_wall", new WallBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<Block> SMOOTH_AEQUALIS =
            register("smooth_aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));

    public static final BlockEntry<Block> SMOOTH_DIAMOND_AEQUALIS =
            register("smooth_diamond_aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));

    public static final BlockEntry<Block> SMOOTH_DARK_DIAMOND_AEQUALIS =
            register("smooth_dark_diamond_aequalis", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));

    public static final BlockEntry<bigBricks> BIG_AEQUALIS_BRICKS =
            register("big_aequalis_bricks", new bigBricks(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<Block> CRACKED_PILLAR =
            register("cracked_pillar", new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.DEEPSLATE)));

    public static final BlockEntry<johnRock> JOHN_ROCK =
            register("john_rock", new johnRock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.STONE).ignitedByLava().lightLevel(s -> 12)), new Item.Properties().rarity(Rarity.EPIC));

    public static final BlockEntry<Block> CHEESE_BLOCK =
            register("cheese_block", new Block(BlockBehaviour.Properties.copy(Blocks.HONEYCOMB_BLOCK)), new Item.Properties().food(testosteroneModFoods.CHEESE_BLOCK));

    public static final BlockEntry<fragileCopycatBase> FRAGILE_COPYCAT_BASE =
            registerNoItem("fragile_copycat_base", new fragileCopycatBase(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final BlockEntry<fragileCopycatBlock> FRAGILE_COPYCAT_BLOCK =
            register("fragile_copycat_block", new fragileCopycatBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final BlockEntry<decanterCentrifugeBlock> DECANTER_CENTRIFUGE =
            register("decanter_centrifuge", new decanterCentrifugeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion()));

    public static final BlockEntry<trenboloneVial> TRENBOLONE_VIAL =
            register("trenbolone_vial", new trenboloneVial(BlockBehaviour.Properties.copy(Blocks.GLASS).sound(SoundType.GLASS).noOcclusion()));

    public static void register() {
        // Handled via static fields during class loading
    }
}
