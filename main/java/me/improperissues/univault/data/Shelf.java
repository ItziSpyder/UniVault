package me.improperissues.univault.data;

import me.improperissues.univault.UniVault;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Shelf {

    public static File PARENTFOLDER = new File("plugins/UniVault/shelves");
    public static List<ItemStack> STOREDITEMS = new ArrayList<>();
    public static List<ItemStack> STOREDRANDOM = new ArrayList<>();
    public static List<ItemStack> STOREDSHULKERS = new ArrayList<>();

    public static void reloadItemList() {
        STOREDITEMS = getItemList();
        STOREDRANDOM = new ArrayList<>(STOREDITEMS);
        STOREDRANDOM.removeIf(item -> isStorage(item) || item.getType().isAir());
        STOREDSHULKERS = new ArrayList<>(STOREDITEMS);
        STOREDSHULKERS.removeIf(item -> !isStorage(item) || item.getType().isAir());
    }

    public static int getFileIndex(File file) {
        return Integer.parseInt(file.getName().replaceAll("shelf_","").replaceAll(".yml",""));
    }

    public static void delete(Player player, int index) {
        try {
            ItemStack item = STOREDITEMS.get(index);
            STOREDITEMS.remove(index);
            saveItemList();
            String name = item.getType().name().toLowerCase().trim();
            String display = item.getItemMeta().getDisplayName().trim();
            player.sendMessage("§dDeleted item " + name + " [" + display + "§d]");
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            // empty
        }
    }

    public static void deleteUnusedFiles() {
        // gets the total pages, or the number of used files
        final int totalPages = (int) Math.ceil(STOREDITEMS.size() / 100.0);
        // list of the saved files
        File[] files = PARENTFOLDER.listFiles();
        // file list cannot be null
        if (files == null) return;
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < files.length) {
                    try {
                        // if the file index is greater than the current file index, the file is known as unused and is deleted
                        for (int j = 0; j < 500; j ++) {
                            File file = files[i];
                            int index = getFileIndex(file) - 1;
                            if (index > totalPages) {
                                file.delete();
                            }
                            i++;
                        }
                    } catch (NullPointerException | IndexOutOfBoundsException exception) {
                        i = files.length;
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(UniVault.getInstance(),0,1);
    }

    public static void saveItemList() {
        new BukkitRunnable() {
            final int totalPages = (int) Math.ceil(STOREDITEMS.size() / 100.0);
            int i = 0;
            @Override
            public void run() {
                // loops through the main list STOREDITEMS and saves it into seperate files
                if (i < totalPages) {
                    // makes sure each file won't contain too much data by breaking down the list
                    List<ItemStack> newItemArray = new ArrayList<>();
                    for (int j = (i * 100); j < (i * 100) + 100; j ++) {
                        try {
                            ItemStack item = STOREDITEMS.get(j);
                            if (item.getType().equals(Material.AIR)){
                                getServer().getLogger().warning("Empty item detected in archive! Deleting...");
                            } else newItemArray.add(item);
                        } catch (IndexOutOfBoundsException | NullPointerException exception) {
                            // empty
                        }
                    }
                    // adds the broken down list to the file
                    File file = new File(PARENTFOLDER,"shelf_" + i + ".yml");
                    FileConfiguration data = YamlConfiguration.loadConfiguration(file);
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException exception) {
                            getServer().getLogger().warning(exception.toString());
                        }
                    }
                    data.set("shelf.contents",newItemArray);
                    // saves the file
                    try {
                        data.save(file);
                    } catch (IOException exception) {
                        getServer().getLogger().warning(exception.toString());
                    }
                    i ++;
                } else {
                    // deletes the unused files and refreshes the three main item lists
                    deleteUnusedFiles();
                    reloadItemList();
                    this.cancel();
                }
            }
        }.runTaskTimer(UniVault.getInstance(),0,1);
    }

    public static List<ItemStack> getFileItems(File file) {
        // gets the item list in a saved file
        List<ItemStack> items = new ArrayList<>();
        if (file == null) return items;
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        return (List<ItemStack>) data.get("shelf.contents");
    }

    public static List<ItemStack> getItemList() {
        /*
        loops though each file in the file list and adds all of their items to one big list.
        this list is then saved as STOREDITEMS, which is then filtered down to STOREDSHULKERS
        and STOREDRANDOM upon calling the function #reloadItemList();
        */
        List<ItemStack> items = new ArrayList<>();
        File[] files = PARENTFOLDER.listFiles();
        if (files == null) return items;
        for (File file : files) {
            items.addAll(getFileItems(file));
        }
        return items;
    }

    public static void submitItemList(ItemStack[] contents) {
        // submits a list of items to the main big list, then calls the function
        // #saveItemList(); to save the big list into separate files

        // THIS IS THE SECOND FILTER CHECK, OUT OF TWO TOTAL
        List<ItemStack> items = new ArrayList<>(Arrays.asList(filterForSubmission(contents)));
        STOREDITEMS.addAll(items);
        saveItemList();
    }

    public static ItemStack[] filterForSubmission(ItemStack[] contents) {
        List<ItemStack> items = new ArrayList<>(Arrays.asList(contents));
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack item : items) {
            try {
                // submission items cannot have the same display names as the menu sprites
                // submission items cannot duplicate with the items in the big list
                item.setAmount(1);
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(new NamespacedKey(UniVault.getInstance(),"FROM"), PersistentDataType.STRING,Config.getWaterMark());
                item.setItemMeta(meta);
                if (NBT.submittable(item) && !result.contains(item)) result.add(item);
            } catch (NullPointerException exception) {
                // empty
            }
        }
        return result.toArray(new ItemStack[0]);
    }

    public static boolean isStorage(ItemStack item) {
        String name = item.getType().name().toUpperCase();
        return name.contains("CHEST") || name.contains("SHULKER") || name.contains("BARREL");
    }

    public static String getDisplay(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }

    public static void openItems(Player player, int index) {
        // creates an inventory for the page
        Inventory menu = Bukkit.createInventory(player,54,"§7>> §aShelf:ALL §e" + (index + 1));
        ItemStack a = Items.AIR;
        ItemStack x = Items.setBlank(new ItemStack(Material.LIME_STAINED_GLASS_PANE));
        menu.setContents(new ItemStack[] {
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                Items.SEARCH,x,x,Items.BACK,x,Items.NEXT,x,x,Items.SUBMISSION
        });
        // add the contents for all item types
        for (int i = (index * 45); i < (index * 45) + 45; i ++) {
            try {
                menu.setItem(menu.firstEmpty(),STOREDITEMS.get(i));
            } catch (IndexOutOfBoundsException | NullPointerException exception) {
                // empty
            }
        }
        // open the vault for the player
        player.openInventory(menu);
    }

    public static void openShulker(Player player, int index) {
        // creates an inventory for the page
        Inventory menu = Bukkit.createInventory(player,54,"§7>> §aShelf:SHULKER §e" + (index + 1));
        ItemStack a = Items.AIR;
        ItemStack x = Items.setBlank(new ItemStack(Material.LIME_STAINED_GLASS_PANE));
        menu.setContents(new ItemStack[] {
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                Items.SEARCH,x,x,Items.BACK,x,Items.NEXT,x,x,Items.SUBMISSION
        });
        // add the contents for all item types
        for (int i = (index * 45); i < (index * 45) + 45; i ++) {
            try {
                menu.setItem(menu.firstEmpty(),STOREDSHULKERS.get(i));
            } catch (IndexOutOfBoundsException | NullPointerException exception) {
                // empty
            }
        }
        // open the vault for the player
        player.openInventory(menu);
    }

    public static void openRandom(Player player, int index) {
        // creates an inventory for the page
        Inventory menu = Bukkit.createInventory(player,54,"§7>> §aShelf:RANDOM §e" + (index + 1));
        ItemStack a = Items.AIR;
        ItemStack x = Items.setBlank(new ItemStack(Material.LIME_STAINED_GLASS_PANE));
        menu.setContents(new ItemStack[] {
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                Items.SEARCH,x,x,Items.BACK,x,Items.NEXT,x,x,Items.SUBMISSION
        });
        // add the contents for all item types
        for (int i = (index * 45); i < (index * 45) + 45; i ++) {
            try {
                menu.setItem(menu.firstEmpty(),STOREDRANDOM.get(i));
            } catch (IndexOutOfBoundsException | NullPointerException exception) {
                // empty
            }
        }
        // open the vault for the player
        player.openInventory(menu);
    }

    public static void openSubmit(Player player) {
        Inventory menu = Bukkit.createInventory(player,54,"§7>> §a§oSubmission");
        ItemStack g = Items.setBlank(new ItemStack(Material.LIME_STAINED_GLASS_PANE));
        ItemStack x = Items.setBlank(new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        ItemStack a = Items.AIR;
        ItemStack r = new ItemStack(getRanItem().getType());
        ItemMeta rMeta = r.getItemMeta();
        if (rMeta == null) rMeta = Items.NULL.getItemMeta();
        rMeta.setDisplayName("§eRANDOM ITEM");
        r.setItemMeta(rMeta);
        ItemStack[] contents = {
                x,x,x,x,x,x,x,x,x,
                x,g,g,g,g,g,g,g,x,
                x,g,a,a,a,a,a,g,x,
                x,g,g,g,g,g,g,g,x,
                x,x,x,x,x,x,x,x,x,
                Items.SHULKERITEMS,Items.RANDOMITEMS,Items.ALLITEMS,x,r,x,x,Items.SEARCH,Items.SUBMIT,
        };
        menu.setContents(contents);
        player.openInventory(menu);
    }

    public static ItemStack getRanItem() {
        ItemStack item = Items.NULL;
        try {
           item = STOREDITEMS.get(ran(STOREDITEMS.size(),false));
        } catch (NullPointerException | IndexOutOfBoundsException | IllegalArgumentException exception) {
            // empty
        }
        return item;
    }

    public static int ran(int max, boolean ceil) {
        double x = Math.random() * max;
        if (ceil) return (int) Math.ceil(x);
        else return (int) Math.floor(x);
    }
}