package net.mervyn.testosterone.mixin;

import net.mervyn.testosterone.effects.testosteroneModEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void onPlayerTouch(Player player, CallbackInfo ci) {
        if (player.hasEffect(testosteroneModEffects.AFTERLIFE_EFFECT.get())) {
            ci.cancel();
        }
    }
}
