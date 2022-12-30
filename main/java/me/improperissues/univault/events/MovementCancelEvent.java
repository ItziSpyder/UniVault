package me.improperissues.univault.events;

import me.improperissues.univault.UniVault;
import me.improperissues.univault.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;

public class MovementCancelEvent implements Listener {

    static HashMap<String,Long> CANCEL_MOVEMENT = new HashMap<>();
    static HashMap<String,Location> MOVEMENT_POSITIONS = new HashMap<>();
    static HashMap<String,Integer> WARNING_COUNT = new HashMap<>();

    @EventHandler
    public static void PlayerMoveEvent(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (CANCEL_MOVEMENT.containsKey(p.getName()) && CANCEL_MOVEMENT.get(p.getName()) > System.currentTimeMillis()) e.setCancelled(true);
        Location prev = MOVEMENT_POSITIONS.get(p.getName());
        MOVEMENT_POSITIONS.put(p.getName(),p.getLocation());
        if (prev != null && p.getLocation().distanceSquared(prev) > Config.getMaxPlayerSpeed() && Config.getMaxPlayerSpeed() > 0) {
            e.setCancelled(true);
            p.teleport(prev);
            if (WARNING_COUNT.get(p.getName()) != null) WARNING_COUNT.put(p.getName(),WARNING_COUNT.get(p.getName()) + 1); else WARNING_COUNT.put(p.getName(),1);
            CANCEL_MOVEMENT.put(p.getName(),System.currentTimeMillis() + 250);
        }
    }

    public static void clearWarnings() {
        try {
            WARNING_COUNT.forEach((player,integer) -> {
                if (integer >= 3) {
                    Player p = Bukkit.getPlayer(player);
                    if (p != null) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"kick " + p.getName()
                            + " " + UniVault.STARTER + "cYou were moving incorrectly!\nPlease do not abuse or exploit movement glitches!"
                            + "\n\n§7Believe this was in error? Please visit §b§nhttps://github.com/ItziSpyder/UniVault");
                }
                WARNING_COUNT.remove(player);
            });
        } catch (Exception exception) {
            // empty
        }
    }
}
