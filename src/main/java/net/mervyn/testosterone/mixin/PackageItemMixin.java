package net.mervyn.testosterone.mixin;

import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.mervyn.testosterone.packages.TestosteroneItemHandlerWrapper;
import net.mervyn.testosterone.packages.TestosteronePackageStyles;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PackageItem.class, remap = false)
public class PackageItemMixin {
    @Shadow
    public PackageStyles.PackageStyle style;

    @Inject(method = "containing(Lio/github/fabricators_of_create/porting_lib/transfer/item/ItemStackHandler;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "HEAD"), cancellable = true)
    private static void containing(ItemStackHandler stacks, CallbackInfoReturnable<ItemStack> cir) {
        var contained = TestosteronePackageStyles.containing(new TestosteroneItemHandlerWrapper(stacks));
        if (contained != null) {
            cir.setReturnValue(contained);
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/item/Item$Properties;Lcom/simibubi/create/content/logistics/box/PackageStyles$PackageStyle;)V",
            at = @At(value = "RETURN"))
    private void constructor(Item.Properties properties, PackageStyles.PackageStyle style, CallbackInfo ci) {
        if (TestosteronePackageStyles.TESTOSTERONE_PILL_STYLES.contains(style))
            (style.rare() ? PackageStyles.RARE_BOXES : PackageStyles.STANDARD_BOXES).remove((PackageItem) (Object) this);

        if (TestosteronePackageStyles.TRENBOLONE_VIAL_STYLES.contains(style))
            (style.rare() ? PackageStyles.RARE_BOXES : PackageStyles.STANDARD_BOXES).remove((PackageItem) (Object) this);
    }

    @Inject(method = "getDescriptionId", at = @At("HEAD"), cancellable = true, remap = true)
    public void getDescriptionId(CallbackInfoReturnable<String> cir) {
        if (style.getItemId().getNamespace().equals("testosterone")) {
            cir.setReturnValue("item." + style.type().replaceFirst(":", ".") + "_package");
        }
    }
}
