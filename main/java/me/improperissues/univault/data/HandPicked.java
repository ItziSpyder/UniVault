package me.improperissues.univault.data;

import me.improperissues.univault.UniVault;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class HandPicked {

    public static void createItem(Player player, String name) {
        if (!player.getGameMode().equals(GameMode.CREATIVE) || !player.isOp()) {
            player.sendMessage(UniVault.STARTER + "§4You do not have access to this!");
            return;
        }
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c#HANDPICKED§7:" + name);
        meta.setLore(new ArrayList<>(Arrays.asList(
                "§7- This is a custom block, place",
                "§7  this down to spawn a hand-picked",
                "§7  chest storage. Break it to",
                "§7  delete it!",
                "§7- Upon shift-opening this block,",
                "§7  You will be able to edit its",
                "§7  contents if oped!"
        )));
        item.setItemMeta(meta);
        player.getInventory().setItemInMainHand(item);
        player.sendMessage(UniVault.STARTER + "§dGave one handpicked chest! \"" + name + "\"");
    }

    public static File PARENTFOLDER = new File("plugins/UniVault/handpicked");
    private String name;
    private Location location;
    private List<ItemStack> contents;

    public static HandPicked load(File file) {
        if (!file.exists()) return null;
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        String name = data.getString("handpicked.name");
        Location location = data.getLocation("handpicked.location");
        List<ItemStack> contents = (List<ItemStack>) data.get("handpicked.contents");
        return new HandPicked(name,location,contents);
    }

    public static File getFile(String name) {
        return new File(PARENTFOLDER,name + ".yml");
    }

    public static String getDisplay(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }

    public static List<String> getAllChests() {
        List<String> list = new ArrayList<>();
        File[] files = PARENTFOLDER.listFiles();
        if (files == null) return list;
        for (File file : files) {
            list.add(file.getName().replaceAll(".yml",""));
        }
        return list;
    }

    public static ItemStack[] filterForSubmission(ItemStack[] contents) {
        List<ItemStack> items = new ArrayList<>(Arrays.asList(contents));
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack item : items) {
            if (item != null && NBT.passable(item)) result.add(item);
        }
        return result.toArray(new ItemStack[0]);
    }

    public HandPicked(String name, Location location, List<ItemStack> contents) {
        this.name = name;
        this.location = location;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public List<ItemStack> getContents() {
        return contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setContents(List<ItemStack> contents) {
        this.contents = new ArrayList<>(Arrays.asList(filterForSubmission(contents.toArray(new ItemStack[0]))));
    }

    public File getFile() {
        return new File(PARENTFOLDER,this.name + ".yml");
    }

    public void save() {
        File file = new File(PARENTFOLDER,this.name + ".yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);
        data.set("handpicked.name",this.name);
        data.set("handpicked.location",this.location);
        data.set("handpicked.contents",this.contents);
        try {
            data.save(file);
        } catch (IOException exception) {
            getServer().getLogger().warning(exception.toString());
        }
    }

    public void delete() {
        File file = new File(PARENTFOLDER,this.name + ".yml");
        file.delete();
    }

    public void saveInventory(ItemStack[] contents) {
        setContents(new ArrayList<>(Arrays.asList(contents)));
        save();
    }

    public void createInventory(Player player) {
        Inventory menu = Bukkit.createInventory(player,54,"§c#HANDPICKED§7:" + this.name);
        menu.setContents(this.contents.toArray(new ItemStack[0]));
        player.openInventory(menu);
    }

    public void editInventory(Player player) {
        Inventory menu = Bukkit.createInventory(player,54,"§c#HANDPICKED-EDITING§7:" + this.name);
        menu.setContents(this.contents.toArray(new ItemStack[0]));
        player.openInventory(menu);
    }
}
