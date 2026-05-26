package net.mervyn.testosterone.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.mervyn.testosterone.fluids.testosteroneFluids;
import net.mervyn.testosterone.items.curios.tie;
import net.mervyn.testosterone.potions.testosteroneModPotions;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

public class testosteroneModCreativeModTabs {
    public static CreativeModeTab TESTOSTERONE_TAB;

    public static void register() {
        TESTOSTERONE_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(testosterone.MOD_ID, "testosterone_tab"),
            FabricItemGroup.builder()
                .icon(() -> new ItemStack(testosteroneModItems.TESTOSTERONE_PILL.get()))
                .title(Component.literal("Testosterone"))
                .displayItems((pParameters, pOutput) -> {
                    pOutput.accept(testosteroneModItems.TESTOSTERONE_PILL.get());
                    pOutput.accept(testosteroneModItems.TESTOSTERONE_SHOT.get());
                    pOutput.accept(testosteroneModItems.TESTOSTERONE_PROTEIN_BAR.get());
                    pOutput.accept(testosteroneModItems.BEER_MUG.get());
                    pOutput.accept(testosteroneModItems.TRENBOLONE_SHOT.get());
                    pOutput.accept(testosteroneModItems.BETTER_TRENBOLONE_SHOT.get());
                    pOutput.accept(testosteroneModBlocks.DECANTER_CENTRIFUGE.get());
                    pOutput.accept(testosteroneModBlocks.JOHN_ROCK.get());
                    pOutput.accept(testosteroneModBlocks.TESTOSTERONE_PILL_BLOCK.get());
                    pOutput.accept(testosteroneModBlocks.TRENBOLONE_VIAL.get());
                    pOutput.accept(testosteroneModBlocks.FRAGILE_COPYCAT_BLOCK.get());

                    pOutput.accept(testosteroneModBlocks.CHEESE_BLOCK.get());
                    pOutput.accept(addBooleanNbt(testosteroneModItems.CHEESE_ON_A_STICK.get().getDefaultInstance(), "Boost", false));
                    pOutput.accept(testosteroneModItems.WHEY_PROTEIN.get());
                    pOutput.accept(testosteroneModItems.CHEESE_CURDS.get());

                    pOutput.accept(testosteroneModItems.RAT_FUR.get());
                    pOutput.accept(testosteroneModItems.STUPID_RAT_SPAWN_EGG.get());

                    pOutput.accept(tippedArrow(testosteroneModPotions.TESTOSTERONE_POTION.get()));
                    pOutput.accept(tippedArrow(testosteroneModPotions.ROID_RAGE_POTION.get()));

                    pOutput.accept(testosteroneFluids.CHOLESTEROL_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.DILUTED_ZINC_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.TESTOSTERONE_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.TRENBOLONE_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.BEER_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.WHEY_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.CHEESE_FLUID.getBucket().get());
                    pOutput.accept(testosteroneFluids.ESTRONE_FLUID.getBucket().get());

                    pOutput.accept(testosteroneModItems.TIE.get());
                    pOutput.accept(tie.getTieByColor(DyeColor.byId(7).name().toLowerCase()));

                    for (int pId = 0; pId < 16; pId++) {
                        if (pId == 7) {
                            continue;
                        }
                        pOutput.accept(tie.getTieByColor(DyeColor.byId(pId).name().toLowerCase()));
                    }

                    pOutput.accept(testosteroneModBlocks.CRACKED_PILLAR.get());

                    pOutput.accept(testosteroneModBlocks.AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_STAIRS.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_SLAB.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_WALL.get());

                    pOutput.accept(testosteroneModBlocks.POLISHED_CUT_AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.POLISHED_CUT_AEQUALIS_STAIRS.get());
                    pOutput.accept(testosteroneModBlocks.POLISHED_CUT_AEQUALIS_SLAB.get());
                    pOutput.accept(testosteroneModBlocks.POLISHED_CUT_AEQUALIS_WALL.get());

                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_BRICKS.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_BRICK_STAIRS.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_BRICK_SLAB.get());
                    pOutput.accept(testosteroneModBlocks.CUT_AEQUALIS_BRICK_WALL.get());

                    pOutput.accept(testosteroneModBlocks.SMALL_AEQUALIS_BRICKS.get());
                    pOutput.accept(testosteroneModBlocks.SMALL_AEQUALIS_BRICK_STAIRS.get());
                    pOutput.accept(testosteroneModBlocks.SMALL_AEQUALIS_BRICK_SLAB.get());
                    pOutput.accept(testosteroneModBlocks.SMALL_AEQUALIS_BRICK_WALL.get());
                    pOutput.accept(testosteroneModBlocks.LAYERED_AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.AEQUALIS_PILLAR.get());

                    pOutput.accept(testosteroneModBlocks.SMOOTH_AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.SMOOTH_DIAMOND_AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.SMOOTH_DARK_DIAMOND_AEQUALIS.get());
                    pOutput.accept(testosteroneModBlocks.BIG_AEQUALIS_BRICKS.get());
                })
                .build()
        );
    }

    public static ItemStack tippedArrow(Potion potion) {
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        PotionUtils.setPotion(stack, potion);
        return stack;
    }

    public static ItemStack addBooleanNbt(ItemStack itemStack, String key, boolean bool) {
        CompoundTag nbtData = new CompoundTag();
        nbtData.putBoolean(key, bool);
        itemStack.setTag(nbtData);
        return itemStack;
    }
}


