package rmc.mixins.enchantment_stripper.inject;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rmc.mixins.enchantment_stripper.EnchantmentStripper;

import java.util.Iterator;
import java.util.Map;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ItemEntity.class)
public abstract class ItemEntityMixin {

    @Inject(method = "Lnet/minecraft/entity/item/ItemEntity;playerTouch(Lnet/minecraft/entity/player/PlayerEntity;)V",
            at = @At(value = "HEAD"))
    private void removeStrippedEnchantsOnPickup(CallbackInfo mixin) {
        ItemStack stack = ((ItemEntity)(Object) this).getItem();
        Map<Enchantment, Integer> enchs = EnchantmentStripper.tryStrip(stack);
        if (enchs != null) {
            EnchantmentHelper.setEnchantments(enchs, stack);
        }
    }

}
