package net.mervyn.testosterone.items.custom;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CheeseOnAStick extends Item {

    public CheeseOnAStick(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canAttackBlock(net.minecraft.world.level.block.state.BlockState state,
                                  net.minecraft.world.level.Level level,
                                  net.minecraft.core.BlockPos pos,
                                  Player player) {
        return !player.isCreative();
    }
}
