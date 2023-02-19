package io.github.itzispyder.universalvaults.data;

import io.github.itzispyder.universalvaults.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

/**
 * Configuration file loader
 */
public abstract class Config {

    public static final File dataFolder = Main.getInstance().getDataFolder();
    public static final File file = new File(dataFolder,"config.yml");
    public static final FileConfiguration config = Main.getInstance().getConfig();

    /**
     * Save the config file.
     */
    public static void save() {
        try {
            config.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Plugin configuration section
     */
    public class Plugin {
        public static final String prefix = config.getString("config.plugin.prefix");
    }

    /**
     * Archive configuration section
     */
    public class Archive {
        public static final String watermark = config.getString("config.archive.watermark");
        public static final int max_nbt_length = config.getInt("config.archive.max-nbt-length");
        public static final int min_nbt_length = config.getInt("config.archive.min-nbt-length");
    }

    /**
     * Submission configuration section
     */
    public class Submission {
        public static final int cool_down = config.getInt("config.submission.cool-down");
        public static final boolean enabled = config.getBoolean("config.submission.enabled");
    }
}
