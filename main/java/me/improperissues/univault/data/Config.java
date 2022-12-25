package me.improperissues.univault.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {

    static File file = new File("plugins/UniVault/config.yml");
    public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

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

    public static void setMaxPages(int maxPages) {
        config.set("config.general.max_pages",maxPages);
    }

    public static void setMaxNBT(int maxNBTLength) {
        config.set("config.general.max_nbt_length",getMinNBT());
    }

    public static void setMinNBT(int minNBTLength) {
        config.set("config.general.min_nbt_length",minNBTLength);
    }

    public static void setRequiresOp(boolean requiresOp) {
        config.set("config.permissions.requires_op",requiresOp);
    }

    public static void setNonopEdit(boolean nonopEdit) {
        config.set("config.permissions.nonop_edit",nonopEdit);
    }
}
