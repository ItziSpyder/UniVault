package io.github.itzispyder.universalvaults.commands;

import io.github.itzispyder.universalvaults.data.inventory.GuiFrames;
import io.github.itzispyder.universalvaults.data.inventory.InventoryPreset;
import io.github.itzispyder.universalvaults.exceptions.command.CmdExHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

import static io.github.itzispyder.universalvaults.Main.starter;

public class CommandSubmit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Player p = (Player) sender;
            Inventory menu = Bukkit.createInventory(null,54,starter + "§bSubmission");
            InventoryPreset preset = GuiFrames.SUBMISSION;
            preset.addPresetTo(menu);
            p.openInventory(menu);
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
