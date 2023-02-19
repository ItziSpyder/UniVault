package io.github.itzispyder.universalvaults.archive.vaults;

import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.data.SavedPlayer;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a submission for the archive
 */
public class Submission {

    private Set<ItemStack> contents;
    private SavedPlayer submitter;
    private boolean accepted;

    /**
     * Creates a submission
     * @param player the submitter
     * @param contents the contents submitted
     */
    @ItziSpyder // (you found 10/10, congrats!)
    public Submission(Player player, ItemStack[] contents) {
        this.submitter = new SavedPlayer(player);
        this.contents = new HashSet<>();
        this.accepted = true;
        for (ItemStack item : contents) {
            if (item == null) continue;
            item.setAmount(1);
            ArchivedStack stack = new ArchivedStack(item);
            stack.watermark();
            this.contents.add(stack.box());
        }
    }

    public SavedPlayer getSubmitter() {
        return submitter;
    }

    public Set<ItemStack> getContents() {
        return contents;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
