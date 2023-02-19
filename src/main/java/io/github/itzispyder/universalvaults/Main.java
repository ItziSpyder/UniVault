package io.github.itzispyder.universalvaults;

import io.github.itzispyder.universalvaults.archive.vaults.ItemSets;
import io.github.itzispyder.universalvaults.commands.*;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.data.inventory.GuiFrames;
import io.github.itzispyder.universalvaults.events.triggers.OnInventoryClick;
import io.github.itzispyder.universalvaults.events.triggers.OnInventoryClose;
import io.github.itzispyder.universalvaults.events.triggers.OnPlayerInteract;
import io.github.itzispyder.universalvaults.server.plugin.Items;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Logger;

/**
 * UniversalVaults main class
 */
@ItziSpyder // (you found 1/10, congrats!)
public final class Main extends JavaPlugin {

    public static final PluginManager pm = Bukkit.getPluginManager();
    public static final ItemSets is = new ItemSets();
    public static final BukkitScheduler shr = Bukkit.getScheduler();
    public static final Logger log = Bukkit.getLogger();
    public static String starter = "";

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        starter = Config.Plugin.prefix + " ";

        // Registry
        this.registerAll();
        Items.registerAll();
        GuiFrames.registerAll();

        // Archives
        shr.runTaskAsynchronously(this,Main.is::reload);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerAll() {
        // Events
        pm.registerEvents(new OnInventoryClick(),this);
        pm.registerEvents(new OnInventoryClose(),this);
        pm.registerEvents(new OnPlayerInteract(),this);

        // Commands
        getCommand("preview").setExecutor(new CommandPreview());
        getCommand("preview").setTabCompleter(new CommandPreview.Tab());
        getCommand("submit").setExecutor(new CommandSubmit());
        getCommand("submit").setTabCompleter(new CommandSubmit.Tab());
        getCommand("archive").setExecutor(new CommandArchive());
        getCommand("archive").setTabCompleter(new CommandArchive.Tab());
        getCommand("test").setExecutor(new CommandTest());
        getCommand("test").setTabCompleter(new CommandTest.Tab());
        getCommand("testitem").setExecutor(new CommandTestItem());
        getCommand("testitem").setTabCompleter(new CommandTestItem.Tab());
        getCommand("handpicked").setExecutor(new CommandHandpicked());
        getCommand("handpicked").setTabCompleter(new CommandHandpicked.Tab());

        // Loops
    }

    /**
     * Returns an instance of this plugin
     * @return instance of this plugin
     */
    public static Plugin getInstance() {
        return pm.getPlugin("UniVault");
    }
}
