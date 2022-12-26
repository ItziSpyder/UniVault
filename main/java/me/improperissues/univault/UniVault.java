package me.improperissues.univault;

import me.improperissues.univault.commands.Commands;
import me.improperissues.univault.commands.Tabs;
import me.improperissues.univault.data.Items;
import me.improperissues.univault.data.Shelf;
import me.improperissues.univault.events.PageClickEvent;
import me.improperissues.univault.events.ShelfClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class UniVault extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getLogger().info("Enabled UniVault " + getDescription().getVersion());
        getServer().getLogger().info("" +
                "\n========================================" +
                "\n               [UniVault]              " +
                "\n                                        " +
                "\n/vault <page number>" +
                "\n/view <type> <page number>" +
                "\n/submit" +
                "\n========================================"
        );
        Shelf.reloadItemList();

        // Register events
        getServer().getPluginManager().registerEvents(new PageClickEvent(),this);
        getServer().getPluginManager().registerEvents(new ShelfClickEvent(),this);

        // Files
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Commands
        getCommand("vault").setExecutor(new Commands());
        getCommand("vault").setTabCompleter(new Tabs());
        getCommand("view").setExecutor(new Commands());
        getCommand("view").setTabCompleter(new Tabs());
        getCommand("submit").setExecutor(new Commands());
        getCommand("submit").setTabCompleter(new Tabs());

        // Register items
        Items.registerItems();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("Disabled UniVault " + getDescription().getVersion());
    }

    public static Plugin getInstance() {
        return Bukkit.getServer().getPluginManager().getPlugin("UniVault");
    }
}
