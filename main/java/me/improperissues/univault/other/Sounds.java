package me.improperissues.univault.other;

import me.improperissues.univault.UniVault;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Sounds {

    public static void playAll(Location location, Sound sound, float volume, float pitch, double distance) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != null && p.getWorld() == location.getWorld() && p.getLocation().distanceSquared(location) < distance) {
                p.playSound(location,sound,volume,pitch);
            }
        }
    }

    public static void repeat(Player player, Location location, Sound sound, float volume, float pitch, int times, int tickDelay) {
        new BukkitRunnable() {
            int repeat = 0;
            @Override
            public void run() {
                if (repeat < times) {
                    player.playSound(location,sound,volume,pitch);
                    repeat ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(UniVault.getInstance(),0,tickDelay);
    }

    public static void openVault(Player player) {
        player.playSound(player.getLocation(),Sound.BLOCK_CHEST_LOCKED,1,0.1F);
        player.playSound(player.getLocation(),Sound.BLOCK_ENDER_CHEST_OPEN,1,0.1F);
        repeat(player,player.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,1,0.5F,2,5);
    }

    public static void turnPage(Player player) {
        player.playSound(player.getLocation(),Sound.UI_BUTTON_CLICK,0.1F,0.1F);
        player.playSound(player.getLocation(),Sound.ITEM_BOOK_PAGE_TURN,10,10F);
    }

    public static void closeVault(Player player) {
        player.playSound(player.getLocation(),Sound.BLOCK_CHEST_LOCKED,1,0.1F);
        player.playSound(player.getLocation(),Sound.BLOCK_ENDER_CHEST_CLOSE,10,0.1F);
    }

    public static void randomItem(Player player) {
        repeat(player,player.getLocation(),Sound.BLOCK_NOTE_BLOCK_BELL,1,10F,3,5);
        player.playSound(player.getLocation(),Sound.BLOCK_ENDER_CHEST_CLOSE,10,0.1F);
    }

    public static void error(Player player) {
        repeat(player,player.getLocation(),Sound.BLOCK_NOTE_BLOCK_BASS,10,0.1F,2,5);
    }
}
