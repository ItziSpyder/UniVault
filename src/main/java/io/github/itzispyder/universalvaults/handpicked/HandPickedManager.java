package io.github.itzispyder.universalvaults.handpicked;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.archive.UniVaultValidation;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.exceptions.archive.ArchiveInvalidException;
import io.github.itzispyder.universalvaults.exceptions.archive.InvalidArchiveNameException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handpicked archives manager
 */
public abstract class HandPickedManager {

    /**
     * Returns a list of the names of the current archives
     * @return a list
     */
    public static List<String> listValues() {
        List<String> list = new ArrayList<>();
        File parent = new File(Config.dataFolder,"handpicked");
        File[] children = parent.listFiles();
        if (children == null) return list;
        for (File file : children) list.add(file.getName().split("\\.")[0]);
        return list;
    }

    /**
     * Generates a placeable handpicked archive as a physical item
     * @param name the archive name
     * @return the generated item
     * @throws InvalidArchiveNameException if the archive name is invalid
     */
    public static ItemStack generateItem(String name) throws InvalidArchiveNameException {
        HandPicked hp = new HandPicked(name);
        ItemStack item = new ItemStack(Material.CHEST);
        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        Chest chest = (Chest) meta.getBlockState();
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"HAND_PICKED");
        PersistentDataContainer pdc = chest.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING,hp.getName());
        chest.update();
        meta.setBlockState(chest);
        meta.setDisplayName(Main.starter + "§3Handpicked id.§b" + name);
        meta.setLore(List.of("§7-Placeable handpicked kit"));
        meta.addEnchant(Enchantment.LUCK,69,true);
        for (ItemFlag flag : ItemFlag.values()) meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Save a handpicked kit
     * Will save to the specified path.
     * @param hp handpicked archive
     */
    public static void save(HandPicked hp) {
        try {
            File file = hp.getFile();
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            BukkitObjectOutputStream boos = new BukkitObjectOutputStream(oos);
            boos.writeObject(hp);
            boos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads a handpicked archive from the specified
     * file path
     * @param file file path
     * @throws ArchiveInvalidException if the file path is invalid
     * @return the handpicked archive
     */
    public static HandPicked load(File file) throws ArchiveInvalidException {
        if (!file.exists()) throw new ArchiveInvalidException("This archive does not exist!");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(ois);
            HandPicked hp = (HandPicked) bois.readObject();
            bois.close();
            return hp;
        } catch (Exception ex) {
            throw new ArchiveInvalidException("'" + file.getPath() + "' is an invalid archive!");
        }
    }

    /**
     * Loads a handpicked archive from the specified name
     * @param name handpicked archive's name
     * @throws ArchiveInvalidException if there is no archive with the given name
     * @throws InvalidArchiveNameException if the name is invalid
     * @return the handpicked archive
     */
    public static HandPicked load(String name) throws ArchiveInvalidException, InvalidArchiveNameException {
        if (!UniVaultValidation.validFileName(name)) throw new InvalidArchiveNameException();
        File file = new File(Config.dataFolder,"handpicked/" + name + ".handpicked");
        if (!file.exists()) return new HandPicked(name);
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(ois);
            HandPicked hp = (HandPicked) bois.readObject();
            bois.close();
            return hp;
        } catch (Exception ex) {
            throw new ArchiveInvalidException("'" + file.getPath() + "' is an invalid archive!");
        }
    }

    /**
     * Checks if a block is a handpicked chest
     * @param block block to check
     * @return is handpicked chest
     */
    public static boolean isHandPickedChest(Block block) {
        return getHandPickedId(block) != null;
    }

    /**
     * Gets the block handpicked id
     * Returns null if block is null
     * Returns null if the block is not a chest
     * Returns null if the block is not a handpicked archive
     * @param block block to check
     * @return handpicked id
     */
    public static String getHandPickedId(Block block) {
        if (block == null) return null;
        if (!block.getType().equals(Material.CHEST)) return null;
        Chest chest = (Chest) block.getState();
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"HAND_PICKED");
        PersistentDataContainer pdc = chest.getPersistentDataContainer();
        return pdc.get(key,PersistentDataType.STRING);
    }
}
