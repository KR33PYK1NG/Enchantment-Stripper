package rmc.mixins.enchantment_stripper.inject;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CClickWindowPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rmc.mixins.enchantment_stripper.EnchantmentStripper;

import java.util.Map;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
@Mixin(value = ServerPlayNetHandler.class)
public abstract class ServerPlayNetHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "Lnet/minecraft/network/play/ServerPlayNetHandler;handleContainerClick(Lnet/minecraft/network/play/client/CClickWindowPacket;)V",
            at = @At(value = "HEAD"))
    private void removeStrippedEnchantsOnClick(CClickWindowPacket packet, CallbackInfo mixin) {
        if (this.player == null) return;
        if (this.player.containerMenu == null) return;
        if (this.player.containerMenu.slots == null) return;
        if (packet.getSlotNum() < 0 || packet.getSlotNum() >= this.player.containerMenu.slots.size()) return;
        Slot item = this.player.containerMenu.slots.get(packet.getSlotNum());
        if (item == null) return;
        ItemStack stack = item.getItem();
        if (stack != null) {
            Map<Enchantment, Integer> enchs = EnchantmentStripper.tryStrip(stack);
            if (enchs != null) {
                EnchantmentHelper.setEnchantments(enchs, stack);
            }
        }
    }

}
