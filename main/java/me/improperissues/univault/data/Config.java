package me.improperissues.univault.data;

import me.improperissues.univault.UniVault;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    public static FileConfiguration config = UniVault.getInstance().getConfig();

    public static int getMaxPages() {
        return config.getInt("config.general.max_pages");
    }

    public static int getMaxNBT() {
        return config.getInt("config.general.max_nbt_length");
    }

    public static int getMinNBT() {
        return config.getInt("config.general.min_nbt_length");
    }

    public static boolean getRequiresOp() {
        return config.getBoolean("config.permissions.requires_op");
    }

    public static boolean getNonopEdit() {
        return config.getBoolean("config.permissions.nonop_edit");
    }

    public static boolean getEnableSubmissions() {
        return config.getBoolean("config.submissions.enabled");
    }

    public static boolean getDuplicates() {
        return config.getBoolean("config.submissions.duplicates");
    }

    public static boolean getJoinClear() {
        return config.getBoolean("config.general.join_clear");
    }

    public static int getCooldown() {
        return config.getInt("config.submissions.cooldown");
    }

    public static double getMaxPlayerSpeed() {
        return config.getInt("config.player.max_speed");
    }

    public static long getPlayerCommandDelay() {
        return (long) (config.getDouble("config.player.command_delay") * 1000);
    }

    public static List<String> getDelayBlacklist() {
        return config.getStringList("config.player.delay_blacklist");
    }

    public static boolean getArchiveBreakPlace() {
        return config.getBoolean("config.archive.can_break_or_place");
    }


    public static String getWaterMark() {
        String mark = config.getString("config.plugin.watermark");
        if (mark == null) mark = "Plugin by ImproperIssues, visit \"github.com/ItziSpyder/UniVault\"";
        return mark;
    }

    public static String getPluginPrefix() {
        String prefix = config.getString("config.plugin.prefix");
        if (prefix == null || prefix.equals("")) prefix = "§7[§3§lUni§b§lV§7] §";
        return prefix;
    }
}
