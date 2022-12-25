package me.improperissues.univault.commands;

import me.improperissues.univault.data.Config;
import me.improperissues.univault.data.Page;
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
                    return true;
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
