package io.github.itzispyder.universalvaults.server.plugin.util;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents a sound player
 */
public class SoundPlayer {

    private Location location;
    private Sound sound;
    private float volume;
    private float pitch;

    /**
     * Constructs a new sound, this aims to add more methods to
     * the Bukkit APIs Sound class, as they don't have many
     * methods to use.
     *
     * @param location Location
     * @param sound Sound
     * @param volume float
     * @param pitch float
     */
    public SoundPlayer(Location location, Sound sound, float volume, float pitch) {
        this.location = location;
        this.sound = sound;
        this.pitch = pitch;
        this.volume = volume;
    }


    /**
     * Plays a sound to a player but at the store location
     *
     * @param player Player
     */
    public void play(Player player) {
        player.playSound(this.location,this.sound,this.volume,this.pitch);
    }

    /**
     * Plays a sound to a player but at the player's location
     *
     * @param player Player
     */
    public void playAt(Player player) {
        player.playSound(player.getLocation(),this.sound,this.volume,this.pitch);
    }

    /**
     * Plays the sound to all players within a distance, but at the stored location.
     *
     * @param distance double
     */
    @ItziSpyder // (you found 2/10, congrats!)
    public void playWithin(double distance) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != null && p.getWorld() == this.location.getWorld() && p.getLocation().distanceSquared(this.location) < distance) {
                p.playSound(this.location,this.sound,this.volume,this.pitch);
            }
        }
    }

    /**
     * Plays the sound to all players within a distance, but at the players' location.
     *
     * @param distance double
     */
    public void playWithinAt(double distance) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != null && p.getWorld() == this.location.getWorld() && p.getLocation().distanceSquared(this.location) < distance) {
                p.playSound(p.getLocation(),this.sound,this.volume,this.pitch);
            }
        }
    }


    /**
     * Plays the sound to all players on the server, but at the stored location.
     */
    public void playAll() {
        for (Player p : Bukkit.getOnlinePlayers()) p.playSound(this.location,this.sound,this.volume,this.pitch);
    }

    /**
     * Plays the sound to all players on the server, but at the players' location.
     */
    public void playAllAt() {
        for (Player p : Bukkit.getOnlinePlayers()) p.playSound(p.getLocation(),this.sound,this.volume,this.pitch);
    }

    /**
     * Repeats a sound to a player, but at the stored location.
     *
     * @param player Player
     * @param times int
     * @param tickDelay int
     */
    public void repeat(Player player, int times, int tickDelay) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < times) {
                    play(player);
                    i ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,tickDelay);
    }

    /**
     * Repeats a sound to a player, but at the player's location.
     *
     * @param player Player
     * @param times int
     * @param tickDelay int
     */
    public void repeatAt(Player player, int times, int tickDelay) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < times) {
                    playAt(player);
                    i ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,tickDelay);
    }

    /**
     * Repeats a sound to all players on the server, but at the stored location.
     *
     * @param times int
     * @param tickDelay int
     */
    public void repeatAll(int times, int tickDelay) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < times) {
                    playAll();
                    i ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,tickDelay);
    }

    /**
     * Repeats a sound to all players on the server, but at the players' location.
     *
     * @param times int
     * @param tickDelay int
     */
    public void repeatAllAt(int times, int tickDelay) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < times) {
                    playAllAt();
                    i ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,tickDelay);
    }

    /**
     * Repeats a sound to all players within a radius, but at the stored location.
     *
     * @param radius double
     * @param times int
     * @param tickDelay int
     */
    public void repeatAll(double radius,int times, int tickDelay) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < times) {
                    playWithin(radius);
                    i ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,tickDelay);
    }

    /**
     * Repeats a sound to all players within a radius, but at the players' location.
     *
     * @param distance double
     * @param times int
     * @param tickDelay int
     */
    public void repeatAllAt(double distance, int times, int tickDelay) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i < times) {
                    playWithinAt(distance);
                    i ++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(),0,tickDelay);
    }

    public Sound getSound() {
        return sound;
    }

    public float getPitch() {
        return pitch;
    }

    public float getVolume() {
        return volume;
    }

    public Location getLocation() {
        return location;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
