package io.github.itzispyder.universalvaults.events.triggers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class OnInventoryClose implements Listener {

    @EventHandler
    public static void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
    }
}
