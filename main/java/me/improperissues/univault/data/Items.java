package me.improperissues.univault.data;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Items {

    public static List<ItemStack> getValues() {
        return new ArrayList<>(Arrays.asList(
                BACK,
                NEXT,
                NULL,
                AIR,
                SUBMIT,
                RANDOMITEMS,
                SHULKERITEMS,
                ALLITEMS,
                SEARCH,
                SUBMISSION,
                SUBMITCHEST
        ));
    }

    public static void registerItems() {
        setBACK();
        setNEXT();
        setNULL();
        setAIR();
        setSUBMIT();
        setRANDOMITEMS();
        setSHULKERITEMS();
        setALLITEMS();
        setSEARCH();
        setSUBMISSION();
        setSUBMITCHEST();
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
    public static ItemStack ALLITEMS;
    public static ItemStack SHULKERITEMS;
    public static ItemStack RANDOMITEMS;
    public static ItemStack SEARCH;
    public static ItemStack SUBMISSION;
    public static ItemStack SUBMITCHEST;


    static void setSUBMITCHEST() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c#SUBMISSION");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7- Right click this block",
                "§7  after placing it down to",
                "§7  open up submission menu"
        )));
        item.setItemMeta(meta);
        SUBMITCHEST = addItemFlags(item);
    }

    static void setSUBMISSION() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fSubmission Page");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Submission menu -->"
        )));
        item.setItemMeta(meta);
        SUBMISSION = addItemFlags(item);
    }

    static void setSEARCH() {
        ItemStack item = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bSearch Query");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Search the archived items -->"
        )));
        item.setItemMeta(meta);
        SEARCH = addItemFlags(item);
    }

    static void setALLITEMS() {
        ItemStack item = new ItemStack(Material.BOOKSHELF);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fALL ITEMS");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Travel to current page -->"
        )));
        item.setItemMeta(meta);
        ALLITEMS = addItemFlags(item);
    }

    static void setSHULKERITEMS() {
        ItemStack item = new ItemStack(Material.SHULKER_BOX);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fSHULKER ITEMS");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Travel to current page -->"
        )));
        item.setItemMeta(meta);
        SHULKERITEMS = addItemFlags(item);
    }

    static void setRANDOMITEMS() {
        ItemStack item = new ItemStack(Material.GLOWSTONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fRANDOM ITEMS");
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7Travel to current page -->"
        )));
        item.setItemMeta(meta);
        RANDOMITEMS = addItemFlags(item);
    }

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
                "§7The item you're looking for",
                "§7turned out to be null, this",
                "§7may be a result of the item",
                "§7not being able to pass the",
                "§7verification checks or an",
                "§7internal error."
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
