package io.github.itzispyder.universalvaults.events.triggers;

import io.github.itzispyder.universalvaults.data.inventory.InventoryContents;
import io.github.itzispyder.universalvaults.exceptions.ArchiveException;
import io.github.itzispyder.universalvaults.exceptions.LargeNbtException;
import io.github.itzispyder.universalvaults.handpicked.HandPicked;
import io.github.itzispyder.universalvaults.handpicked.HandPickedManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.List;

import static io.github.itzispyder.universalvaults.Main.starter;

public class OnInventoryClose implements Listener {

    @EventHandler
    public static void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        String title = e.getView().getTitle();

        try {
            if (title.contains("§3Handpicked editing.§b")) functionHandPicked(e);
        } catch (Exception ex) { }
    }


    /**
     * Called when a player closes a handpicked archive inventory
     * @param e inventory close event
     */
    static void functionHandPicked(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        String title = e.getView().getTitle();
        try {
            String name = title.split("§3Handpicked editing.§b")[1];
            HandPicked hp = HandPickedManager.load(name);
            InventoryContents contents = new InventoryContents(inv);
            hp.setContents(new HashSet<>(List.of(contents.subArray(0,44))));
            hp.save();
            p.sendMessage(starter + "§bHandpicked archive §7" + name + " §bsaved!");
        }
        catch (ArchiveException ex) {

        }
        catch (LargeNbtException ex) {
            p.sendMessage(starter + "§cNBT too large!");
        }
    }
}
