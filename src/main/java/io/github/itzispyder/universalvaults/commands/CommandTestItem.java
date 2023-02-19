package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.archive.ArchivedStack;
import io.github.itzispyder.universalvaults.data.Config;
import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static io.github.itzispyder.universalvaults.Main.starter;

public class CommandTestItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player p = (Player) sender;
            ItemStack item = p.getInventory().getItemInMainHand();
            if (item.getType().isAir()) {
                p.sendMessage(starter + "§cBro thinks air has nbt §f☠");
                return true;
            }
            int length = item.toString().length();
            int max = Config.Archive.max_nbt_length;
            int min = Config.Archive.min_nbt_length;
            p.sendMessage(starter + "§7" + item.getType().name() + " §bis within NBT limits?§7: " + ArchivedStack.isPassable(item) +
                    "\n§7(§b" + length + " §7compared to §e" + max + " §7configured max & §e" + min + " §7configured min)");
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
