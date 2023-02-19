package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.Main;
import io.github.itzispyder.universalvaults.archive.ArchiveLoader;
import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static io.github.itzispyder.universalvaults.Main.is;
import static io.github.itzispyder.universalvaults.Main.starter;

@ItziSpyder
public class CommandArchive implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            switch (args[0]) {
                case "reload" -> {
                    Main.shr.runTaskAsynchronously(Main.getInstance(),() -> {
                        Main.is.reload();
                        sender.sendMessage(starter + "§bUpdated and Reloaded the archived!");
                    });
                }
                case "saveall" -> {
                    Main.shr.runTaskAsynchronously(Main.getInstance(),() -> {
                        ArchiveLoader.archiveAll();
                        sender.sendMessage(starter + "§bSaved the archived!");
                    });
                }
                case "update" -> {
                    Main.shr.runTaskAsynchronously(Main.getInstance(),() -> {
                        Main.is.update();
                        sender.sendMessage(starter + "§bUpdated the archived!");
                    });
                }
                case "preview" -> {
                    int index = Integer.parseInt(args[1]);
                    Bukkit.dispatchCommand(sender,"preview " + index);
                }
                case "submit" -> {
                    Bukkit.dispatchCommand(sender,"submit");
                }
                case "list" -> {
                    sender.sendMessage("\n" + starter + "§bArchive statistics:" +
                            "\n    §3There are §b" + is.all.size() + " §3total submissions across the server," +
                            "\n    §b" + is.shulker.size() + " §3are storage, and §b" + is.random.size() + " §3are random.\n ");
                }
                default -> sender.sendMessage(starter + "§cUnknown or incomplete command!");
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
                    list.add("reload");
                    list.add("saveall");
                    list.add("update");
                    list.add("list");
                    list.add("preview");
                    list.add("submit");
                }
                case 2 -> {
                    switch (args[0]) {
                        case "preview": list.add("§7<index>");
                    }
                }
            }
            list.removeIf(s -> !s.toLowerCase().contains(args[args.length - 1].toLowerCase()));
            return list;
        }
    }
}
