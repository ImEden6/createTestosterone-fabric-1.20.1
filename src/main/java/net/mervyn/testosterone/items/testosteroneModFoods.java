package net.mervyn.testosterone.items;

import net.minecraft.world.food.FoodProperties;

public class testosteroneModFoods {
    public static final FoodProperties TESTOSTERONE_PILL = new FoodProperties.Builder()
            .nutrition(0)
            .saturationMod(0f)
            .alwaysEat()
            .fast()
            .build();

    public static final FoodProperties TESTOSTERONE_PROTEIN_BAR = new FoodProperties.Builder()
            .nutrition(8)
            .saturationMod(1.5f)
            .alwaysEat()
            .fast()
            .build();

    public static final FoodProperties CHEESE_CURDS = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(1f)
            .build();

    public static final FoodProperties CHEESE_BLOCK = new FoodProperties.Builder()
            .nutrition(1)
            .saturationMod(1f)
            .build();

    public static final FoodProperties BLANK = new FoodProperties.Builder().alwaysEat().build();
}


