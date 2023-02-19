package io.github.itzispyder.universalvaults.server.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Server utils
 */
public abstract class ServerUtils {

    /**
     * List of names of online players
     * @return list of names
     */
    public static List<String> listPlayers() {
        List<String> list =new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
        return list;
    }

    /**
     * List of names of online staff
     * @return list of names
     */
    public static List<String> listStaff() {
        List<String> list =new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.isOp()) list.add(p.getName());
        });
        return list;
    }

    /**
     * List of names of online staff
     * @return list of staff
     */
    public static Set<Player> getStaff() {
        Set<Player> list = new HashSet<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.isOp()) list.add(p);
        });
        return list;
    }
}
