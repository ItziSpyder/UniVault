package me.improperissues.univault.commands;

import me.improperissues.univault.data.Config;
import me.improperissues.univault.data.Page;
import me.improperissues.univault.data.Shelf;
import me.improperissues.univault.other.Sounds;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName().toLowerCase().trim();

        try {
            switch (commandName) {
                case "vault":
                    // if the player is not oped and the command requires op deny access
                    if (!sender.isOp() && Config.getRequiresOp()) {
                        sender.sendMessage("§4You cannot use this command!");
                        return true;
                    }
                    // makes sure the index is within bounds
                    int index = 0;
                    try {
                        index = Integer.parseInt(args[0]);
                    } catch (IllegalArgumentException exception) {
                        if (args[0].contains("existing:")) {
                            index = Integer.parseInt(args[0].split(":")[1]);
                        }
                    }
                    if (index < 1) index = 1;
                    if (index > Config.getMaxPages()) index = Config.getMaxPages();
                    // load and gets the instance of the class, then opens the inventory and sends a message
                    Page page = Page.load(index);
                    page.createInventory((Player) sender);
                    sender.sendMessage("§dOpening vault " + index + "...");
                    Sounds.openVault((Player) sender);
                    return true;
                case "view":
                    // permission check
                    if (!sender.isOp() && Config.getRequiresOp()) {
                        sender.sendMessage("§4You cannot use this command!");
                        return true;
                    }
                    // open inventory
                    int viewIndex = Integer.parseInt(args[1]);
                    sender.sendMessage("§dCurrent total items: " + Shelf.getItemList().size());
                    switch (args[0]) {
                        case "all":
                            Shelf.openItems((Player) sender,viewIndex - 1);
                            Sounds.openVault((Player) sender);
                            sender.sendMessage("§dOpening shelf:all " + viewIndex + "...");
                            return true;
                        case "shulker":
                            Shelf.openShulker((Player) sender,viewIndex - 1);
                            Sounds.openVault((Player) sender);
                            sender.sendMessage("§dOpening shelf:shulker " + viewIndex + "...");
                            return true;
                        case "random":
                            Shelf.openRandom((Player) sender,viewIndex - 1);
                            Sounds.openVault((Player) sender);
                            sender.sendMessage("§dOpening shelf:random " + viewIndex + "...");
                            return true;
                    }
                    return false;
                case "submit":
                    if (args.length == 0) {
                        // permission check
                        if (!Config.getEnableSubmissions()) {
                            sender.sendMessage("§4Submissions are disabled at the moment!");
                            return true;
                        }
                        // open inventory
                        Shelf.openSubmit((Player) sender);
                        Sounds.openVault((Player) sender);
                        sender.sendMessage("§dOpening item submission menu...");
                        return true;
                    } else if (args.length == 2) {
                        // deletes an item according to provided index
                        if (!sender.isOp()) {
                            sender.sendMessage("§4You cannot use this command!");
                            return true;
                        }
                        if (args[0].equals("delete")) {
                            int deleteIndex = Integer.parseInt(args[1]);
                            Shelf.delete((Player) sender,deleteIndex);
                            return true;
                        }
                    }
                    return false;
            }
        } catch (Exception exception) {
            // if the command generates or throws and exception, state the cause and print the exception to the command sender
            sender.sendMessage("§4The following error occurred: §c" + exception);
            sender.sendMessage("§4Caused by: §c" + exception.getCause());
            sender.sendMessage("§4Try: §cChecking if you've typed anything wrong in the command, or missed a few arguments!");
        }

        return false;
    }
}
