package me.improperissues.univault.data;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Page {

    private int index;
    private List<ItemStack> contents;

    public static Page load(int index) {
        // makes sure the index is within bounds
        if (index < 1) index = 1;
        if (index > Config.getMaxPages()) index = Config.getMaxPages();
        // load the file
        File file = new File("plugins/UniVault/vaults/page_" + index + ".yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        // load contents and return class instance
        List<ItemStack> contents = (List<ItemStack>) data.get("page.contents");
        if (contents == null) contents = new ArrayList<>();
        return new Page(index,contents);
    }

    public Page(int index, List<ItemStack> contents) {
        this.index = index;
        this.contents = contents;
    }

    public Page(int index, ItemStack[] contents) {
        this.index = index;
        this.contents = new ArrayList<>(Arrays.asList(contents));
    }

    public static List<Integer> getExistingIndexes() {
        File[] files = new File("plugins/UniVault/vaults").listFiles();
        List<Integer> ints = new ArrayList<>();
        for (int i = 0; i < 500; i ++) {
            try {
                File file = files[i];
                ints.add(Integer.parseInt(file.getName().replaceAll("page_","").replaceAll(".yml","")));
            } catch (IndexOutOfBoundsException exception) {
                // empty
            }
        }
        return ints;
    }

    public int getIndex() {
        return index;
    }

    public List<ItemStack> getContents() {
        return contents;
    }

    public void setContents(List<ItemStack> contents) {
        this.contents = contents;
    }

    public void setContents(ItemStack[] contents) {
        this.contents = new ArrayList<>(Arrays.asList(contents));
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public File getFile() {
        return new File("plugins/UniVault/vaults/page_" + this.index + ".yml");
    }

    public boolean fileExists() {
        return getFile().exists();
    }

    public void save() {
        File file = getFile();
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        // if the file doesn't exist then create a new file
        if (file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                getServer().getLogger().warning(exception.toString());
            }
        }
        // contents of this class
        List<ItemStack> contents = this.contents.subList(0,45);
        List<ItemStack> filtered = new ArrayList<>();
        /*
        if the item doesn't meet the required conditions, it gets
        replaced by the custom NULL item. the null item is pretty
        useless, and disappears upon interacting with it in an
        open inventory.
        */
        for (int i = 0; i < 45; i ++) {
            try {
                ItemStack item = contents.get(i);
                if (NBT.passable(item)) filtered.add(item);
                else filtered.add(Items.NULL);
            } catch (NullPointerException | IndexOutOfBoundsException exception) {
                filtered.add(null);
            }
        }
        // saves the file with the data
        data.set("page.index",this.index);
        data.set("page.contents",filtered);
        try {
            data.save(file);
        } catch (IOException exception) {
            getServer().getLogger().warning(exception.toString());
        }
    }

    public boolean delete() {
        return getFile().delete();
    }

    public void createInventory(Player player) {
        // creates an inventory for the page
        Inventory menu = Bukkit.createInventory(player,54,"§7>> §aPage §e" + this.index);
        ItemStack x = Items.setBlank(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        ItemStack a = Items.AIR;
        // set the items first
        ItemStack[] contents = {
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                x,x,x,Items.BACK,x,Items.NEXT,x,x,x
        };
        // add the contents from this class
        menu.setContents(contents);
        for (int i = 0; i < 45; i ++) {
            try {
                ItemStack item = this.contents.get(i);
                menu.setItem(i,item);
            } catch (NullPointerException | IndexOutOfBoundsException exception) {
                menu.setItem(i,a);
            }
        }
        // open the vault for the player
        player.openInventory(menu);
    }
}
