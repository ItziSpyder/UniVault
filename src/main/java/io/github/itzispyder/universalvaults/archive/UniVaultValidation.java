package io.github.itzispyder.universalvaults.archive;

import org.bukkit.inventory.ItemStack;

/**
 * Validate items
 */
public abstract class UniVaultValidation {

    /**
     * Checks if an item is within the configured nbt limits
     * @param item item to check
     * @return is within nbt limits
     */
    public static boolean isWithinNBT(ItemStack item) {
        return ArchivedStack.isPassable(item);
    }

    /**
     * Checks if a name is valid for being file name
     * @param name the name to check
     * @return file name is valid
     */
    public static boolean validFileName(String name) {
        String validName = name.replaceAll("[a-z\\-\\_]","");
        return validName.trim().length() == 0;
    }
}
