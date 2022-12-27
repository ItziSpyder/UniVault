package me.improperissues.univault.data;

import me.improperissues.univault.UniVault;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NBT {

    public static String getNBT(ItemStack item) {
        return item.getItemMeta().toString();
    }

    public static int getNBTLength(ItemStack item) {
        return item.getItemMeta().toString().length();
    }

    public static boolean passable(ItemStack item) {
        int length = getNBT(item).length();
        return (length > Config.getMinNBT()) && (length < Config.getMaxNBT());
    }

    public static boolean submittable(ItemStack item) {
        String display = getDisplay(item);
        return passable(item) && !willDuplicate(item) && !item.getType().equals(Material.AIR) && !(display.equals(" ") || display.contains(getDisplay(Items.SUBMIT))
                || display.contains(getDisplay(Items.SEARCH)) || display.contains(getDisplay(Items.NULL))
                || display.contains(getDisplay(Items.RANDOMITEMS)) || display.contains(getDisplay(Items.ALLITEMS))
                || display.contains(getDisplay(Items.SHULKERITEMS)) || display.contains("§eRANDOM ITEM"));
    }

    public static boolean willDuplicate(ItemStack item) {
        ItemStack clone = item.clone();
        clone.setAmount(1);
        return Shelf.STOREDITEMS.contains(clone) && !Config.getDuplicates();
    }

    public static void testItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            player.sendMessage(UniVault.STARTER + "§4Air does not have nbt!");
            return;
        }
        int nbt = getNBTLength(item);
        player.sendMessage(UniVault.STARTER + "§7" + item.getType().name() + " §6has §7" + nbt + " §6characters worth of nbt data!");
        if (nbt > Config.getMaxNBT()) {
            player.sendMessage(UniVault.STARTER + "§6This item's nbt is §7" + (nbt - Config.getMaxNBT()) + " §6characters over limit!");
        } else if (nbt < Config.getMinNBT()) {
            player.sendMessage(UniVault.STARTER + "§6This item's nbt is §7" + (Config.getMinNBT() - nbt) + " §6characters under limit!");
        } else {
            player.sendMessage(UniVault.STARTER + "§6This item's nbt is within limits!");
        }
        player.sendMessage(UniVault.STARTER + "§6Item duplicates: §7" + willDuplicate(item));
    }

    public static void readItem(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            player.sendMessage(UniVault.STARTER + "§4Air does not have nbt!");
            return;
        }
        String nbt = getNBT(item).toLowerCase().replaceAll("§","&");
        nbt = (nbt.length() < 262144 ? nbt : "§cString value is too big!");
        player.sendMessage(UniVault.STARTER + "§7" + item.getType().name() + " §6has the following data: §a" + nbt);
    }

    public static String getDisplay(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }
}
