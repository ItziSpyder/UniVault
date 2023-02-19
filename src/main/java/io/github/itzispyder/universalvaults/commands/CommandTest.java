package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandTest implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            return true;
        } catch (Exception ex) {
            CmdExHandler exh = new CmdExHandler(ex,command);
            sender.sendMessage(exh.getErrorMessage());
            ex.printStackTrace();
            return true;
        }
    }

    public static class Tab implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            return new ArrayList<>();
        }
    }
}
