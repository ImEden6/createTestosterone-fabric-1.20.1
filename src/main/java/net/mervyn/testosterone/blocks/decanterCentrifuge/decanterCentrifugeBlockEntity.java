package net.mervyn.testosterone.blocks.decanterCentrifuge;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.mervyn.testosterone.blocks.testosteroneBlockEntities;
import net.mervyn.testosterone.recipes.decantation;
import net.mervyn.testosterone.recipes.testosteroneModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class decanterCentrifugeBlockEntity extends KineticBlockEntity {

    float tickCount = 0;

    public decanterCentrifugeBlockEntity(BlockPos pos, BlockState state) {
        this(testosteroneBlockEntities.DECANTER_CENTRIFUGE.get(), pos, state);
    }

    public decanterCentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

        if (level != null && level.isClientSide) return;

        if (this.getBlockState().getBlock() instanceof decanterCentrifugeBlock decanterCentrifugeBlock) {
            float minSpeed = decanterCentrifugeBlock.getMinimumRequiredSpeedLevel().getSpeedValue();

            if (Math.abs(getSpeed()) >= minSpeed) {
                tickCount += Math.abs(getSpeed());

                if (tickCount >= 256) {
                    tickCount -= 256;

                    List<decantation> allRecipes = findRecipe();

                    allRecipes.forEach(decantation -> {
                        level.playSound(null, getBlockPos(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 0.25f, 1.5f);
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.BUBBLE,
                                    getBlockPos().getX() + 0.5,
                                    getBlockPos().getY() + 0.5,
                                    getBlockPos().getZ() + 0.5,
                                    5, 0.15, 0.15, 0.15, 0.0);
                        }
                        setChanged();
                        sendData();
                    });
                }
                return;
            }
        }
        tickCount = 0;
    }

    public List<decantation> findRecipe() {
        Level level = getLevel();

        if (level != null) {
            return level.getRecipeManager()
                    .getAllRecipesFor(testosteroneModRecipes.DECANTATION.getType())
                    .stream()
                    .filter(decantation.class::isInstance)
                    .map(decantation.class::cast)
                    .filter(r -> r.match(this))
                    .toList();
        } else {
            return new ArrayList<>();
        }
    }
}
