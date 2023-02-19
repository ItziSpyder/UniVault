package io.github.itzispyder.universalvaults.archive;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.data.Config;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static io.github.itzispyder.universalvaults.data.Config.Archive.max_nbt_length;
import static io.github.itzispyder.universalvaults.data.Config.Archive.min_nbt_length;

/**
 * Represents an archived item.
 */
public class ArchivedStack extends ItemStack {

    private ItemStack item;
    private ArchivedStackType stackType;
    private ArchivedStackData stackData;

    /**
     * Create an archived item.
     * @param item the item to archive
     */
    public ArchivedStack(ItemStack item) {
        super(item);
        this.item = item;
        if (isStorage()) this.stackType = ArchivedStackType.SHULKER;
        else this.stackType = ArchivedStackType.RANDOM;
        this.stackData = new ArchivedStackData(item);
    }

    /**
     * Returns the item that this archived item represents.
     * @return the item
     */
    public ItemStack toItemStack() {
        return this.item;
    }

    /**
     * Boxes an item.
     * @return the boxed item.
     */
    @Deprecated
    public ItemStack box() {
        if (this.isArchived()) return this.item;
        ItemStack box = new ItemStack(Material.WHITE_SHULKER_BOX);
        if (this.stackType == ArchivedStackType.SHULKER) box.setType(Material.LIGHT_GRAY_SHULKER_BOX);
        BlockStateMeta state = (BlockStateMeta) box.getItemMeta();
        ShulkerBox shulker = (ShulkerBox) state.getBlockState();
        shulker.getInventory().setItem(0,this.item);
        shulker.update();
        state.setBlockState(shulker);
        state.setDisplayName(Main.starter + "§cARCHIVED-ITEM");
        box.setItemMeta(state);
        return box;
    }

    /**
     * Unboxes an archived item.
     * @return the unboxed item
     */
    @Deprecated
    public ItemStack unbox() {
        if (!this.isArchived()) return this.item;
        BlockStateMeta state = (BlockStateMeta) this.item.getItemMeta();
        ShulkerBox shulker = (ShulkerBox) state.getBlockState();
        return shulker.getInventory().getItem(0);
    }

    /**
     * Watermarks and item
     * @return the watermarked item
     */
    public ItemStack watermark() {
        ItemMeta meta = this.item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Main.getInstance(),"FROM");
        data.set(key,PersistentDataType.STRING,Config.Archive.watermark);
        this.item.setItemMeta(meta);
        return this.item;
    }

    /**
     * Checks if the archived item is a storage block/item.
     * @return is storage
     */
    public boolean isStorage() {
        String name = this.item.getType().name();
        return name.contains("SHULKER_BOX") ||
                name.contains("CHEST") ||
                name.contains("BARREL");
    }

    /**
     * If the item is already an archived item.
     * @return is archived
     */
    public boolean isArchived() {
        return this.getDisplay().equalsIgnoreCase(Main.starter + "§cARCHIVED-ITEM");
    }

    /**
     * Returns the display name of the archived item.
     * @return the display name
     */
    public static String getDisplay(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) return meta.getDisplayName();
        else return item.getType().name().toLowerCase();
    }

    public String getDisplay() {
        return getDisplay(this);
    }

    /**
     * Returns if the item is passable or valid for submission
     * @return is passable
     */
    public static boolean isPassable(ItemStack item) {
        int length = item.toString().length();
        return length <= max_nbt_length && length >= min_nbt_length;
    }

    public boolean isPassable() {
        return isPassable(item);
    }

    public ArchivedStackType getStackType() {
        return stackType;
    }

    public ArchivedStackData getStackData() {
        return stackData;
    }
}

