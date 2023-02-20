package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandUniVault implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            sender.sendMessage("""
                    §r
                    §r
                    §8===========================
                    §7About §l[§b§lUni§3§lVault§7§l]
                    §8§oby §8ImproperIssues §o& §8VideoGameSmash12
                    §7§oUniVault is a NBT archive plugin designed to store 
                    lots of NBT kits and items in files.
                    §r
                    §7By storing data in files, it prevents all sorts of 
                    exploits like chunk clears. UniVault also provides
                    easy access to NBT items via commands.
                    §r
                    §3§l§oHandpicked archives
                    §7UniVaults also has a handpicked kits system separate
                    from the main archive, for storing contents that 
                    you may wish to be highlighted. You can create multiple 
                    of these with less restrictions compared to archiving 
                    with the main archive.
                    §r
                    §3§l§oCommands
                    §b/submit §7§o-submit NBT items
                    §b/preview <index> §7§o-search the archive
                    §b/search [<query>] §7§o-search the archive for an item
                    §b/testitem §7§o-test item qualification
                    §8===========================
                    §r 
                    §r      
                    """);
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
            return new ArrayList<>();
        }
    }
}
