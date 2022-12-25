package me.improperissues.univault;

import me.improperissues.univault.commands.Commands;
import me.improperissues.univault.commands.Tabs;
import me.improperissues.univault.data.Items;
import me.improperissues.univault.events.PageClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class UniVault extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register events
        getServer().getPluginManager().registerEvents(new PageClickEvent(),this);

        // Files
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        // Commands
        getCommand("vault").setExecutor(new Commands());
        getCommand("vault").setTabCompleter(new Tabs());

        // Register items
        Items.registerItems();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
