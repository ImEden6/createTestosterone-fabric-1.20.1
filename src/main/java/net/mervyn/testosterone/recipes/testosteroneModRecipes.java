package net.mervyn.testosterone.recipes;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.CreateLang;
import net.mervyn.testosterone.testosterone;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public enum testosteroneModRecipes implements IRecipeTypeInfo {
    DECANTATION(decantation::new);

    private final ResourceLocation id;
    private final RecipeSerializer<?> serializerObject;
    private final RecipeType<?> typeObject;

    testosteroneModRecipes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = CreateLang.asId(name());
        id = testosterone.rl(name);
        serializerObject = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializerSupplier.get());
        typeObject = Registry.register(BuiltInRegistries.RECIPE_TYPE, id, new RecipeType<>() {
            @Override
            public String toString() {
                return id.toString();
            }
        });
    }

    testosteroneModRecipes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ProcessingRecipeSerializer<>(processingFactory));
    }

    public static void register() {
        // Triggers static block initialization
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends RecipeType<?>> T getType() {
        return (T) typeObject;
    }
}
