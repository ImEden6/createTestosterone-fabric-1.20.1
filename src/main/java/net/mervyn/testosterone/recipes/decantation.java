package net.mervyn.testosterone.recipes;

import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.mervyn.testosterone.blocks.decanterCentrifuge.decanterCentrifugeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public class decantation extends ProcessingRecipe<Inventory> {
    public decantation(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(testosteroneModRecipes.DECANTATION, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 0;
    }

    @Override
    protected int getMaxOutputCount() {
        return 0;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }

    public boolean match(@NotNull decanterCentrifugeBlockEntity decanterCentrifugeBlockEntity) {
        if (fluidIngredients.isEmpty() || fluidResults.isEmpty()) return false;
        if (decanterCentrifugeBlockEntity.getLevel() == null) return false;

        Direction facing = decanterCentrifugeBlockEntity.getBlockState().getValue(HorizontalKineticBlock.HORIZONTAL_FACING);

        BlockPos input = decanterCentrifugeBlockEntity.getBlockPos().relative(facing.getOpposite());
        BlockPos output = decanterCentrifugeBlockEntity.getBlockPos().relative(facing);

        Storage<FluidVariant> inputHandler = FluidStorage.SIDED.find(decanterCentrifugeBlockEntity.getLevel(), input, facing);
        Storage<FluidVariant> outputHandler = FluidStorage.SIDED.find(decanterCentrifugeBlockEntity.getLevel(), output, facing.getOpposite());

        if (inputHandler == null || outputHandler == null) return false;

        var ingredient = getFluidIngredients().get(0);
        var resultStack = getFluidResults().get(0).copy();

        FluidVariant matchingVariant = null;
        long requiredDroplets = 0;

        for (var view : inputHandler) {
            if (view.isResourceBlank()) continue;

            FluidStack portingStack =
                    new FluidStack(view.getResource().getFluid(), view.getAmount());

            if (ingredient.test(portingStack)) {
                matchingVariant = view.getResource();
                requiredDroplets = ingredient.getRequiredAmount();
                if (view.getAmount() >= requiredDroplets) {
                    break;
                } else {
                    matchingVariant = null;
                }
            }
        }

        if (matchingVariant == null) return false;

        FluidVariant resultVariant = FluidVariant.of(resultStack.getFluid(), resultStack.getOrCreateTag());
        long resultDroplets = resultStack.getAmount();

        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = inputHandler.extract(matchingVariant, requiredDroplets, transaction);
            if (extracted != requiredDroplets) return false;

            long inserted = outputHandler.insert(resultVariant, resultDroplets, transaction);
            if (inserted != resultDroplets) return false;

            transaction.commit();
            return true;
        }
    }

    @Override
    public boolean matches(@NotNull Inventory pContainer, @NotNull Level pLevel) {
        return false;
    }
}
