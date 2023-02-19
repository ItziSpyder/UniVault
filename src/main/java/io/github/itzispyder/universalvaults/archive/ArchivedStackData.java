package io.github.itzispyder.universalvaults.archive;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data of an archived stack
 */
public class ArchivedStackData {

    private String displayName;
    private List<String> lore;
    private Material type;

    /**
     * Constructs a new archived stack data
     * @param item item
     */
    public ArchivedStackData(ItemStack item) {
        this.type = item.getType();
        if (!item.hasItemMeta()) {
            this.displayName = "";
            this.lore = new ArrayList<>();
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) this.displayName = meta.getDisplayName();
        else this.displayName = "";
        if (meta.hasLore()) this.lore = meta.getLore();
        else this.lore = new ArrayList<>();
    }

    @Override
    public String toString() {
        return type.name() + displayName + lore;
    }
}
