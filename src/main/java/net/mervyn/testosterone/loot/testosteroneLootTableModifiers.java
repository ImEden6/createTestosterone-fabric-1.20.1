package net.mervyn.testosterone.loot;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.mervyn.testosterone.items.testosteroneModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class testosteroneLootTableModifiers {
    private static final float TIE_SPAWN_CHANCE = 0.1f;
    private static final Set<ResourceLocation> TIE_CHEST_LOOT_TABLES = Set.of(
            new ResourceLocation("minecraft", "chests/buried_treasure"),
            new ResourceLocation("minecraft", "chests/woodland_mansion"),
            new ResourceLocation("minecraft", "chests/abandoned_mineshaft"),
            new ResourceLocation("minecraft", "chests/shipwreck_supply"),
            new ResourceLocation("minecraft", "chests/end_city_treasure"),
            new ResourceLocation("minecraft", "chests/ancient_city")
    );

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!source.isBuiltin() || !TIE_CHEST_LOOT_TABLES.contains(id)) {
                return;
            }

            LootPool.Builder pool = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .when(LootItemRandomChanceCondition.randomChance(TIE_SPAWN_CHANCE));

            for (DyeColor color : DyeColor.values()) {
                pool.add(createColoredTieEntry(color));
            }

            tableBuilder.pool(pool.build());
        });
    }

    private static CompoundTag createTieTag(DyeColor color) {
        try {
            return TagParser.parseTag("{color:\"" + color.getName().toLowerCase() + "\"}");
        } catch (CommandSyntaxException exception) {
            throw new IllegalStateException("Failed to create tie loot tag for " + color.getName(), exception);
        }
    }

    @SuppressWarnings("deprecation")
    private static LootItem.Builder<?> createColoredTieEntry(DyeColor color) {
        // Minecraft 1.20.1 still uses set_nbt for this; the set_components replacement exists in later versions.
        return LootItem.lootTableItem(testosteroneModItems.TIE.get())
                .apply(SetNbtFunction.setTag(createTieTag(color)));
    }
}
