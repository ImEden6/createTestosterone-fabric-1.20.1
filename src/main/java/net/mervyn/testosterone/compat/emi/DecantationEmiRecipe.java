package net.mervyn.testosterone.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.mervyn.testosterone.recipes.decantation;
import net.minecraft.resources.ResourceLocation;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.mervyn.testosterone.blocks.testosteroneModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DecantationEmiRecipe implements EmiRecipe {
	private final ResourceLocation id;
	private final List<EmiIngredient> inputs;
	private final List<EmiStack> outputs;

	public DecantationEmiRecipe(decantation recipe) {
		this.id = recipe.getId();

		this.inputs = new ArrayList<>();
		if (!recipe.getFluidIngredients().isEmpty()) {
			FluidIngredient ingredient = recipe.getFluidIngredients().get(0);
			List<EmiStack> matching = new ArrayList<>();
			for (FluidStack stack : ingredient.getMatchingFluidStacks()) {
				matching.add(EmiStack.of(stack.getFluid(), stack.getOrCreateTag(), ingredient.getRequiredAmount()));
			}
			this.inputs.add(EmiIngredient.of(matching));
		}

		this.outputs = new ArrayList<>();
		if (!recipe.getFluidResults().isEmpty()) {
			FluidStack result = recipe.getFluidResults().get(0);
			this.outputs.add(EmiStack.of(result.getFluid(), result.getOrCreateTag(), result.getAmount()));
		}
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return TestosteroneEmiPlugin.DECANTATION_CATEGORY;
	}

	@Override
	public @Nullable ResourceLocation getId() {
		return id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return inputs;
	}

	@Override
	public List<EmiStack> getOutputs() {
		return outputs;
	}

	@Override
	public int getDisplayWidth() {
		return 176;
	}

	@Override
	public int getDisplayHeight() {
		return 70;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		// Background
		widgets.addTexture(new ResourceLocation("testosterone", "textures/gui/decantation_jei.png"), 0, 0, 176, 70, 0, 0);

		// Inputs (slot matches 26, 26)
		if (!inputs.isEmpty()) {
			widgets.addSlot(inputs.get(0), 26, 26).drawBack(false);
		}

		// Outputs (slot matches 132, 49)
		if (!outputs.isEmpty()) {
			widgets.addSlot(outputs.get(0), 132, 49).drawBack(false).recipeContext(this);
		}

		// Decanter Centrifuge item icon as decoration at 80, 30
		widgets.addSlot(EmiStack.of(testosteroneModBlocks.DECANTER_CENTRIFUGE.get()), 80, 30).drawBack(false);
	}
}
