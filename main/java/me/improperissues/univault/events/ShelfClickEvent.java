package me.improperissues.univault.events;

import me.improperissues.univault.data.Config;
import me.improperissues.univault.data.Items;
import me.improperissues.univault.data.Shelf;
import me.improperissues.univault.other.Sounds;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShelfClickEvent implements Listener {

    public static HashMap<String,Long> cooldown = new HashMap<>();

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();

        try {
            ItemStack item = e.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            String display = meta.getDisplayName();

            if (title.contains("§7>> §a§oSubmission")) {
                // cancel the event if conditions aren't met
                if (inv.getType().equals(InventoryType.PLAYER)) {
                    if (!p.isOp() && !Config.getNonopEdit()) {
                        e.setCancelled(true);
                        return;
                    }
                    return;
                }
                if (display.equals(" ")) {
                    e.setCancelled(true);
                    return;
                }

                // register the event if the conditions are met
                if (display.contains(getDisplay(Items.SUBMIT))) {
                    e.setCancelled(true);
                    // if action is on cooldown, cance
                    if (cooldown.containsKey(p.getName()) && cooldown.get(p.getName()) > System.currentTimeMillis())  {
                        int sec = (int) Math.ceil((cooldown.get(p.getName()) - System.currentTimeMillis()) / 1000.);
                        p.closeInventory();
                        p.sendMessage("§4This action is on cooldown for another " + sec + " seconds!");
                        return;
                    }
                    if (Shelf.filterForSubmission(inv.getContents()).length == 0) {
                        p.closeInventory();
                        p.sendMessage("§4Submission failed since non of the provided items are submittable!");
                        return;
                    }
                    cooldown.put(p.getName(),System.currentTimeMillis() + (1000L * Config.getCooldown()));
                    // item submission
                    p.sendMessage("§dSubmitting items...");
                    Shelf.submitItemList(inv.getContents());
                    p.closeInventory();
                    Sounds.closeVault(p);
                } else if (display.contains(getDisplay(Items.NULL))) {
                    e.setCurrentItem(Items.AIR);
                    e.setCancelled(true);
                } else if (display.contains("§eRANDOM ITEM")) {
                    e.setCancelled(true);
                    p.closeInventory();
                    p.getWorld().dropItemNaturally(p.getLocation(),Shelf.getRanItem());
                    Sounds.randomItem(p);
                } else if (display.contains(getDisplay(Items.ALLITEMS))) {
                    e.setCancelled(true);
                    p.chat("/review all");
                } else if (display.contains(getDisplay(Items.SHULKERITEMS))) {
                    e.setCancelled(true);
                    p.chat("/review shulker");
                } else if (display.contains(getDisplay(Items.RANDOMITEMS))) {
                    e.setCancelled(true);
                    p.chat("/review random");
                } else if (display.equals(getDisplay(Items.SEARCH))) {
                    e.setCancelled(true);
                    p.chat("/review search");
                }
            } else if (title.contains("§c#SEARCH KEY:§7")) {
                if (inv.getType().equals(InventoryType.PLAYER)) {
                    return;
                }
                if (display.equals(getDisplay(Items.SUBMISSION))) {
                    e.setCancelled(true);
                    p.chat("/submit");
                } else if (display.equals(getDisplay(Items.SEARCH))) {
                    e.setCancelled(true);
                    p.chat("/review search");
                }
            }
        } catch (NullPointerException exception) {
            // empty
        }
    }

    public static void sendSearchMessage(Player player) {
        TextComponent message = new TextComponent("§7====================\n§f§nClick here to search\n§7====================");
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,TextComponent.fromLegacyText("Click to search query")));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,"/review search #:"));
        player.closeInventory();
        player.spigot().sendMessage(message);
    }

    public static void openQueriedItems(Player player, String query) {
        Sounds.turnPage(player);
        Inventory menu = Bukkit.createInventory(player,54,"§c#SEARCH KEY:§7" + query);
        ItemStack x = Items.setBlank(new ItemStack(Material.BLUE_STAINED_GLASS_PANE));
        ItemStack a = Items.AIR;
        menu.setContents(new ItemStack[]{
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                a,a,a,a,a,a,a,a,a,
                Items.SEARCH,x,x,x,x,x,x,x,Items.SUBMISSION
        });
        List<ItemStack> adds = new ArrayList<>(Shelf.STOREDITEMS);
        adds.removeIf(item -> !(getDisplay(item).toLowerCase().contains(query) || item.getType().name().toLowerCase().contains(query)));
        for (int i = 0; i < 45; i ++) {
            try {
                menu.setItem(menu.firstEmpty(),adds.get(i));
            } catch (IndexOutOfBoundsException exception) {
                break;
            }
        }
        player.openInventory(menu);
    }

    public static String getDisplay(ItemStack item) {
        return item.getItemMeta().getDisplayName();
    }
}
