package io.github.itzispyder.universalvaults.archive.vaults;

import io.github.itzispyder.universalvaults.archive.ArchiveManager;
import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

import static io.github.itzispyder.universalvaults.Main.starter;

/**
 * The archived item sorter and manager
 */
public class ItemSets {

    public Set<ItemStack> all, random, shulker;
    private HashMap<String,Long> submitCoolDown = new HashMap<>();

    /**
     * Constructs an item set
     */
    public ItemSets() {
        this.all = new HashSet<>();
        this.random = new HashSet<>();
        this.shulker = new HashSet<>();
    }

    /**
     * Does a full reload for the archive
     */
    public void fullReload() {
        ArchiveManager.archiveAll();
        reload();
    }

    /**
     * Reloads the item sets
     */
    public void reload() {
        all = ArchiveManager.loadAll();
        update();
    }

    /**
     * Updates the item sets
     */
    public void update() {
        random = new HashSet<>(all);
        random.removeIf(this::isShulker);
        shulker = new HashSet<>(all);
        shulker.removeIf(this::isRandom);
    }

    /**
     * Appends a new submission to the archive
     * @param submission the submission
     */
    public void acceptSubmission(Submission submission) {
        if (submission == null || submission.getContents() == null) {
            return; // if the submission is null
        }
        Player p = submission.getSubmitter().getPlayer();
        if (!Config.Submission.enabled) {
            p.sendMessage(starter + "§cSubmissions are currently disabled at the moment, come back later!");
            submission.setAccepted(false);
            return; // submission is not enabled
        }
        if (submitCoolDown.containsKey(p.getName()) && submitCoolDown.get(p.getName()) > System.currentTimeMillis()) {
            double sec = Math.floor((submitCoolDown.get(p.getName()) - System.currentTimeMillis()) / 100.0) / 10;
            p.sendMessage(starter + "§cPlease wait §7" + sec + " §cseconds before submitting again!");
            submission.setAccepted(false);
            return; // cool down
        }
        // add cool down for player
        submitCoolDown.put(p.getName(),System.currentTimeMillis() + (Config.Submission.cool_down * 1000L));
        // begin submission
        List<String> failed = new ArrayList<>();
        for (ItemStack item : submission.getContents()) {
            ArchivedStack stack = new ArchivedStack(item);
            if (!ArchivedStack.isPassable(stack.unbox())){
                failed.add(stack.unbox().getType().name().toLowerCase());
                continue;
            }
            this.all.add(stack.toItemStack());
        }
        if (failed.size() > 0) p.sendMessage(starter + "§cThe following items have failed to submit: §7(/testitem) §o" + failed);
        submission.setAccepted(true);
    }

    /**
     * If the item is a shulker
     * @param item the item
     * @return is shulker
     */
    private boolean isShulker(ItemStack item) {
        ArchivedStack stack = new ArchivedStack(item);
        return stack.isArchived() && stack.getType().equals(Material.LIGHT_GRAY_SHULKER_BOX);
    }

    /**
     * If the item is a random stack
     * @param item the item
     * @return is random
     */
    @ItziSpyder
    public boolean isRandom(ItemStack item) {
        ArchivedStack stack = new ArchivedStack(item);
        return stack.isArchived() && stack.getType().equals(Material.WHITE_SHULKER_BOX);
    }

    /**
     * Predict the amount of archives used to store all items
     * @return the predicted amount
     */
    public int getAmountArchives() {
        File parent = new File(Config.dataFolder,"archives");
        File[] children = parent.listFiles();
        if (children == null) return 0;
        return children.length;
    }

    /**
     * Predict the amount of archives used to store all items
     * @return the predicted amount
     */
    public int predictAmountArchives() {
        int predicted = all.size() / 45;
        predicted = all.size() % 45 == 0 ? predicted : predicted + 1;
        return predicted;
    }
}
