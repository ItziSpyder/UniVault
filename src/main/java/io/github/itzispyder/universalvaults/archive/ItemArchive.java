package io.github.itzispyder.universalvaults.archive;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.data.inventory.GuiFrames;
import io.github.itzispyder.universalvaults.data.inventory.InventoryPreset;
import io.github.itzispyder.universalvaults.exceptions.InvalidInventoryPresetException;
import io.github.itzispyder.universalvaults.exceptions.LargeNbtException;
import io.github.itzispyder.universalvaults.exceptions.archive.ArchiveFullException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents an item archive.
 */
public class ItemArchive implements Serializable, ConfigurationSerializable, Cloneable {

    private Set<ItemStack> contents;
    private final int id;

    /**
     * Construct a new item archive
     * @param id id value
     */
    public ItemArchive(int id) {
        this.id = id;
        this.contents = new HashSet<>();
    }

    /**
     * Construct a new item archive.
     */
    public ItemArchive() {
        this.id = 0;
        this.contents = new HashSet<>();
    }

    /**
     * Adds an item to the archive.
     * @param item item
     * @throws ArchiveFullException if the archive is already full
     */
    public void addItem(ItemStack item) throws ArchiveFullException {
        if (this.contents.size() >= 45)
            throw new ArchiveFullException("This archive is full! Max size: 45  Provided size: " + (this.contents.size() + 1));
        this.contents.add(item);
    }

    /**
     * Saves this archive.
     */
    public void save() {
        ArchiveManager.save(this);
    }

    /**
     * Returns this archive as an inventory
     * @return inventory
     * @throws InvalidInventoryPresetException if the preset is null
     */
    public Inventory asInv() throws InvalidInventoryPresetException {
        Inventory menu = Bukkit.createInventory(null,54, Main.starter + "§3Archive index.§b" + id);
        InventoryPreset preset = GuiFrames.PREVIEW;
        preset.addPresetTo(menu);
        for (ItemStack item : contents) {
            if (item == null) continue;
            ArchivedStack stack = new ArchivedStack(item);
            menu.setItem(menu.firstEmpty(),stack.unbox());
        }
        return menu;
    }

    public int getId() {
        return id;
    }

    public Set<ItemStack> getContents() {
        return contents;
    }

    /**
     * Sets the contents of the archive
     * @param contents the new set of contents
     * @throws ArchiveFullException if the contents size is too large
     */
    public void setContents(Set<ItemStack> contents) throws ArchiveFullException, LargeNbtException {
        if (contents.size() > 45)
            throw new ArchiveFullException("This archive is full! Max size: 45  Provided size: " + contents.size());
        if (contents.toString().length() > contents.size() * Config.Archive.max_nbt_length)
            if (contents.size() * Config.Archive.max_nbt_length > 0) throw new LargeNbtException();
        this.contents = contents;
    }

    @Override
    public ItemArchive clone() {
        try {
            return (ItemArchive) super.clone();
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
