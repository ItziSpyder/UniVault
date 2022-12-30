package me.improperissues.univault.events;

import me.improperissues.univault.UniVault;
import me.improperissues.univault.data.Archive;
import me.improperissues.univault.data.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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

    @EventHandler
    public static void BlockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();

        try {
            if ((isWithin(block.getLocation(),Archive.getArchiveCorner1(),Archive.getArchiveCorner2()) || (isWithin(block.getLocation(),Archive.getArchiveCorner3(),Archive.getArchiveCorner4())))) {
                if (Config.getArchiveBreakPlace()) return;
                e.setCancelled(true);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(UniVault.STARTER + "cPlease do not break anything in the archive!"));
            }
        } catch (NullPointerException exception) {}
    }

    @EventHandler
    public static void BlockPlaceEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();

        try {
            if ((isWithin(block.getLocation(),Archive.getArchiveCorner1(),Archive.getArchiveCorner2()) || (isWithin(block.getLocation(),Archive.getArchiveCorner3(),Archive.getArchiveCorner4())))) {
                if (Config.getArchiveBreakPlace()) return;
                e.setCancelled(true);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(UniVault.STARTER + "cPlease do not place anything in the archive!"));
            }
        } catch (NullPointerException exception) {}
    }

    public static boolean isWithin(Location location, Location corner1, Location corner2) {
        int minX = Math.min(corner1.getBlockX(),corner2.getBlockX());
        int maxX = Math.max(corner1.getBlockX(),corner2.getBlockX());
        int minY = Math.min(corner1.getBlockY(),corner2.getBlockY());
        int maxY = Math.max(corner1.getBlockY(),corner2.getBlockY());
        int minZ = Math.min(corner1.getBlockZ(),corner2.getBlockZ());
        int maxZ = Math.max(corner1.getBlockZ(),corner2.getBlockZ());
        return (location.getX() >= minX && location.getX() <= maxX) && (location.getY() >= minY && location.getY() <= maxY) && (location.getZ() >= minZ && location.getZ() <= maxZ);
    }
}
