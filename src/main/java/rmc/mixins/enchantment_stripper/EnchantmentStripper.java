package rmc.mixins.enchantment_stripper;

import com.electronwill.nightconfig.core.file.FileConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Developed by RMC Team, 2021
 * @author KR33PY
 */
public abstract class EnchantmentStripper {

    private static final ArrayList<String> STRIPPED_ENCHANTS;

    static {
        File cfgFile = new File("mixincfg/enchantment-stripper.toml");
        cfgFile.getParentFile().mkdir();
        FileConfig cfg = FileConfig.of(cfgFile);
        cfg.load();
        cfg.add("stripped-enchants", new ArrayList<>());
        STRIPPED_ENCHANTS = cfg.get("stripped-enchants");
        cfg.save();
        cfg.close();
    }

    public static boolean isStripped(Enchantment ench) {
        return STRIPPED_ENCHANTS.contains(ench.getRegistryName().toString());
    }

    public static Map<Enchantment, Integer> tryStrip(ItemStack stack) {
        if (!stack.isEnchanted()) {
            return null;
        }
        boolean stripped = false;
        Map<Enchantment, Integer> enchs = EnchantmentHelper.getEnchantments(stack);
        Iterator<Enchantment> it = enchs.keySet().iterator();
        while (it.hasNext()) {
            if (isStripped(it.next())) {
                it.remove();
                stripped = true;
            }
        }
        return stripped ? enchs : null;
    }

}
