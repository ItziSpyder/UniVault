package me.improperissues.univault.events;

import me.improperissues.univault.data.Config;
import me.improperissues.univault.data.Items;
import me.improperissues.univault.data.Shelf;
import me.improperissues.univault.other.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class ShelfClickEvent implements Listener {

    public static HashMap<String,Long> cooldown = new HashMap<>();

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();

        try {
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String display = meta.getDisplayName();

            if (title.contains("§7>> §a§oSubmission")) {
                // cancel the event if conditions aren't met
                if (inv.getType().equals(InventoryType.PLAYER)) {
                    if (!p.isOp() && !Config.getNonopEdit()) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }
                if (display.equals(" ")) {
                    e.setCancelled(true);
                    return;
                }

                // register the event if the conditions are met
                if (display.contains(getDisplay(Items.SUBMIT))) {
                    e.setCancelled(true);
                    // if action is on cooldown, cance
                    if (cooldown.containsKey(p.getName()) && cooldown.get(p.getName()) > System.currentTimeMillis())  {
                        int sec = (int) Math.ceil((cooldown.get(p.getName()) - System.currentTimeMillis()) / 1000.);
                        p.closeInventory();
                        p.sendMessage("§4This action is on cooldown for another " + sec + " seconds!");
                        return;
                    }
                    if (Shelf.filterForSubmission(inv.getContents()).length == 0) {
                        p.closeInventory();
                        p.sendMessage("§4Submission failed since non of the provided items are submittable!");
                        return;
                    }
                    cooldown.put(p.getName(),System.currentTimeMillis() + (1000L * Config.getCooldown()));
                    // item submission
                    p.sendMessage("§dSubmitting items...");
                    Shelf.submitItemList(inv.getContents());
                    p.closeInventory();
                    Sounds.closeVault(p);
                } else if (display.contains(getDisplay(Items.NULL))) {
                    e.setCurrentItem(Items.AIR);
                    e.setCancelled(true);
                } else if (display.contains("§eRANDOM ITEM")) {
                    e.setCancelled(true);
                    p.closeInventory();
                    p.getWorld().dropItemNaturally(p.getLocation(),Shelf.getRanItem());
                    Sounds.randomItem(p);
                }
            }
        } catch (NullPointerException exception) {
            // empty
        }
    }

    public static String getDisplay(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }
}
