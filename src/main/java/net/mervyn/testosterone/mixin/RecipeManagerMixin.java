package net.mervyn.testosterone.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "fromJson", at = @At("HEAD"))
    private static void onFromJson(ResourceLocation id, JsonObject json, CallbackInfoReturnable<Recipe<?>> cir) {
        if (id.getNamespace().equals("testosterone")) {
            testosterone$scaleFluidAmounts(json);
        }
    }

    @Unique
    private static void testosterone$scaleFluidAmounts(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            if (obj.has("amount") && (obj.has("fluid") || obj.has("fluidTag"))) {
                JsonElement amountElement = obj.get("amount");
                if (amountElement.isJsonPrimitive() && amountElement.getAsJsonPrimitive().isNumber()) {
                    long amount = amountElement.getAsLong();
                    obj.addProperty("amount", amount * 81);
                }
            }
            for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                testosterone$scaleFluidAmounts(entry.getValue());
            }
        } else if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (JsonElement el : array) {
                testosterone$scaleFluidAmounts(el);
            }
        }
    }
}
