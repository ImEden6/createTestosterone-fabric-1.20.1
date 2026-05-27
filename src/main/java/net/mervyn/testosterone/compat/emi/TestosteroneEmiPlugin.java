package net.mervyn.testosterone.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import net.mervyn.testosterone.recipes.decantation;
import net.mervyn.testosterone.recipes.testosteroneModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@EmiEntrypoint
public class TestosteroneEmiPlugin implements EmiPlugin {
	public static final ResourceLocation DECANTATION_ID = new ResourceLocation("testosterone", "decantation");
	public static final EmiRecipeCategory DECANTATION_CATEGORY = new EmiRecipeCategory(DECANTATION_ID,
			EmiStack.of(testosteroneModBlocks.DECANTER_CENTRIFUGE.get()));

	@Override
	public void register(EmiRegistry registry) {
		registry.addCategory(DECANTATION_CATEGORY);
		registry.addWorkstation(DECANTATION_CATEGORY, EmiStack.of(testosteroneModBlocks.DECANTER_CENTRIFUGE.get()));

		RecipeManager rm = registry.getRecipeManager();
		List<decantation> recipes = rm.getAllRecipesFor(testosteroneModRecipes.DECANTATION.getType());
		for (decantation recipe : recipes) {
			registry.addRecipe(new DecantationEmiRecipe(recipe));
		}
	}
}
