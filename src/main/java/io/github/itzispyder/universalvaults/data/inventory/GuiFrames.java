package io.github.itzispyder.universalvaults.data.inventory;

import io.github.itzispyder.universalvaults.server.plugin.Items;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import io.github.itzispyder.universalvaults.server.plugin.misc.PaneType;
import org.bukkit.inventory.ItemStack;

/**
 * The gui frames for this plugin
 */
public abstract class GuiFrames {

    /**
     * Registers the inventory presets for this server
     */
    public static void registerAll() {
        setSubmission();
        setPreview();
        setHandPicked();
    }

    public static final InventoryPreset SUBMISSION = new InventoryPreset();
    public static final InventoryPreset PREVIEW = new InventoryPreset();
    public static final InventoryPreset HAND_PICKED = new InventoryPreset();

    public static void setSubmission() {
        ItemStack x = Items.blank(PaneType.BLACK);
        ItemStack b = Items.blank(PaneType.CYAN);
        ItemStack a = Items.AIR;
        ItemStack sub = Items.Submissions.SUBMIT;
        ItemStack arc = Items.Submissions.TOARCHIVE;
        SUBMISSION.setContents(new ItemStack[]{
                b,b,b,b,b,b,b,b,b,
                x,x,x,x,x,x,x,x,x,
                x,a,a,a,a,a,a,a,x,
                x,a,a,a,a,a,a,a,x,
                x,x,x,x,x,x,x,x,x,
                arc,b,b,b,b,b,b,b,sub
        });
    }

    @ItziSpyder // (you found 4/10, congrats!)
    public static void setPreview() {
        ItemStack b = Items.blank(PaneType.CYAN);
        ItemStack bk = Items.BACK;
        ItemStack nx = Items.NEXT;
        ItemStack sub = Items.Submissions.TOSUBMIT;
        ItemStack a = Items.AIR;
        PREVIEW.setContents(new ItemStack[]{
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                bk,sub,b,b,b,b,b,b,nx
        });
    }

    public static void setHandPicked() {
        ItemStack b = Items.blank(PaneType.CYAN);
        ItemStack sub = Items.Submissions.TOSUBMIT;
        ItemStack a = Items.AIR;
        HAND_PICKED.setContents(new ItemStack[]{
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                sub,b,b,b,b,b,b,b,b
        });
    }
}
