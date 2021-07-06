package rmc.mixins.enchantment_stripper.inject;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rmc.mixins.enchantment_stripper.EnchantmentStripper;

import java.util.Iterator;
import java.util.List;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "Lnet/minecraft/enchantment/EnchantmentHelper;getAvailableEnchantmentResults(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "TAIL"))
    private static void removeStrippedEnchantsOnSelect(int arg0, ItemStack arg1, boolean arg2, CallbackInfoReturnable<?> mixin, List<EnchantmentData> enchs) {
        Iterator<EnchantmentData> it = enchs.iterator();
        while (it.hasNext()) {
            if (EnchantmentStripper.isStripped(it.next().enchantment)) {
                it.remove();
            }
        }
    }

}
