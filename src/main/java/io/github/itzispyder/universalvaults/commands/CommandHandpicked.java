package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import io.github.itzispyder.universalvaults.handpicked.HandPicked;
import io.github.itzispyder.universalvaults.handpicked.HandPickedManager;
import io.github.itzispyder.universalvaults.handpicked.ViewMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static io.github.itzispyder.universalvaults.Main.starter;

public class CommandHandpicked implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player p = (Player) sender;
            switch (args[0]) {
                case "give" -> {
                    ItemStack item = HandPickedManager.generateItem(args[1]);
                    p.getInventory().addItem(item);
                    p.sendMessage(starter + "§bGave one §7" + args[1] + " §bhandpicked archive chest!");
                }
                case "open" -> {
                    HandPicked hp = HandPickedManager.load(args[1]);
                    p.openInventory(hp.asInv(ViewMode.PREVIEW));
                    p.sendMessage(starter + "§bOpening §7" + hp.getName() + " §bhandpicked archive...");
                }
                case "edit" -> {
                    HandPicked hp = HandPickedManager.load(args[1]);
                    p.openInventory(hp.asInv(ViewMode.EDITING));
                    p.sendMessage(starter + "§bEditing §7" + hp.getName() + " §bhandpicked archive...");
                }
                default -> p.sendMessage(starter + "§cUnknown or incomplete command!");
            }
            return true;
        } catch (Exception ex) {
            CmdExHandler exh = new CmdExHandler(ex,command);
            sender.sendMessage(exh.getErrorMessage());
            return true;
        }
    }

    public static class Tab implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            List<String> list = new ArrayList<>();
            switch (args.length) {
                case 1 -> {
                    list.add("open");
                    list.add("edit");
                    list.add("give");
                }
                case 2 -> {
                    list.addAll(HandPickedManager.listValues());
                }
            }
            list.removeIf(s -> !s.toLowerCase().contains(args[args.length - 1].toLowerCase()));
            return list;
        }
    }
}
