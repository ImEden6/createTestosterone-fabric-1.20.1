package net.mervyn.testosterone.items;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyItem;
import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.mervyn.testosterone.entities.testosteroneEntities;
import net.mervyn.testosterone.items.curios.tie;
import net.mervyn.testosterone.items.custom.*;
import net.mervyn.testosterone.packages.TestosteronePackageStyles;
import net.mervyn.testosterone.registry.ItemEntry;
import net.mervyn.testosterone.registry.RegistryObject;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;

import java.util.List;

public class testosteroneModItems {

    private static <T extends Item> ItemEntry<T> register(String name, T item) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(testosterone.MOD_ID, name), item);
        return ItemEntry.of(item);
    }

    public static final List<ItemEntry<PackageItem>> ALL_TESTOSTERONE_PILL_BOXES =
            TestosteronePackageStyles.TESTOSTERONE_PILL_STYLES.stream()
                    .map(style -> {
                        PackageItem item = new PackageItem(new Item.Properties().stacksTo(1), style);
                        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(testosterone.MOD_ID, style.getItemId().getPath()), item);
                        return ItemEntry.of(item);
                    })
                    .toList();

    public static final List<ItemEntry<PackageItem>> ALL_TRENBOLONE_BOXES =
            TestosteronePackageStyles.TRENBOLONE_VIAL_STYLES.stream()
                    .map(style -> {
                        PackageItem item = new PackageItem(new Item.Properties().stacksTo(1), style);
                        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(testosterone.MOD_ID, style.getItemId().getPath()), item);
                        return ItemEntry.of(item);
                    })
                    .toList();

    public static final ItemEntry<SequencedAssemblyItem> INCOMPLETE_ANDESITE_ALLOY =
            register("incomplete_andesite_alloy", new SequencedAssemblyItem(new Item.Properties()));

    public static final ItemEntry<Item> TESTOSTERONE_PILL =
            register("testosterone_pill", new effectFoodItem(new Item.Properties().food(testosteroneModFoods.TESTOSTERONE_PILL).rarity(Rarity.RARE).stacksTo(16), testosteroneModEffects.TESTOSTERONE_EFFECT, 6000, 0));

    public static final ItemEntry<Item> TESTOSTERONE_PROTEIN_BAR =
            register("testosterone_protein_bar", new effectFoodItem(new Item.Properties().food(testosteroneModFoods.TESTOSTERONE_PROTEIN_BAR).rarity(Rarity.RARE), testosteroneModEffects.TESTOSTERONE_EFFECT, 6000, 0));

    public static final ItemEntry<shotItem> TESTOSTERONE_SHOT =
            register("testosterone_shot", new shotItem(new Item.Properties().food(testosteroneModFoods.BLANK).rarity(Rarity.EPIC).stacksTo(16), testosteroneModEffects.TESTOSTERONE_EFFECT, 1));

    public static final ItemEntry<shotItem> TRENBOLONE_SHOT =
            register("trenbolone_shot", new shotItem(new Item.Properties().food(testosteroneModFoods.BLANK).rarity(Rarity.EPIC).stacksTo(16), testosteroneModEffects.ROID_RAGE_EFFECT));

    public static final ItemEntry<shotItem> BETTER_TRENBOLONE_SHOT =
            register("better_trenbolone_shot", new shotItem(new Item.Properties().food(testosteroneModFoods.BLANK).rarity(Rarity.EPIC).stacksTo(16), testosteroneModEffects.ROID_RAGE_EFFECT, 1, true));

    public static final ItemEntry<beerMug> BEER_MUG =
            register("beer_mug", new beerMug(new Item.Properties().food(testosteroneModFoods.BLANK).stacksTo(16)));

    public static final ItemEntry<tie> TIE =
            register("tie", new tie(new Item.Properties()));

    public static final ItemEntry<Item> AFTERLIFE_TOTEM =
            register("totem_of_afterlife", new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final ItemEntry<CheeseOnAStick> CHEESE_ON_A_STICK =
            register("cheese_on_a_stick", new CheeseOnAStick(new Item.Properties().stacksTo(1).defaultDurability(256)));

    public static final ItemEntry<Item> CHEESE_CURDS =
            register("cheese_curds", new Item(new Item.Properties().stacksTo(64).food(testosteroneModFoods.CHEESE_CURDS)));

    public static final ItemEntry<Item> WHEY_PROTEIN =
            register("whey_protein", new Item(new Item.Properties().stacksTo(64)));

    public static final ItemEntry<Item> RAT_FUR =
            register("rat_fur", new Item(new Item.Properties().stacksTo(64)));

    public static RegistryObject<Item> STUPID_RAT_SPAWN_EGG;

    public static void register() {
        STUPID_RAT_SPAWN_EGG = RegistryObject.of(
            Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(testosterone.MOD_ID, "stupid_rat_spawn_egg"),
                new SpawnEggItem(testosteroneEntities.RAT.get(), 0xFFFFFF, 0xFFFFFF, new Item.Properties())
            )
        );
    }
}


