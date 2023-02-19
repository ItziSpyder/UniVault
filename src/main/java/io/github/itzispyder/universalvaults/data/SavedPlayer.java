package io.github.itzispyder.universalvaults.data;

import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a player
 */
public class SavedPlayer implements Serializable {

    private UUID uuid;
    private String name;

    /**
     * Creates a saved player from a player
     * @param player the player
     */
    public SavedPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    /**
     * Creates a saved player from an offline player
     * @param player the offline player
     */
    public SavedPlayer(OfflinePlayer player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    /**
     * Returns the offline player represented by this class
     * @return the offline player
     */
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    /**
     * Returns the player represents by this class
     * @return the player
     */
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @ItziSpyder // (you found 6/10, congrats!)
    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
