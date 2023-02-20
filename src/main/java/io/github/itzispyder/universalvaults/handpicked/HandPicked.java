package io.github.itzispyder.universalvaults.handpicked;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.archive.ItemArchive;
import io.github.itzispyder.universalvaults.archive.UniVaultValidation;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.data.inventory.GuiFrames;
import io.github.itzispyder.universalvaults.data.inventory.InventoryPreset;
import io.github.itzispyder.universalvaults.exceptions.InvalidInventoryPresetException;
import io.github.itzispyder.universalvaults.exceptions.archive.InvalidArchiveNameException;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * Represents a handpicked archive kit
 */
public class HandPicked extends ItemArchive {

    private final String name;
    private final File file;

    /**
     * Construct a handpicked kit
     * @param name the name of the handpicked archive
     * @throws InvalidArchiveNameException if the name is an invalid archive name
     */
    public HandPicked(String name) throws InvalidArchiveNameException {
        super();
        if (!UniVaultValidation.validFileName(name)) throw new InvalidArchiveNameException();
        this.name = name;
        File file = new File(Config.dataFolder,"handpicked/" + name + ".handpicked");
        this.file = file;
    }

    @Override
    public void save() {
        HandPickedManager.save(this);
    }

    /**
     * Deletes the current handpicked archive along with its files
     */
    public void delete() {
        this.file.delete();
    }

    /**
     * Returns this archive as an inventory
     * @return inventory
     * @throws InvalidInventoryPresetException if the preset is null
     */
    @Override
    public Inventory asInv() throws InvalidInventoryPresetException {
        return asInv(ViewMode.PREVIEW);
    }

    /**
     * Returns this archive as an inventory
     * @param mode view mode
     * @return inventory
     * @throws InvalidInventoryPresetException if the preset is null
     */
    public Inventory asInv(ViewMode mode) throws InvalidInventoryPresetException {
        Inventory menu = Bukkit.createInventory(null,54, Main.starter + "§3Handpicked preview.§b" + name);
        menu = mode.equals(ViewMode.PREVIEW) ? menu : Bukkit.createInventory(null,54, Main.starter + "§3Handpicked editing.§b" + name);
        InventoryPreset preset = GuiFrames.HAND_PICKED;
        preset.addPresetTo(menu);
        for (ItemStack item : super.getContents()) {
            if (item == null) continue;
            ArchivedStack stack = new ArchivedStack(item);
            menu.setItem(menu.firstEmpty(),stack.unbox());
        }
        return menu;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
