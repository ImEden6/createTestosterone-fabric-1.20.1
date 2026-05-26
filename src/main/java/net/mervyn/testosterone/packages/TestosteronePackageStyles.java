package net.mervyn.testosterone.packages;

import com.simibubi.create.content.logistics.box.PackageStyles.PackageStyle;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.mervyn.testosterone.fluids.testosteroneFluids;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.mervyn.testosterone.testosterone;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class TestosteronePackageStyles {
    public static final ResourceLocation TESTOSTERONE_TYPE = new ResourceLocation(testosterone.MOD_ID, "testosterone_cardboard");
    public static final ResourceLocation TRENBOLONE_TYPE = new ResourceLocation(testosterone.MOD_ID, "trenbolone_cardboard");
    private static final Random RANDOM = new Random();

    public static final List<PackageStyle> TESTOSTERONE_PILL_STYLES = List.of(
            new PackageStyle(TESTOSTERONE_TYPE.toString(), 12, 12, 23f, false),
            new PackageStyle(TESTOSTERONE_TYPE.toString(), 10, 12, 22f, false),
            new PackageStyle(TESTOSTERONE_TYPE.toString(), 10, 8, 18f, false),
            new PackageStyle(TESTOSTERONE_TYPE.toString(), 12, 10, 21f, false)
    );

    public static final List<PackageStyle> TRENBOLONE_VIAL_STYLES = List.of(
            new PackageStyle(TRENBOLONE_TYPE.toString(), 12, 12, 23f, false),
            new PackageStyle(TRENBOLONE_TYPE.toString(), 10, 12, 22f, false),
            new PackageStyle(TRENBOLONE_TYPE.toString(), 10, 8, 18f, false),
            new PackageStyle(TRENBOLONE_TYPE.toString(), 12, 10, 21f, false)
    );

    private static Set<Item> allowedTestosteroneItemsToBeCounted;
    private static Set<Item> allowedTrenboloneItemsToBeCounted;

    private static Set<Item> getAllowedTestosteroneItemsToBeCounted() {
        if (allowedTestosteroneItemsToBeCounted == null) {
            allowedTestosteroneItemsToBeCounted = Set.of(
                    testosteroneModItems.TESTOSTERONE_PILL.get(),
                    testosteroneModBlocks.TESTOSTERONE_PILL_BLOCK.get().asItem(),
                    testosteroneModItems.TESTOSTERONE_SHOT.get(),
                    testosteroneModItems.TESTOSTERONE_PROTEIN_BAR.get(),
                    testosteroneFluids.TESTOSTERONE_FLUID.getBucket().get()
            );
        }
        return allowedTestosteroneItemsToBeCounted;
    }

    private static Set<Item> getAllowedTrenboloneItemsToBeCounted() {
        if (allowedTrenboloneItemsToBeCounted == null) {
            allowedTrenboloneItemsToBeCounted = Set.of(
                    testosteroneModItems.TRENBOLONE_SHOT.get(),
                    testosteroneModBlocks.TRENBOLONE_VIAL.get().asItem(),
                    testosteroneModItems.BETTER_TRENBOLONE_SHOT.get(),
                    testosteroneFluids.TRENBOLONE_FLUID.getBucket().get()
            );
        }
        return allowedTrenboloneItemsToBeCounted;
    }

    public static ItemStack containing(TestosteroneItemHandler stacks) {
        if (isMajorityOfItemsTestosteroneItems(stacks)) {
            ItemStack box = new ItemStack(
                    testosteroneModItems.ALL_TESTOSTERONE_PILL_BOXES.get(RANDOM.nextInt(testosteroneModItems.ALL_TESTOSTERONE_PILL_BOXES.size())).get()
            );

            CompoundTag compound = new CompoundTag();
            compound.put("Items", stacks.serializeNBT());
            box.setTag(compound);

            return box;
        } else if (isMajorityOfItemsTrenboloneItems(stacks)) {
            ItemStack box = new ItemStack(
                    testosteroneModItems.ALL_TRENBOLONE_BOXES.get(RANDOM.nextInt(testosteroneModItems.ALL_TRENBOLONE_BOXES.size())).get()
            );

            CompoundTag compound = new CompoundTag();
            compound.put("Items", stacks.serializeNBT());
            box.setTag(compound);

            return box;
        }
        return null;
    }

    private static boolean isMajorityOfItemsTestosteroneItems(TestosteroneItemHandler stacks) {
        Map<Item, Integer> itemToAmount = new HashMap<>();

        for (int i = 0; i < stacks.getSlots(); i++) {
            ItemStack stack = stacks.getStackInSlot(i);
            Item item = stack.getItem();

            int amount = itemToAmount.getOrDefault(item, 0);
            amount += stack.getCount();
            itemToAmount.put(item, amount);
        }

        int testosteroneCount = 0;

        for (Item x : getAllowedTestosteroneItemsToBeCounted()) {
            if (x instanceof BlockItem) {
                testosteroneCount += itemToAmount.getOrDefault(x, 0) * 9;
            } else {
                testosteroneCount += itemToAmount.getOrDefault(x, 0);
            }
        }

        int otherCount = itemToAmount.entrySet().stream()
                .filter(e -> !getAllowedTestosteroneItemsToBeCounted().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();

        return testosteroneCount > otherCount;
    }

    private static boolean isMajorityOfItemsTrenboloneItems(TestosteroneItemHandler stacks) {
        Map<Item, Integer> itemToAmount = new HashMap<>();

        for (int i = 0; i < stacks.getSlots(); i++) {
            ItemStack stack = stacks.getStackInSlot(i);
            Item item = stack.getItem();

            int amount = itemToAmount.getOrDefault(item, 0);
            amount += stack.getCount();
            itemToAmount.put(item, amount);
        }

        int trenboloneCount = 0;

        for (Item x : getAllowedTrenboloneItemsToBeCounted()) {
            if (x instanceof BlockItem) {
                trenboloneCount += itemToAmount.getOrDefault(x, 0) * 9;
            } else {
                trenboloneCount += itemToAmount.getOrDefault(x, 0);
            }
        }

        int otherCount = itemToAmount.entrySet().stream()
                .filter(e -> !getAllowedTrenboloneItemsToBeCounted().contains(e.getKey()))
                .mapToInt(Map.Entry::getValue)
                .sum();

        return trenboloneCount > otherCount;
    }
}
