package io.github.itzispyder.universalvaults.data.inventory;

import io.github.itzispyder.universalvaults.exceptions.InvalidInventoryPresetException;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the frame of an empty inventory
 */
public class InventoryPreset {

    private InventoryContents contents;

    /**
     * Construct a new inventory frame
     */
    public InventoryPreset() {
        contents = new InventoryContents(new ItemStack[0]);
    }

    /**
     * Set this preset to an inventory
     * @param inventory the inventory
     * @throws InvalidInventoryPresetException if the preset is too large or is null
     */
    public void addPresetTo(Inventory inventory) throws InvalidInventoryPresetException {
        if (this.contents == null || this.contents.size() > 54)
            throw new InvalidInventoryPresetException();
        this.contents.getContents().forEach(inventory::setItem);
    }

    /**
     * Creates a new inventory with this preset applied
     * @param title title of the inventory
     * @throws InvalidInventoryPresetException if the preset is not valid
     */
    public Inventory createInv(String title) throws InvalidInventoryPresetException {
        int size = (int) Math.ceil(this.contents.size() / 9.0) * 9;
        size = Math.min(size, 54);
        return Bukkit.createInventory(null,size,title);
    }

    public InventoryContents getContents() {
        return contents;
    }

    /**
     * Sets contents with hash map
     * @param contents item hash map
     */
    public void setContents(InventoryContents contents) {
        this.contents = contents;
    }

    /**
     * Sets contents with ItemStack array
     * @param contents item array
     */
    public void setContents(ItemStack[] contents) {
        this.contents = new InventoryContents(contents);
    }
}
