package me.improperissues.univault.commands;

import me.improperissues.univault.data.Page;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Tabs implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        String commandName = command.getName().toLowerCase().trim();

        switch (commandName) {
            case "vault":
                switch (args.length) {
                    case 1:
                        if (args[0].length() < 1) {
                            list.add("1");
                            list.add("existing:");
                            break;
                        } else if (args[0].contains("existing")) {
                            for (int index : Page.getExistingIndexes()) {
                                list.add("existing:" + index);
                            }
                            break;
                        }
                        break;
                }
        }

        return list;
    }
}
