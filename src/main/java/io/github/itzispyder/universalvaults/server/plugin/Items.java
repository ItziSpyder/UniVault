package io.github.itzispyder.universalvaults.server.plugin;

import io.github.itzispyder.universalvaults.server.plugin.misc.PaneType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Server plugin items
 */
public abstract class Items {

    /**
     * Register all the server plugin items
     */
    public static void registerAll() {
        setBack();
        setNext();
        Items.Submissions.setSubmit();
        Items.Submissions.setToSubmit();
        Items.Submissions.setToArchive();
    }

    /**
     * Creates a new blank glass pane with a pane type
     * @param type pane type
     * @return the blank glass pane
     */
    public static ItemStack blank(PaneType type) {
        Material mat = type.getType();
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public static final ItemStack BACK = new ItemStack(Material.ARROW);
    public static final ItemStack NEXT = new ItemStack(Material.ARROW);
    public static final ItemStack AIR = new ItemStack(Material.AIR);

    static void setBack() {
        ItemMeta meta = BACK.getItemMeta();
        meta.setDisplayName("§fPrevious");
        meta.setLore(List.of("§7Return to previous"));
        BACK.setItemMeta(meta);
    }

    static void setNext() {
        ItemMeta meta = NEXT.getItemMeta();
        meta.setDisplayName("§fNext");
        meta.setLore(List.of("§7Go to next"));
        NEXT.setItemMeta(meta);
    }

    public class Submissions {

        public static final ItemStack SUBMIT = new ItemStack(Material.DIAMOND);
        public static final ItemStack TOSUBMIT = new ItemStack(Material.DIAMOND);
        public static final ItemStack TOARCHIVE = new ItemStack(Material.CHEST);

        static void setSubmit() {
            ItemMeta meta = SUBMIT.getItemMeta();
            meta.setDisplayName("§bSubmit Contents");
            meta.setLore(List.of(
                    "§7By submitting you are",
                    "§7making sure that you are",
                    "§7submitting items within",
                    "§7set set NBT limits.",
                    "§8(/testitem)"
            ));
            SUBMIT.setItemMeta(meta);
        }

        static void setToSubmit() {
            ItemMeta meta = TOSUBMIT.getItemMeta();
            meta.setDisplayName("§bSubmission Page");
            meta.setLore(List.of("§7Navigate to submission page..."));
            TOSUBMIT.setItemMeta(meta);
        }

        static void setToArchive() {
            ItemMeta meta = TOARCHIVE.getItemMeta();
            meta.setDisplayName("§bArchive Page");
            meta.setLore(List.of("§7Navigate to archives..."));
            TOARCHIVE.setItemMeta(meta);
        }
    }
}
