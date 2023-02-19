package io.github.itzispyder.universalvaults.archive;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.exceptions.archive.ArchiveFullException;
import io.github.itzispyder.universalvaults.exceptions.archive.ArchiveInvalidException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Loads and saves item archives
 */
public abstract class ArchiveManager {

    /**
     * Loads all the file items at once
     * @return all the items
     */
    public static Set<ItemStack> loadAll() {
        Set<ItemStack> items = new HashSet<>();
        File[] files = new File(Config.dataFolder,"archives").listFiles();
        if (files == null) return items;
        for (File file : files) {
            if (file == null || !file.exists()) continue;
            try {
                ItemArchive ia = load(file);
                if (ia == null) continue;
                items.addAll(ia.getContents());
            } catch (ArchiveInvalidException ex) {
                ex.printStackTrace();
            }
        }
        return items;
    }

    /**
     * Loads all the file items at once
     * @return all the items
     */
    public static void archiveAll() {
        List<ItemStack> items = new ArrayList<>(Main.is.all);
        int predicted = items.size() / 45;
        predicted = items.size() % 45 == 0 ? predicted : predicted + 1;
        for (int i = 0; i < predicted; i++) {
            ItemArchive ia = new ItemArchive(i);
            for (int x = (i * 45); x < (i * 45) + 45; x++) {
                try {
                    ia.addItem(items.get(x));
                } catch (ArchiveFullException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    break;
                }
            }
            ia.save(); // save the file
        }
    }

    /**
     * Save an item archive.
     * Will save to the archive folder.
     * @param archive item archive
     */
    public static void save(ItemArchive archive) {
        try {
            File file = new File(Config.dataFolder,"archives/archive-" + archive.getId() + ".archive");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            BukkitObjectOutputStream boos = new BukkitObjectOutputStream(oos);
            boos.writeObject(archive);
            boos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load and item archive.
     * @param file archive file
     * @return item archive
     * @throws ArchiveInvalidException if the archive file is invalid.
     */
    public static ItemArchive load(File file) throws ArchiveInvalidException {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(ois);
            ItemArchive ia = (ItemArchive) bois.readObject();
            bois.close();
            return ia;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ArchiveInvalidException("'" + file.getPath() + "' is an invalid archive!");
        }
    }

    /**
     * Returns an ItemArchive based on the index given.
     * Loaded archives will temporarily save for faster loading
     * @param index archive index
     * @return the archive with the matching index
     * @throws ArchiveInvalidException if the archive is invalid
     */
    public static ItemArchive load(int index) throws ArchiveInvalidException {
        if (index < 0) throw new ArchiveInvalidException("index cannot be negative!"); // if the index is negative
        File file = new File(Config.dataFolder,"archives/archive-" + index + ".archive");
        if (!file.exists()) throw new ArchiveInvalidException("This archive does not exist!"); // if the archive does not exist
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            BukkitObjectInputStream bois = new BukkitObjectInputStream(ois);
            ItemArchive archive = (ItemArchive) bois.readObject();
            bois.close();
            return archive;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ArchiveInvalidException("'" + file.getPath() + "' is an invalid archive!"); // if the archive is invalid
        }
    }
}
