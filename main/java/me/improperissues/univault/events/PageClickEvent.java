package me.improperissues.univault.events;

import me.improperissues.univault.data.Config;
import me.improperissues.univault.data.Items;
import me.improperissues.univault.data.Page;
import me.improperissues.univault.other.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PageClickEvent implements Listener {

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();

        try {
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String display = meta.getDisplayName();

            if (title.contains("§7>> §aPage §e")) {
                // cancel the event if conditions aren't met
                if (inv.getType().equals(InventoryType.PLAYER)) {
                    if (!p.isOp() && !Config.getNonopEdit()) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }

                // register the event if the conditions are met
                int currentPage = getCurrentPage(title);
                Page page = Page.load(currentPage);
                if (display.contains("§fNext")) {
                    currentPage ++;
                    Page next = Page.load(currentPage);
                    next.createInventory(p);
                    Sounds.turnPage(p);
                } else if (display.contains("§fBack")) {
                    currentPage --;
                    Page back = Page.load(currentPage);
                    back.createInventory(p);
                    Sounds.turnPage(p);
                }  else if (display.contains("§cNULL")) {
                    e.setCurrentItem(Items.AIR);
                } else {
                    if (!p.isOp() && !Config.getNonopEdit()) {
                        e.setCancelled(true);
                        return;
                    }
                    page.setContents(inv.getContents());
                    page.save();
                }

            }

        } catch (NullPointerException exception) {
            // empty
        }
    }

    @EventHandler
    public static void InventoryCloseEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        String title = e.getView().getTitle();

        try {
            if (inv.getType().equals(InventoryType.PLAYER)) {
                return;
            }
            if (title.contains("§7>> §aPage §e")) {
                int index = getCurrentPage(title);
                Page page = Page.load(index);
                page.setContents(inv.getContents());
                page.save();
            }
        } catch (NullPointerException exception) {
            // empty
        }
    }

    public static int getCurrentPage(String invTitle) {
        return Integer.parseInt(invTitle.substring(("§7>> §aPage §e").length()));
    }
}
