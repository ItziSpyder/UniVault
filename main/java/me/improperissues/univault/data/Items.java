package me.improperissues.univault.data;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Items {

    public static ItemStack[] getValues() {
        return new ItemStack[]{
                BACK,
                NEXT,
                NULL,
                AIR,
                SUBMIT
        };
    }

    public static void registerItems() {
        setBACK();
        setNEXT();
        setNULL();
        setAIR();
        setSUBMIT();
    }

    public static ItemStack addItemFlags(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        for (ItemFlag flag : ItemFlag.values()) { meta.addItemFlags(flag); }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addFoil(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.LUCK,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setBlank(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        meta.setUnbreakable(true);
        meta.setLore(new ArrayList<>());
        item.setItemMeta(meta);
        return addItemFlags(item);
    }

    public static ItemStack BACK;
    public static ItemStack NEXT;
    public static ItemStack NULL;
    public static ItemStack AIR;
    public static ItemStack SUBMIT;

    static void setBACK() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fBack");
        item.setItemMeta(meta);
        BACK = addItemFlags(item);
    }

    static void setNEXT() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fNext");
        item.setItemMeta(meta);
        NEXT = addItemFlags(item);
    }

    static void setNULL() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cNULL");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7This item is null because",
                "§7it didn't meet the required",
                "§7conditions to be saved to a file.",
                "§7This could mean that the item",
                "§7nbt was too large or too small.",
                "§7This can be configured in the",
                "§7plugin config"
        )));
        item.setItemMeta(meta);
        addFoil(item);
        NULL = addItemFlags(item);
    }

    static void setAIR() {
        AIR = new ItemStack(Material.AIR);
    }

    static void setSUBMIT() {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aSUBMIT");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7By submitting an item, you",
                "§7are storing a new item into",
                "§7the server, and everyone will",
                "§7have access to it. Make sure",
                "§7the submitted items don't go",
                "§7pass the nbt limit, or it will",
                "§7be removed!"
        )));
        item.setItemMeta(meta);
        SUBMIT = addItemFlags(item);
    }
}
