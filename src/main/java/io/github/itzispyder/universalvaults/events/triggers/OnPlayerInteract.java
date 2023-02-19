package io.github.itzispyder.universalvaults.events.triggers;

import io.github.itzispyder.universalvaults.handpicked.HandPicked;
import io.github.itzispyder.universalvaults.handpicked.HandPickedManager;
import io.github.itzispyder.universalvaults.handpicked.ViewMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnPlayerInteract implements Listener {


    @EventHandler
    public static void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        try {
            switch (e.getAction()) {
                case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> {
                    Block b = e.getClickedBlock();
                    if (HandPickedManager.isHandPickedChest(b)) {
                        e.setCancelled(true);
                        String name = HandPickedManager.getHandPickedId(b);
                        HandPicked hp = HandPickedManager.load(name);
                        ViewMode mode = ViewMode.PREVIEW;
                        if (p.hasPermission("univault.handpicked") && p.isSneaking()) mode = ViewMode.EDITING;
                        p.openInventory(hp.asInv(mode));
                    }
                }
            }
        } catch (Exception ex) { }
    }
}
