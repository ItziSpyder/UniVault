package me.improperissues.univault.data;

import org.bukkit.inventory.ItemStack;

public class NBT {

    public static String getNBT(ItemStack item) {
        return item.getItemMeta().toString();
    }

    public static boolean passable(ItemStack item) {
        int length = getNBT(item).length();
        return (length > Config.getMinNBT()) && (length < Config.getMaxNBT());
    }
}
