package me.improperissues.univault.events;

import me.improperissues.univault.UniVault;
import me.improperissues.univault.data.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEntityEvent implements Listener {

    @EventHandler
    public static void PlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (Config.getJoinClear()) {
            p.getInventory().clear();
            p.updateInventory();
            p.sendMessage(UniVault.STARTER + "dWe've been configured to clear your inventory on join!");
        }
    }
}
