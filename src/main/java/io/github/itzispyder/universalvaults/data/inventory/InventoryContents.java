package io.github.itzispyder.universalvaults.data.inventory;

import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the contents of an inventory
 */
public class InventoryContents {

    private HashMap<Integer,ItemStack> contents;
    private ItemStack[] array;

    /**
     * Constructs inventory contents from item array
     * @param contents item array
     */
    @ItziSpyder // (you found 5/10, congrats!)
    public InventoryContents(ItemStack[] contents) {
        this.contents = new HashMap<>();
        this.array = contents;
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) continue;
            this.contents.put(i,item);
        }
    }

    /**
     * Constructs inventory contents from inventory
     * @param inventory inventory
     */
    public InventoryContents(Inventory inventory) {
        this.contents = new HashMap<>();
        this.array = inventory.getContents();
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) continue;
            this.contents.put(i,item);
        }
    }

    /**
     * Return contents as item array
     * @return item array
     */
    public ItemStack[] asArray() {
        ItemStack[] items = new ItemStack[this.contents.size()];
        this.contents.forEach(((integer, itemStack) -> items[integer] = itemStack));
        return items;
    }

    /**
     * Return contents as item array with max capacity
     * @param max the maximum value/capacity
     * @return item array
     */
    public ItemStack[] asArray(int max) {
        ItemStack[] items = new ItemStack[max];
        for (int i = 0; i < max; i++)
            items[i] = this.contents.get(i);
        return items;
    }

    /**
     * Returns a sub array of the current array
     * @param begin begin index
     * @param end end index
     * @return the sub array
     */
    public ItemStack[] subArray(int begin, int end) {
        if (begin < 0 || end < 0) throw new IllegalArgumentException("values cannot be negative!");
        if (begin > end) throw new IllegalArgumentException("begin cannot be greater than end!");
        List<ItemStack> items = new ArrayList<>();
        this.contents.forEach(((integer, itemStack) -> {
            if (integer >= begin && integer <= end)
                items.add(itemStack);
        }));
        return items.toArray(new ItemStack[]{});
    }

    /**
     * Returns a sub array of the current array
     * @param indexes integers
     * @return the sub array
     */
    public ItemStack[] subArray(int... indexes) {
        List<ItemStack> items = new ArrayList<>();
        for (int index : indexes) {
            ItemStack item = this.contents.get(index);
            if (item == null) continue;
            items.add(item);
        }
        return items.toArray(new ItemStack[]{});
    }

    public HashMap<Integer, ItemStack> getContents() {
        return contents;
    }

    /**
     * Clears the contents
     */
    public void clear() {
        this.contents.clear();
        this.array = new ItemStack[this.array.length];
    }

    /**
     * Returns the amount of items stored
     * @return amount
     */
    public int size() {
        return array.length;
    }

    /**
     * Returns the actual amount of non-null items
     * @return amount
     */
    public int actualSize() {
        return this.contents.size();
    }
}
