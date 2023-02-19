package io.github.itzispyder.universalvaults.events.triggers;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.archive.ArchiveLoader;
import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.archive.vaults.Submission;
import io.github.itzispyder.universalvaults.data.inventory.InventoryContents;
import io.github.itzispyder.universalvaults.server.plugin.Items;
import io.github.itzispyder.universalvaults.server.plugin.util.SoundPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static io.github.itzispyder.universalvaults.Main.is;
import static io.github.itzispyder.universalvaults.Main.starter;

public class OnInventoryClick implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent e) {
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();
        Player p = (Player) e.getWhoClicked();
        try {
            if (title.contains(starter)) {
                ItemStack item = e.getCurrentItem();
                ItemMeta meta = item.getItemMeta();
                String display = ArchivedStack.getDisplay(item);
                if (inv.getType().equals(InventoryType.PLAYER)) return;
                if (matchDisplay(item," ")) {
                    e.setCancelled(true);
                    return;
                }
                if (matchDisplay(item,Items.Submissions.TOSUBMIT)) {
                    e.setCancelled(true);
                    Bukkit.dispatchCommand(p,"submit");
                    return;
                }
                if (matchDisplay(item,Items.Submissions.TOARCHIVE)) {
                    e.setCancelled(true);
                    Bukkit.dispatchCommand(p,"preview 0");
                    return;
                }

                if (title.contains("§bSubmission"))
                    functionSubmission(e);
                else if (title.contains("§3Archive index.§b"))
                    functionArchivePage(e);
            }
        } catch (Exception ex) {}
    }


    /**
     * Checks if the display name of an item matches a string
     * @param item the item
     * @param display the display name to match
     * @return if the display matches the item
     */
    static boolean matchDisplay(ItemStack item, String display) {
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (!meta.hasDisplayName()) return false;
        return Objects.equals(meta.getDisplayName(),display);
    }

    /**
     * Checks if the display names of two items match
     * @param item the item
     * @param item2 the second item
     * @return if the display matches
     */
    static boolean matchDisplay(ItemStack item, ItemStack item2) {
        if (!item.hasItemMeta() || !item2.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (!meta.hasDisplayName() || !meta2.hasDisplayName()) return false;
        return Objects.equals(meta.getDisplayName(),meta2.getDisplayName());
    }

    // A huge gap because the code below is messy, and it hurts my brain









    /**
     * Called when a player clicks a submission page
     * @param e inventory click event
     */
    static void functionSubmission(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        String display = meta.getDisplayName();
        if (display.contains("§bSubmit Contents")) {
            e.setCancelled(true);
            SoundPlayer submit = new SoundPlayer(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE,10,10);
            submit.play(p);
            Main.shr.runTaskAsynchronously(Main.getInstance(),() -> {
                InventoryContents contents = new InventoryContents(inv);
                ItemStack[] array = contents.subArray(19,20,21,22,23,24,25,28,29,30,31,32,33,34);
                Submission sub = new Submission(p,array);
                p.sendMessage(starter + "§bSubmitting items §7§o(" + sub.getContents().size() + "/14)§b, eliminating possible duplicates...");
                if (sub.getContents().isEmpty()) {
                    p.sendMessage(starter + "§cCannot submit nothing! Please be sure to add something for submission!");
                    return;
                }
                is.acceptSubmission(sub);
                ArchiveLoader.archiveAll();
                is.reload();
                p.sendMessage(starter + "§bSubmissions saved! §7§o(There are " + is.all.size() + " submissions)");
            });
            p.closeInventory();
        }
    }

    /**
     * Called when a player clicks an archive page
     * @param e inventory click event
     */
    static void functionArchivePage(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();
        ItemStack item = e.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        String display = meta.getDisplayName();
        SoundPlayer page = new SoundPlayer(p.getLocation(), Sound.ITEM_BOOK_PAGE_TURN,10,10);
        int index = Integer.parseInt(title.split("§3Archive index.§b")[1]);
        index = Math.max(index, 0);
        if (display.contains("§fNext")) {
            e.setCancelled(true);
            if (index + 1 > is.getAmountArchives() - 1) return;
            Bukkit.dispatchCommand(p,"preview " + (index + 1));
            page.play(p);
        }
        else if (display.contains("§fPrevious")) {
            e.setCancelled(true);
            if (index - 1 < 0) return;
            Bukkit.dispatchCommand(p,"preview " + (index - 1));
            page.play(p);
        }
    }
}
