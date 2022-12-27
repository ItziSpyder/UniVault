package me.improperissues.univault.events;

import me.improperissues.univault.UniVault;
import me.improperissues.univault.data.HandPicked;
import me.improperissues.univault.other.Sounds;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class HandPickedEvent implements Listener {

    @EventHandler
    public static void PlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        try {
            Block block = e.getClickedBlock();
            String name = block.getType().name();
            if (name.contains("CHEST") || name.contains("BARREL")) {
                if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
                Container container = (Container) block.getState();
                String title = container.getCustomName();
                if (title.contains("§c#HANDPICKED§7:")) {
                    e.setCancelled(true);
                    String hpName = title.split(":")[1];
                    HandPicked hp = HandPicked.load(HandPicked.getFile(hpName));
                    if (hp == null) {
                        Sounds.error(p);
                        p.sendMessage(UniVault.STARTER + "§4Chest was broken at another location!");
                        return;
                    }
                    Sounds.openVault(p);
                    if (p.isOp() && p.isSneaking()) {
                        hp.editInventory(p);
                        p.sendMessage(UniVault.STARTER + "§dEditing contents...");
                        return;
                    }
                    hp.createInventory(p);
                    return;
                } else if (title.contains("§c#SUBMISSION")) {
                    e.setCancelled(true);
                    p.chat("/submit");
                    return;
                } else if (title.contains("§c#SHULKER")) {
                    e.setCancelled(true);
                    p.chat("/review shulker " + Integer.parseInt(title.split(":")[1]));
                    return;
                } else if (title.contains("§c#RANDOM")) {
                    e.setCancelled(true);
                    p.chat("/review random " + Integer.parseInt(title.split(":")[1]));
                    return;
                }
            }
        } catch (NullPointerException | IllegalArgumentException | ClassCastException exception) {
            // empty
        }
    }

    @EventHandler
    public static void InventoryCloseEvent(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        String title = e.getView().getTitle();
        Inventory inv = e.getInventory();

        try {
            if (title.contains("§c#HANDPICKED-EDITING§7:")) {
                Sounds.closeVault(p);
                String hpName = title.split(":")[1];
                HandPicked hp = HandPicked.load(HandPicked.getFile(hpName));
                if (!p.isOp()) {
                    p.sendMessage(UniVault.STARTER + "§4You do not have not access to this!");
                    return;
                }
                hp.saveInventory(inv.getContents());
                p.sendMessage(UniVault.STARTER + "§dSaved contents!");
            }
        } catch (NullPointerException | IllegalArgumentException | ClassCastException exception) {
            // empty
        }
    }

    @EventHandler
    public static void BlockPlaceEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        String name = block.getType().name();

        try {
            if (name.contains("CHEST")) {
                Container container = (Container) block.getState();
                String title = container.getCustomName();
                if (title.contains("§c#HANDPICKED§7:")) {
                    String hpName = title.split(":")[1];
                    HandPicked hp = HandPicked.load(HandPicked.getFile(hpName));
                    hp = (hp != null ? hp : new HandPicked(hpName,block.getLocation(),new ArrayList<>()));
                    hp.save();
                }
            }
        } catch (NullPointerException | IllegalArgumentException | ClassCastException exception) {
            // empty
        }
    }
}
