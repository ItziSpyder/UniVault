package io.github.itzispyder.universalvaults.archive.vaults;

import io.github.itzispyder.universalvaults.archive.ArchiveManager;
import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.github.itzispyder.universalvaults.Main.starter;

/**
 * The archived item sorter and manager
 */
public class ItemSets {

    public Set<ItemStack> all, random, shulker;

    /**
     * Constructs an item set
     */
    public ItemSets() {
        this.all = new HashSet<>();
        this.random = new HashSet<>();
        this.shulker = new HashSet<>();
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
        if (submission == null) return;
        if (submission.getContents() == null) return;
        Player p = submission.getSubmitter().getPlayer();
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
        int predicted = all.size() / 45;
        predicted = all.size() % 45 == 0 ? predicted : predicted + 1;
        return predicted;
    }
}
