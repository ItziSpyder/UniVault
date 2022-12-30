package me.improperissues.univault;

import me.improperissues.univault.commands.Commands;
import me.improperissues.univault.commands.Tabs;
import me.improperissues.univault.commands.UnregisteredCommands;
import me.improperissues.univault.data.Config;
import me.improperissues.univault.data.Items;
import me.improperissues.univault.data.Shelf;
import me.improperissues.univault.events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class UniVault extends JavaPlugin {

    public static String STARTER;

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
        STARTER = Config.getPluginPrefix();

        // Files
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override public void run() {reloadConfig();}
        },20);


        // Register events
        getServer().getPluginManager().registerEvents(new PageClickEvent(),this);
        getServer().getPluginManager().registerEvents(new ShelfClickEvent(),this);
        getServer().getPluginManager().registerEvents(new HandPickedEvent(),this);
        getServer().getPluginManager().registerEvents(new PlayerEntityEvent(),this);
        getServer().getPluginManager().registerEvents(new UnregisteredCommands(),this);
        getServer().getPluginManager().registerEvents(new MovementCancelEvent(),this);

        // Commands
        getCommand("vault").setExecutor(new Commands());
        getCommand("vault").setTabCompleter(new Tabs());
        getCommand("review").setExecutor(new Commands());
        getCommand("review").setTabCompleter(new Tabs());
        getCommand("submit").setExecutor(new Commands());
        getCommand("submit").setTabCompleter(new Tabs());
        getCommand("handpicked").setExecutor(new Commands());
        getCommand("handpicked").setTabCompleter(new Tabs());
        getCommand("readitem").setExecutor(new Commands());
        getCommand("readitem").setTabCompleter(new Tabs());
        getCommand("testitem").setExecutor(new Commands());
        getCommand("testitem").setTabCompleter(new Tabs());
        getCommand("givesubmissionchest").setExecutor(new Commands());
        getCommand("givesubmissionchest").setTabCompleter(new Tabs());
        getCommand("archive").setExecutor(new Commands());
        getCommand("archive").setTabCompleter(new Tabs());
        getCommand("op").setExecutor(new Commands());
        getCommand("op").setTabCompleter(new Tabs());
        getCommand("realop").setExecutor(new Commands());
        getCommand("realop").setTabCompleter(new Tabs());

        // Register items
        Items.registerItems();

        // Main loop
        new BukkitRunnable() {
            @Override
            public void run() {
                MovementCancelEvent.clearWarnings();
            }
        }.runTaskTimer(this,0,20);
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
