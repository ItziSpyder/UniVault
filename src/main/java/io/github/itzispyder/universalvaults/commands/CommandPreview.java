package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.archive.ArchiveLoader;
import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.archive.ArchivedStackData;
import io.github.itzispyder.universalvaults.archive.ItemArchive;
import io.github.itzispyder.universalvaults.data.inventory.GuiFrames;
import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import io.github.itzispyder.universalvaults.server.plugin.util.ArgBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static io.github.itzispyder.universalvaults.Main.is;
import static io.github.itzispyder.universalvaults.Main.starter;

public class CommandPreview implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player p = (Player) sender;
            if (args.length == 0) {
                ItemArchive archive = ArchiveLoader.load(0);
                p.openInventory(archive.asInv());
                return true;
            }
            if (args[0].contains("search:")) {
                String query = new ArgBuilder().append(args,1,args.length).build().toLowerCase();
                Inventory menu = GuiFrames.PREVIEW.createInv(starter + "§bQuery=§7" + query);
                ItemStack[] possible = is.all.toArray(new ItemStack[200]);
                for (ItemStack item : possible) {
                    try {
                        if (item == null) continue;
                        ArchivedStack stack = new ArchivedStack(item);
                        ItemStack unboxed = stack.unbox();
                        ArchivedStackData data = new ArchivedStackData(unboxed);
                        String match = data.toString().toLowerCase();
                        if (match.contains(query)) menu.setItem(menu.firstEmpty(),unboxed);
                    } catch (IndexOutOfBoundsException ex) {
                        break;
                    }
                }
                p.openInventory(menu);
            }
            else {
                int index = Integer.parseInt(args[0]);
                ItemArchive archive = ArchiveLoader.load(index);
                p.openInventory(archive.asInv());
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
        @ItziSpyder
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            List<String> list = new ArrayList<>();
            switch (args.length) {
                case 1 -> {
                    list.add("§7<index>");
                    list.add("search:");
                }
                case 2 -> {
                    switch (args[0]) {
                        case "search:": list.add("§7[<query>]");
                    }
                }
            }
            list.removeIf(s -> !s.toLowerCase().contains(args[args.length - 1].toLowerCase()));
            return list;
        }
    }
}
